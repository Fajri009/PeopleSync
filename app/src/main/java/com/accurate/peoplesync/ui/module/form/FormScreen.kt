package com.accurate.peoplesync.ui.module.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.accurate.peoplesync.R
import com.accurate.peoplesync.ui.components.CustomDropDownField
import com.accurate.peoplesync.ui.components.CustomTextField
import com.accurate.peoplesync.ui.components.GenderSelector
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.PrimaryOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.heading5SemiBold
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.heading6SemiBold
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.paragraph1

@Composable
fun FormScreen(navigateBack: () -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var gender by remember { mutableIntStateOf(0) }

    Scaffold(containerColor = Color.White) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Row {
                IconButton(
                    modifier = Modifier.size(30.dp),
                    onClick = navigateBack
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.ic_back_page),
                        contentDescription = "Icon Back Page"
                    )
                }
                Spacer(modifier = Modifier.size(15.dp))
                Text(
                    text = "Tambah User Baru",
                    style = heading5SemiBold
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                CustomTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = "Nama Lengkap"
                )
                Spacer(modifier = Modifier.size(20.dp))

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    isEmail = true
                )
                Spacer(modifier = Modifier.size(20.dp))

                CustomTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = "Nomor Telepon",
                    isNumber = true
                )
                Spacer(modifier = Modifier.size(20.dp))

                CustomTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = "Alamat Lengkap"
                )
                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = "Pilh Kota",
                    style = paragraph1
                )
                Spacer(modifier =  Modifier.size(5.dp))
                CustomDropDownField(
                    selectedValue = city,
                    options = listOf(
                        "Aceh",
                        "Medan",
                        "Padang",
                        "Jambi",
                        "Bengkulu",
                        "Palembang",
                        "Tangerang",
                        "Jakarta",
                        "Bandung",
                        "Yogyakarta"
                    ),
                    onValueSelected = { city = it }
                )
                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = "Jenis Kelamin",
                    style = paragraph1
                )
                Spacer(modifier =  Modifier.size(5.dp))
                GenderSelector(
                    selectedGender = gender,
                    onGenderSelect = { gender = it }
                )
                Spacer(modifier = Modifier.size(25.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                    enabled = fullName.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && address.isNotEmpty() && city.isNotEmpty() && gender != 0,
                    onClick = { navigateBack() }
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "Simpan",
                        style = heading6SemiBold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FormScreenPreview() {
    FormScreen {}
}