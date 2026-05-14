package com.accurate.peoplesync.ui.module.home

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.accurate.peoplesync.di.FetchDataState
import com.accurate.peoplesync.ui.components.CustomTextField
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.PrimaryOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.paragraph1
import com.accurate.peoplesync.viewmodel.home.HomeViewModelType
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(
    viewModel: HomeViewModelType,
    navigateForm: () -> Unit
) {
    val userData by viewModel.userData.collectAsStateWithLifecycle()

    var search by remember { mutableStateOf("") }

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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.ic_sort),
                        contentDescription = "Icon Sort"
                    )
                    Spacer(modifier = Modifier.size(3.dp))
                    Text(
                        text = "Sort",
                        style = paragraph1
                    )
                    Spacer(modifier = Modifier.size(10.dp))
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
                Spacer(modifier = Modifier.size(20.dp))
                when (val result = userData) {
                    is FetchDataState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is FetchDataState.Success -> {
                        val response = result.data

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(response.size) { index ->
                                val user = response[index]

                                UserCard(
                                    name = user.name,
                                    address = user.address,
                                    city = user.city,
                                    email = user.email,
                                    phoneNumber = user.phoneNumber,
                                    gender = user.gender
                                )
                            }
                        }
                    }

                    is FetchDataState.Error -> { }

                    null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
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
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val viewModel = object : HomeViewModelType {
        override val userData = MutableStateFlow(null)
        override fun getUserData() { }
    }

    HomeScreen(
        viewModel = viewModel,
        navigateForm = {}
    )
}