package com.accurate.peoplesync.ui.module.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.accurate.peoplesync.R
import com.accurate.peoplesync.ui.components.CustomTextField
import com.accurate.peoplesync.ui.components.FilterBottomSheet
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.LightOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.PrimaryOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.heading5SemiBold
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.heading6
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.paragraph1
import com.accurate.peoplesync.viewmodel.home.HomeViewModelType
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModelType,
    navigateForm: () -> Unit
) {
    val userData by viewModel.userData.collectAsStateWithLifecycle()
    val cityData by viewModel.cityData.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val showErrorDialog by viewModel.showErrorDialog.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    var search by remember { mutableStateOf("") }
    var sortState by remember { mutableStateOf(SortState.DEFAULT) }
    var showFilterBottomSheet by remember { mutableStateOf(false) }
    var filteredCity by remember { mutableStateOf("") }
    var filteredGender by remember { mutableIntStateOf(99) }

    val pullRefreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.size(20.dp))
                CustomTextField(
                    value = search,
                    onValueChange = { search = it },
                    isSearch = true
                )
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Row(
                        modifier = Modifier.clickable {
                            sortState =
                                when (sortState) {
                                    SortState.DEFAULT -> SortState.ASCENDING
                                    SortState.ASCENDING -> SortState.DESCENDING
                                    SortState.DESCENDING -> SortState.DEFAULT
                                }
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(
                                id =
                                    when (sortState) {
                                        SortState.DEFAULT -> R.drawable.ic_sort
                                        SortState.ASCENDING -> R.drawable.ic_ascending_alpha
                                        SortState.DESCENDING -> R.drawable.ic_descending_alpha
                                    }
                            ),
                            contentDescription = "Icon Sort"
                        )
                        Spacer(modifier = Modifier.size(3.dp))
                        Text(
                            text = "Sort",
                            style = paragraph1
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.clickable {
                            showFilterBottomSheet = true
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "Icon Filter"
                        )
                        Spacer(modifier = Modifier.size(3.dp))
                        Text(
                            text = "Filter",
                            style = paragraph1
                        )
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                PullToRefreshBox(
                    modifier = Modifier.fillMaxSize(),
                    state = pullRefreshState,
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        viewModel.refreshData()
                    },
                    indicator = {
                        PullToRefreshDefaults.Indicator(
                            modifier = Modifier.align(Alignment.TopCenter),
                            isRefreshing = isRefreshing,
                            state = pullRefreshState,
                            containerColor = LightOrange,
                            color = PrimaryOrange
                        )
                    }
                ) {
                    val filteredList = userData
                        ?.filter { user ->
                            val nameSearch =
                                user.name.contains(search, ignoreCase = true) ||
                                user.email.contains(search, ignoreCase = true)

                            val citySearch =
                                filteredCity.isEmpty() ||
                                user.city.equals(filteredCity, ignoreCase = true)

                            val genderSearch =
                                filteredGender == 99 ||
                                user.gender == filteredGender

                            nameSearch && citySearch && genderSearch
                        }
                        ?.let { list ->
                            when (sortState) {
                                SortState.DEFAULT -> list
                                SortState.ASCENDING -> list.sortedBy { it.name }
                                SortState.DESCENDING -> list.sortedByDescending { it.name }
                            }
                        }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        when {
                            userData == null -> {
                                item {
                                    Box(
                                        modifier = Modifier.fillParentMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }

                            filteredList.isNullOrEmpty() -> {
                                item {
                                    Box(
                                        modifier = Modifier.fillParentMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(
                                                modifier = Modifier.size(100.dp),
                                                painter = painterResource(id = R.drawable.ic_inbox),
                                                contentDescription = null
                                            )
                                            Spacer(Modifier.size(10.dp))
                                            Text("Data Tidak Ditemukan", style = heading6)
                                        }
                                    }
                                }
                            }

                            else -> {
                                items(filteredList.size) { index ->
                                    val user = filteredList[index]

                                    UserCard(
                                        name = user.name,
                                        address = user.address,
                                        city = user.city,
                                        email = user.email,
                                        phoneNumber = user.phoneNumber,
                                        gender = user.gender
                                    )
                                }

                                item {
                                    Spacer(Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(20.dp)
                    .size(60.dp)
                    .align(Alignment.BottomEnd),
                onClick = navigateForm,
                containerColor = PrimaryOrange,
                contentColor = Color.White
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Icon Add"
                )
            }

            if (showFilterBottomSheet && cityData != null) {
                FilterBottomSheet(
                    onDismiss = { showFilterBottomSheet = false },
                    cityData = cityData ?: emptyList(),
                    selectedCity = filteredCity,
                    selectedGender = filteredGender,
                    onApply = { city, gender ->
                        filteredCity = city
                        filteredGender = gender
                    }
                )
            }

            if (showErrorDialog) {
                AlertDialog(
                    onDismissRequest = { },
                    title = {
                        Text(
                            text = "PeopleSync",
                            style = heading5SemiBold
                        )
                    },
                    text = {
                        Text(
                            text = errorMessage,
                            style = paragraph1,
                            color = Color.Black
                        )
                    },
                    containerColor = Color.White,
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.refreshData()
                            }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val viewModel = object : HomeViewModelType {
        override val userData = MutableStateFlow(null)
        override val cityData = MutableStateFlow(null)
        override val isRefreshing = MutableStateFlow(false)
        override val showErrorDialog = MutableStateFlow(false)
        override val errorMessage = MutableStateFlow("")

        override fun setUserData() { }
        override fun getUserData() { }
        override fun setCityData() { }
        override fun getAllCity() { }
        override fun refreshData() { }
    }

    HomeScreen(
        viewModel = viewModel,
        navigateForm = {}
    )
}