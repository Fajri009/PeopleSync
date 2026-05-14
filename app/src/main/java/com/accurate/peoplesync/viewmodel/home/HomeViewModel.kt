package com.accurate.peoplesync.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accurate.peoplesync.data.repository.UserRepository
import com.accurate.peoplesync.data.repository.model.userResponse.UserResponse
import com.accurate.peoplesync.di.FetchDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelType {
    val userData: StateFlow<FetchDataState<UserResponse>?>

    fun getUserData()
}

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel(), HomeViewModelType {
    private val _userData = MutableStateFlow<FetchDataState<UserResponse>?>(null)
    override val userData: StateFlow<FetchDataState<UserResponse>?> = _userData

    init {
        Log.d("HomeViewModel", "INIT CALLED")
        getUserData()
    }

    override fun getUserData() {
        Log.d("HomeViewModel", "Get User Data Called!")
        _userData.value = FetchDataState.Loading

        viewModelScope.launch {
            userRepository.getUser()
                .catch { error ->
                    _userData.value = FetchDataState.Error(error.message!!)
                    Log.e("HomeViewModel", error.message!!)
                }
                .collect { response ->
                    _userData.value = FetchDataState.Success(response)
                    Log.d("HomeViewModel", "Get User Data Success : $response")
                }
        }
    }
}