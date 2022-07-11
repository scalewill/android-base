package com.scalewill.android.login

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.scalewill.android.graphql.mutation.LoginMutation
import com.scalewill.android.model.entities.Session
import com.scalewill.android.model.network.NetworkBoundResource
import com.scalewill.android.model.network.errors.ErrorHandler
import com.scalewill.android.profile.AuthorizationModel
import com.scalewill.android.type.SignInInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class LoginRepository(
    private val apolloClient: ApolloClient,
    private val authorizationModel: AuthorizationModel,
    private val errorHandler: ErrorHandler,
    private val loginMapper: LoginMapper
) : CoroutineScope {
    override val coroutineContext = SupervisorJob() + Dispatchers.IO

    fun login(username: String, password: String) =
        object : NetworkBoundResource<Session, LoginMutation.Data>(coroutineContext, errorHandler) {
            override suspend fun createCallAsync(): Deferred<Response<LoginMutation.Data>> =
                apolloClient.mutate(loginMutation(username, password)).toDeferred()

            override suspend fun saveCallResult(item: LoginMutation.Data?) {
                authorizationModel.setSession(loginMapper.mapLogin(item?.signin))
            }

            override suspend fun loadFromDb() = authorizationModel.getSession()
        }.asLiveData()

    fun unAuthorize() {
        launch {
            authorizationModel.unAuthorize()
        }
    }

    fun onDestroy() {
        coroutineContext.cancelChildren()
    }

    private fun loginMutation(email: String, password: String) =
        LoginMutation(SignInInput(email = email, password = password))
}
