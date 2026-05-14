package com.accurate.peoplesync.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accurate.peoplesync.data.repository.UserRepository
import com.accurate.peoplesync.data.repository.model.cityResponse.CityResponse
import com.accurate.peoplesync.data.repository.model.userResponse.UserResponse
import com.accurate.peoplesync.data.room.RepositoryDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelType {
    val userData: StateFlow<UserResponse?>
    val cityData: StateFlow<CityResponse?>
    val isRefreshing: StateFlow<Boolean>

    fun setUserData()
    fun getUserData()
    fun setCityData()
    fun getAllCity()
    fun refreshData()
}

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
    private val dbRepository: RepositoryDb
) : ViewModel(), HomeViewModelType {
    private val _userData = MutableStateFlow<UserResponse?>(null)
    override val userData: StateFlow<UserResponse?> = _userData

    private val _cityData = MutableStateFlow<CityResponse?>(null)
    override val cityData: StateFlow<CityResponse?> = _cityData

    private val _isRefreshing = MutableStateFlow(false)
    override val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init { refreshData() }

    override fun setUserData() {
        Log.d("HomeViewModel", "Set User Data Called!")

        viewModelScope.launch {
            _isRefreshing.value = true

            userRepository.getUser()
                .catch { error ->
                    Log.e("HomeViewModel", error.message!!)
                    _isRefreshing.value = false
                }
                .collect { response ->
                    _isRefreshing.value = false
                    dbRepository.insertUsers(response)
                    Log.d("HomeViewModel", "Get User Data Success : $response")
                }
        }
    }

    override fun getUserData() {
        Log.d("HomeViewModel", "Get User Data Called!")

        viewModelScope.launch {
            dbRepository.getUsers().collect { users ->
                _userData.value = users
            }
        }
    }

    override fun setCityData() {
        Log.d("HomeViewModel", "Set All City Data Called!")

        viewModelScope.launch {
            _isRefreshing.value = true

            userRepository.getAllCity()
                .catch { error ->
                    Log.e("HomeViewModel", error.message!!)
                    _isRefreshing.value = false
                }
                .collect { response ->
                    _isRefreshing.value = false
                    dbRepository.insertCity(response)
                    Log.d("HomeViewModel", "Get All City Data Success : $response")
                }
        }
    }

    override fun getAllCity() {
        Log.d("HomeViewModel", "Get All City Data Called!")

        viewModelScope.launch {
            dbRepository.getCity().collect { city ->
                _cityData.value = city
            }
        }
    }

    override fun refreshData() {
        // User Data
        setUserData()
        getUserData()

        // City Data
        setCityData()
        getAllCity()
    }
}