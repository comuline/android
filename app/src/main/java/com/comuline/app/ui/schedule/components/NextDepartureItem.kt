package com.comuline.app.ui.schedule.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import com.comuline.app.ui.theme.Typography

@Preview
@Composable
fun NextDepartureItem(
    timeStr: String = "22:34:00",
    timeDiffStr: String = "22 jam")
{
    Column (
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = timeStr,
            color = MaterialTheme.colorScheme.onBackground,
            style = Typography.labelSmall
        )
        Text(
            text = timeDiffStr,
            color = MaterialTheme.colorScheme.onBackground,
            style = Typography.titleSmall,
            modifier = Modifier.alpha(0.3f)
        )
    }
}