package com.accurate.peoplesync.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    label: String = ""
) {
    Column {
        if (!isSearch) {
            Text(
                text = label,
                style = paragraph1
            )
            Spacer(modifier =  Modifier.size(10.dp))
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
            )
        )
    }
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        value = "",
        onValueChange = {},
        isSearch = false,
        label = "Nama Lengkap"
    )
}