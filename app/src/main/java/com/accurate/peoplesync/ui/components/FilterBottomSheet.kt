package com.accurate.peoplesync.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.accurate.peoplesync.data.repository.model.cityResponse.CityItem
import com.accurate.peoplesync.data.repository.model.cityResponse.CityResponse
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.Gray
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.PrimaryOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.heading5SemiBold
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.heading6
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.paragraph1SemiBold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onDismiss: () -> Unit = {},
    cityData: CityResponse,
    selectedCity: String,
    selectedGender: Int,
    onApply: (String, Int) -> Unit
) {
    var selectedCity by remember { mutableStateOf(selectedCity) }
    var selectedGender by remember { mutableIntStateOf(selectedGender) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = Color.White,
        sheetState = sheetState,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(6.dp)
                        .background(
                            color = Gray,
                            shape = RoundedCornerShape(3.dp)
                        )
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Filter Data",
                style = heading5SemiBold
            )
            Spacer(modifier = Modifier.size(15.dp))

            Text(
                text = "Pilih Kota",
                style = heading6
            )
            Spacer(modifier = Modifier.size(10.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                cityData.forEach { city ->
                    CityItem(
                        city = city.name,
                        selected = selectedCity == city.name,
                        onSelect = { selectedCity = it }
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Pilih Jenis Kelamin",
                style = heading6
            )
            Spacer(modifier = Modifier.size(10.dp))
            GenderSelector(
                selectedGender = selectedGender,
                onGenderSelect = { selectedGender = it }
            )
            Spacer(modifier = Modifier.size(15.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton (
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        selectedGender = 99
                        selectedCity = ""
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "Reset",
                        style = paragraph1SemiBold
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                    onClick = {
                        onApply(selectedCity, selectedGender)
                        onDismiss()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "Apply",
                        style = paragraph1SemiBold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FilterBottomSheetPreview() {
    Box(
        modifier =  Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val cityDummy = listOf(
            CityItem(name = "Tangerang", id = "1"),
            CityItem(name = "Depok", id = "2")
        )

        FilterBottomSheet(
            onDismiss = {},
            cityData = cityDummy,
            selectedCity = "",
            selectedGender = 99,
            onApply = {_, _ ->}
        )
    }
}