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
    val showErrorDialog: StateFlow<Boolean>
    val errorMessage: StateFlow<String>

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

    private val _showErrorDialog = MutableStateFlow(false)
    override val showErrorDialog: StateFlow<Boolean> = _showErrorDialog

    private val _errorMessage = MutableStateFlow("")
    override val errorMessage: StateFlow<String> = _errorMessage

    private var userApiFailed = false
    private var cityApiFailed = false

    private var userDbReady = false
    private var cityDbReady = false

    init { refreshData() }

    override fun setUserData() {
        Log.d("HomeViewModel", "Set User Data Called!")

        viewModelScope.launch {
            _isRefreshing.value = true
            userApiFailed = false

            userRepository.getUser()
                .catch { error ->
                    _isRefreshing.value = false
                    userApiFailed = true
                    Log.e("HomeViewModel", error.message!!)

                    val errorMessage =
                        if (error.message!!.contains("No address associated with hostname", ignoreCase = true)) {
                            "Tidak dapat terhubung ke server. Periksa konseksi internet Anda dan coba lagi."
                        } else {
                            "Terjadi kesalahan. Silahkan dicoba lagi."
                        }

                    _errorMessage.value = errorMessage

                    checkErrorState()
                }
                .collect { response ->
                    _isRefreshing.value = false
                    userApiFailed = false

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
                _showErrorDialog.value = users.isEmpty()
                userDbReady = true

                checkErrorState()
            }
        }
    }

    override fun setCityData() {
        Log.d("HomeViewModel", "Set All City Data Called!")

        viewModelScope.launch {
            _isRefreshing.value = true
            cityApiFailed = false

            userRepository.getAllCity()
                .catch { error ->
                    _isRefreshing.value = false
                    cityApiFailed = true
                    Log.e("HomeViewModel", error.message!!)

                    val errorMessage =
                        if (error.message!!.contains("No address associated with hostname", ignoreCase = true)) {
                            "Tidak dapat terhubung ke server. Periksa konseksi internet Anda dan coba lagi."
                        } else {
                            "Terjadi kesalahan. Silahkan dicoba lagi."
                        }

                    _errorMessage.value = errorMessage

                    checkErrorState()
                }
                .collect { response ->
                    _isRefreshing.value = false
                    userApiFailed = false

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
                cityDbReady = true

                checkErrorState()
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

    private fun checkErrorState() {
        val userEmpty = _userData.value.isNullOrEmpty()
        val cityEmpty = _cityData.value.isNullOrEmpty()

        val dbReady = userDbReady || cityDbReady
        val apiFailed = userApiFailed || cityApiFailed

        _showErrorDialog.value = dbReady && apiFailed && userEmpty && cityEmpty
    }
}