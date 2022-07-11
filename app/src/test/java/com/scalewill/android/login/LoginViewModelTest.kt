package com.scalewill.android.login

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.scalewill.android.model.entities.Resource
import com.scalewill.android.model.entities.Session
import com.scalewill.android.test_utils.InstantLiveDataExecutor
import com.scalewill.android.util.StringResource
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek

object LoginViewModelTest : Spek({
    val mainThreadSurrogate = newSingleThreadContext("UI thread")
    val mockStringResource = mockk<StringResource>()
    val mockLoginRepository = mockk<LoginRepository>()

    beforeEachTest {
        Dispatchers.setMain(mainThreadSurrogate)
        ArchTaskExecutor.getInstance().setDelegate(InstantLiveDataExecutor)
    }

    afterEachTest {
        ArchTaskExecutor.getInstance().setDelegate(null)
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    test("initialization") {
        val mockObserver = mockk<Observer<Resource<Session>?>>()

        val viewModel = LoginViewModel(mockLoginRepository, mockStringResource)

        viewModel.loginResource.observeForever(mockObserver)

        verify(inverse = true) { mockObserver.onChanged(any()) }
        verify(inverse = true) { mockObserver.onChanged(null) }
    }

    group("login") {

        test("All good") {
            val expectedUsername = "Ilmaz"
            val expectedPassword = "admin"

            val mockObserver = mockk<Observer<Resource<Session>>>()
            val viewModel = LoginViewModel(mockLoginRepository, mockStringResource)
            val expectedLiveData = MutableLiveData<Resource<Session>>()
            every { mockObserver.onChanged(any()) } just Runs
            every { mockLoginRepository.login(expectedUsername, expectedPassword) } returns expectedLiveData
            viewModel.loginResource.observeForever(mockObserver)

            viewModel.login(expectedUsername, expectedPassword)

            verify { mockLoginRepository.login(expectedUsername, expectedPassword) }
        }

        test("empty password") {
            val expectedUsername = "Ilmaz"
            val expectedPassword = ""

            val expectedErrorText = "Bad boy!"
            val expectedError = Resource.error<Session>(expectedErrorText)
            val expectedLiveData = MutableLiveData<Resource<Session>>()
            expectedLiveData.value = expectedError

            val mockObserver = mockk<Observer<Resource<Session>>>()
            val viewModel = LoginViewModel(mockLoginRepository, mockStringResource)
            every { mockStringResource.getString(any()) } returns expectedErrorText
            every { mockObserver.onChanged(any()) } just Runs
            viewModel.loginResource.observeForever(mockObserver)

            viewModel.login(expectedUsername, expectedPassword)

            verify { mockObserver.onChanged(expectedError) }
        }

        test("empty login") {
            val expectedUsername = ""
            val expectedPassword = "not admin"

            val expectedErrorText = "Bad boy!"
            val expectedError = Resource.error<Session>(expectedErrorText)
            val expectedLiveData = MutableLiveData<Resource<Session>>()
            expectedLiveData.value = expectedError

            val mockObserver = mockk<Observer<Resource<Session>>>()
            val viewModel = LoginViewModel(mockLoginRepository, mockStringResource)
            every { mockStringResource.getString(any()) } returns expectedErrorText
            every { mockObserver.onChanged(any()) } just Runs
            viewModel.loginResource.observeForever(mockObserver)

            viewModel.login(expectedUsername, expectedPassword)

            verify { mockObserver.onChanged(expectedError) }
        }

        test("empty login and password") {
            val expectedUsername = ""
            val expectedPassword = ""

            val expectedErrorText = "Bad boy!"
            val expectedError = Resource.error<Session>(expectedErrorText)
            val expectedLiveData = MutableLiveData<Resource<Session>>()
            expectedLiveData.value = expectedError

            val mockObserver = mockk<Observer<Resource<Session>>>()
            val viewModel = LoginViewModel(mockLoginRepository, mockStringResource)
            every { mockStringResource.getString(any()) } returns expectedErrorText
            every { mockObserver.onChanged(any()) } just Runs
            viewModel.loginResource.observeForever(mockObserver)

            viewModel.login(expectedUsername, expectedPassword)

            verify { mockObserver.onChanged(expectedError) }
        }
    }
})
