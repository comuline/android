package com.comuline.app.ui.schedule.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.comuline.app.R
import com.comuline.app.ui.theme.Typography

@Preview
@Composable
fun CurrentDepartureTime(
    timeStr: String = "22:34:00",
    timeDiffStr: String = "22 jam"
) {

    Column (
        horizontalAlignment = Alignment.End,
    ) {
        Text(
            text = stringResource(id = R.string.departure_time),
            color = MaterialTheme.colorScheme.secondary,
            style = Typography.titleSmall
        )
        Text(
            text = timeStr,
            color = MaterialTheme.colorScheme.primary,
            style = Typography.titleLarge
        )
        Text(
            text = timeDiffStr,
            color = MaterialTheme.colorScheme.tertiary,
            style = Typography.titleSmall
        )
    }
}