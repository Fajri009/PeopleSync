package com.accurate.peoplesync.viewmodel.form

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accurate.peoplesync.data.repository.UserRepository
import com.accurate.peoplesync.data.repository.model.UserRequest
import com.accurate.peoplesync.di.FetchDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FormViewModelType {
    val userState: StateFlow<FetchDataState<Int>?>

    fun addUserData(userData: UserRequest)
}

@HiltViewModel
class FormViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel(), FormViewModelType {
    private val _userState = MutableStateFlow<FetchDataState<Int>?>(null)
    override val userState: StateFlow<FetchDataState<Int>?> = _userState

    override fun addUserData(userData: UserRequest) {
        Log.d("FormViewModel", "Add User Data Called with User Data : $userData")
        _userState.value = FetchDataState.Loading

        viewModelScope.launch {
            userRepository.addUser(userData)
                .catch { error ->
                    _userState.value = FetchDataState.Error(error.message!!)
                    Log.e("FormViewModel", error.message!!)
                }
                .collect { response ->
                    _userState.value = FetchDataState.Success(response.code())
                    Log.d("FormViewModel", "Add User Data : ${response.code()}")
                }
        }
    }
}