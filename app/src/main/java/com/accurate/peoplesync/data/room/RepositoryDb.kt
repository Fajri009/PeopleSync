package com.accurate.peoplesync.data.room

import com.accurate.peoplesync.data.repository.DbRepository
import com.accurate.peoplesync.data.repository.model.cityResponse.CityItem
import com.accurate.peoplesync.data.repository.model.cityResponse.CityResponse
import com.accurate.peoplesync.data.repository.model.userResponse.UserItem
import com.accurate.peoplesync.data.repository.model.userResponse.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class RepositoryDb @Inject constructor(
    private val userDao: UserDao,
    private val cityDao: CityDao
): DbRepository {
    override fun getUsers(): Flow<UserResponse> {
        return userDao.getUsers().map { users ->
            users.map { user ->
                UserItem(
                    id = user.id,
                    name = user.name,
                    address = user.address,
                    email = user.email,
                    phoneNumber = user.phoneNumber,
                    city = user.city,
                    gender = user.gender
                )
            }
        }
    }

    override suspend fun insertUsers(users: UserResponse) {
        val userEntity = users.map { user ->
            UserEntity(
                id = user.id,
                name = user.name,
                address = user.address,
                email = user.email,
                phoneNumber = user.phoneNumber,
                city = user.city,
                gender = user.gender
            )
        }

        userDao.insertUsers(userEntity)
    }

    override fun getCity(): Flow<CityResponse> {
        return cityDao.getCity().map { cities ->
            cities.map { city ->
                CityItem(
                    id = city.id,
                    name = city.name
                )
            }
        }
    }

    override suspend fun insertCity(city: CityResponse) {
        val cityEntity = city.map { city ->
            CityEntity(
                id = city.id,
                name = city.name
            )
        }

        cityDao.insertCity(cityEntity)
    }
}