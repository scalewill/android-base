package com.scalewill.android.login

import com.scalewill.android.graphql.mutation.LoginMutation
import com.scalewill.android.model.entities.Session

class LoginMapper {
    fun mapLogin(signIn: LoginMutation.Signin?) = Session(accessToken = signIn?.accessToken ?: "")
}
