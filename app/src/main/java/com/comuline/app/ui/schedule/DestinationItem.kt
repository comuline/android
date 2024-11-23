package com.comuline.app.ui.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.comuline.app.R
import com.comuline.app.domain.SCHEDULES_SAMPLES
import com.comuline.app.domain.Schedule
import com.comuline.app.ui.schedule.components.CurrentDepartureTime
import com.comuline.app.ui.schedule.components.NextDepartureItem
import com.comuline.app.ui.schedule.components.OtherTimeDepartureItem
import com.comuline.app.ui.theme.Typography
import com.comuline.app.util.getRelativeTimeString
import com.comuline.app.util.removeSeconds

val PADDING_LEFT = 8.dp
// TODO: 2 for small devices 4 for md > devices
val ROWS = 4
val ROWS_OTHER = 5

val MAX_VISIBLE_SCHEDULES = 4

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun DestinationItem(
    stationName: String = "Bekasi",
    colorStr: String = "#0084D8",
    schedules: List<Schedule> = SCHEDULES_SAMPLES
) {
    val isNextHourAvailable = schedules.isNotEmpty()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colorScheme.background),
    ){
        if(isNextHourAvailable){
            Box(modifier = Modifier
                .width(8.dp)
                .fillMaxHeight()
                .background(Color(colorStr.toColorInt()))
            )
            Spacer(modifier = Modifier.width(PADDING_LEFT))
            Column {
                Row {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                text = stringResource(id = R.string.destination),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.titleSmall
                            )
                            Row {
                                Text(
                                    text = stationName,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = Typography.titleLarge
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1.0f))
                        CurrentDepartureTime(
                            timeStr = removeSeconds(schedules[0].timeEstimated),
                            timeDiffStr = getRelativeTimeString(schedules[0].timeEstimated)
                        )
                    }
                }
                Column {
                    Text(
                        text = stringResource(id = R.string.destination),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.titleSmall
                    )
                    FlowRow(
                        modifier = Modifier.padding(4.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalArrangement = Arrangement.Top,
                        maxItemsInEachRow = ROWS
                    ) {
                        val maxVisibleSchedules = MAX_VISIBLE_SCHEDULES

                        // Split the schedules into visible and hidden parts
                        val visibleSchedules = schedules.drop(1).take(maxVisibleSchedules)

                        visibleSchedules.mapIndexed { index, schedule ->
                            if (index < maxVisibleSchedules) { // Show schedules up to the maxVisibleSchedules
                                NextDepartureItem(
                                    timeStr = removeSeconds(schedule.timeEstimated),
                                    timeDiffStr = getRelativeTimeString(schedule.timeEstimated)
                                )
                            }
                        }

                    }

                    var showAll by remember { mutableStateOf(false) } // State to track visibility of all schedules
                    val maxVisibleSchedules = MAX_VISIBLE_SCHEDULES

                    // Split the schedules into visible and hidden parts
                    val hiddenSchedules = schedules.drop(maxVisibleSchedules)

                    // Add "View More" or "View Less" button if there are hidden schedules
                    if (schedules.size > maxVisibleSchedules && hiddenSchedules.isNotEmpty()) {
                        Button(
                            onClick = { showAll = !showAll }, // Toggle the showAll state
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(if (showAll) stringResource(R.string.view_less) else stringResource(R.string.view_more))
                        }
                    }

                    if (showAll) {
                        FlowRow(
                            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            maxItemsInEachRow = ROWS_OTHER
                        ) {
                            // Conditionally render the hidden schedules if "View More" is toggled
                            hiddenSchedules.forEach { schedule ->
                                OtherTimeDepartureItem( // Use a different component for hidden items
                                    timeStr = removeSeconds(schedule.timeEstimated),
                                )
                            }
                        }
                    }
                }
            }
        }
         else {
            Text(
                text = stringResource(id = R.string.no_schedules_available),
                color = MaterialTheme.colorScheme.onBackground,
                style = Typography.titleSmall
            )
        }
    }
}