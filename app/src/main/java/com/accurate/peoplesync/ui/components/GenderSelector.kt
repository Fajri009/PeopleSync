package com.accurate.peoplesync.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.accurate.peoplesync.R
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.DodgerBlue
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.LightOrange
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Color.Companion.Pink
import com.accurate.peoplesync.ui.theme.PeopleSyncAppTheme.Text.Companion.paragraph1

@Composable
fun GenderSelector(
    selectedGender: Int,
    onGenderSelect: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = LightOrange,
                shape = RoundedCornerShape(5.dp)
            ),
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (selectedGender == 1) DodgerBlue
                    else Color.White
                )
                .clickable { onGenderSelect(1) }
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.ic_male),
                contentDescription = "Icon Laki-laki",
                tint =
                    if (selectedGender == 1) Color.White
                    else Color.Black
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Laki-laki",
                style = paragraph1,
                color =
                    if (selectedGender == 1) Color.White
                    else Color.Black
            )
        }
        VerticalDivider(
            thickness = 1.dp,
            color = LightOrange
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .background(
                    color =
                        if (selectedGender == 2) Pink
                        else Color.White
                )
                .clickable { onGenderSelect(2) }
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.ic_female),
                contentDescription = "Icon Perempuan",
                tint =
                    if (selectedGender == 2) Color.White
                    else Color.Black
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Perempuan",
                style = paragraph1,
                color =
                    if (selectedGender == 2) Color.White
                    else Color.Black
            )
        }
    }
}

@Preview
@Composable
fun GenderSelectorPreview() {
    GenderSelector(
        selectedGender = 0,
        onGenderSelect = {}
    )
}