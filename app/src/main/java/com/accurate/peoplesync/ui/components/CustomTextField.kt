package com.accurate.peoplesync.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.accurate.peoplesync.R
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.LightOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.PrimaryOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.paragraph1

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isSearch: Boolean = false,
    label: String = "",
    isEmail: Boolean = false,
    isNumber: Boolean = false,
) {
    Column {
        if (!isSearch) {
            Text(
                text = label,
                style = paragraph1
            )
            Spacer(modifier =  Modifier.size(5.dp))
        }
        OutlinedTextField(
            placeholder = {
                if (isSearch) {
                    Text(text = "Search")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            textStyle = paragraph1,
            leadingIcon = if (isSearch) {
                {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Icon Search"
                    )
                }
            } else null,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,

                // Border (Outline)
                focusedIndicatorColor = PrimaryOrange,
                unfocusedIndicatorColor = LightOrange
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType =
                    if (isEmail) KeyboardType.Email
                    else if (isNumber) KeyboardType.Number
                    else KeyboardType.Text,
                capitalization = 
                    if (!isEmail) KeyboardCapitalization.Words
                    else KeyboardCapitalization.None,
                autoCorrectEnabled = true
            )
        )
    }
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    Surface(color = Color.White) {
        CustomTextField(
            value = "",
            onValueChange = {},
            isSearch = false,
            label = "Nama Lengkap"
        )
    }
}