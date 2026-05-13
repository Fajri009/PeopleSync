package com.accurate.peoplesync.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.accurate.peoplesync.R
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.LightOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.PrimaryOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.paragraph1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownField(
    selectedValue: String,
    options: List<String>,
    onValueSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            shape = RoundedCornerShape(5.dp),
            textStyle = paragraph1,
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(
                        id =
                            if (expanded) R.drawable.ic_dropup_arrow
                            else R.drawable.ic_dropdown_arrow
                    ),
                    contentDescription = "Icon Dropdown Arrow",
                    tint = Color.Black
                )
            },
            colors = outlinedTextFieldColors(
                focusedBorderColor = PrimaryOrange,
                unfocusedBorderColor = LightOrange,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        ExposedDropdownMenu(
            modifier = Modifier.background(Color.White),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = paragraph1
                        )
                    }, 
                    onClick = {
                        onValueSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CustomDropDownFieldPreview() {
    CustomDropDownField(
        selectedValue = "",
        options = listOf(),
        onValueSelected = {}
    )
}