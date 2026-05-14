package com.accurate.peoplesync.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accurate.peoplesync.data.repository.UserRepository
import com.accurate.peoplesync.data.repository.model.cityResponse.CityResponse
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
    val cityData: StateFlow<FetchDataState<CityResponse>?>

    fun getUserData()
    fun getAllCity()
    fun refreshData()
}

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel(), HomeViewModelType {
    private val _userData = MutableStateFlow<FetchDataState<UserResponse>?>(null)
    override val userData: StateFlow<FetchDataState<UserResponse>?> = _userData

    private val _cityData = MutableStateFlow<FetchDataState<CityResponse>?>(null)
    override val cityData: StateFlow<FetchDataState<CityResponse>?> = _cityData

    init { refreshData() }

    override fun getUserData() {
        Log.d("HomeViewModel", "Get User Data Called!")
        _userData.value = FetchDataState.Loading

        viewModelScope.launch {
            userRepository.getUser()
                .catch { error ->
                    val errorMessage =
                        if (error.message!!.contains("No address associated with hostname", ignoreCase = true)) {
                            "Tidak dapat terhubung ke server. Periksa konseksi internet Anda dan coba lagi."
                        } else {
                            "Terjadi kesalahan. Silahkan dicoba lagi."
                        }
                    _userData.value = FetchDataState.Error(errorMessage)
                    Log.e("HomeViewModel", error.message!!)
                }
                .collect { response ->
                    _userData.value = FetchDataState.Success(response)
                    Log.d("HomeViewModel", "Get User Data Success : $response")
                }
        }
    }

    override fun getAllCity() {
        Log.d("HomeViewModel", "Get All City Data Called!")
        _cityData.value = FetchDataState.Loading

        viewModelScope.launch {
            userRepository.getAllCity()
                .catch { error ->
                    val errorMessage =
                        if (error.message!!.contains("No address associated with hostname", ignoreCase = true)) {
                            "Tidak dapat terhubung ke server. Periksa konseksi internet Anda dan coba lagi."
                        } else {
                            "Terjadi kesalahan. Silahkan dicoba lagi."
                        }
                    _cityData.value = FetchDataState.Error(errorMessage)
                    Log.e("HomeViewModel", error.message!!)
                }
                .collect { response ->
                    _cityData.value = FetchDataState.Success(response)
                    Log.d("HomeViewModel", "Get All City Data Success : $response")
                }
        }
    }

    override fun refreshData() {
        getUserData()
        getAllCity()
    }
}