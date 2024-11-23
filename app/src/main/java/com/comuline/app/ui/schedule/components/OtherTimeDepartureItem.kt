package com.comuline.app.ui.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comuline.app.ui.theme.Typography

@Preview
@Composable
fun OtherTimeDepartureItem(
    timeStr: String = "22:34",
)
{
    Column (
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(24))
            .background(MaterialTheme.colorScheme.surfaceDim)
            .padding(vertical = 1.dp, horizontal = 6.dp)

    ) {
        Text(
            text = timeStr,
            color = MaterialTheme.colorScheme.tertiary,
            style = Typography.titleSmall
        )
    }
}