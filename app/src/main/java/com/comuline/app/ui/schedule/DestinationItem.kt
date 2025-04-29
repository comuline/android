package com.comuline.app.ui.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide
import com.comuline.app.R
//import com.comuline.app.domain.SCHEDULES_SAMPLES
import com.comuline.app.domain.Schedule
import com.comuline.app.domain.Station
import com.comuline.app.ui.schedule.components.CurrentDepartureTime
import com.comuline.app.ui.schedule.components.NextDepartureItem
import com.comuline.app.ui.schedule.components.OtherTimeDepartureItem
import com.comuline.app.ui.theme.Shapes
import com.comuline.app.ui.theme.Typography
import com.comuline.app.util.formatToHourMinute
import com.comuline.app.util.getRelativeTimeString
import kotlinx.coroutines.flow.Flow

val PADDING_LEFT = 8.dp
// TODO: 2 for small devices 4 for md > devices
val ROWS = 4
val ROWS_OTHER = 6

val MAX_VISIBLE_SCHEDULES = 4

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun DestinationItem(
    stationName: String = "Bekasi",
    trainId: String = "1314B",
    colorStr: String = "#0084D8",
    schedules: List<Schedule>,
    stationFlow: Flow<Station?>
) {
    val isNextHourAvailable = schedules.isNotEmpty()

    val station by stationFlow.collectAsState(initial = null)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(shape = Shapes.large)
            .background(MaterialTheme.colorScheme.background),
    ){
        if(isNextHourAvailable && station != null){
            Box(modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(Color(colorStr.toColorInt()))
            )
            Spacer(modifier = Modifier.width(PADDING_LEFT))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = schedules[0].line,
                    color = Color(colorStr.toColorInt()),
                    style = Typography.titleSmall
                )
                Row(
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = stringResource(id = R.string.destination),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = Typography.titleSmall,
                                modifier = Modifier.alpha(0.5f)
                            )
                            Row {
                                Text(
                                    text = station!!.name,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = Typography.titleLarge,
                                )
                            }
                            Row {
                                Text(
                                    text = trainId,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = Typography.titleSmall,
                                    modifier = Modifier.alpha(0.3f)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1.0f))
                        CurrentDepartureTime(
                            timeStr = formatToHourMinute(schedules[0].departsAt),
                            timeDiffStr = getRelativeTimeString(schedules[0].departsAt)
                        )
                    }
                }
                if(schedules.size > 1){
                    Column(
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        var showAll by remember { mutableStateOf(false) } // State to track visibility of all schedules
                        val maxVisibleSchedules = MAX_VISIBLE_SCHEDULES

                        val degrees by animateFloatAsState(if (showAll) -90f else 90f)

                        // Split the schedules into visible and hidden parts
                        val hiddenSchedules = schedules.drop(maxVisibleSchedules)

                        Text(
                            text = stringResource(id = R.string.next_departure),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.titleSmall,
                            modifier = Modifier.alpha(0.5f).padding(bottom = 8.dp)
                        )
                        Box(modifier = Modifier.fillMaxWidth()
                            .then(
                                if (hiddenSchedules.isNotEmpty()) {
                                    Modifier.clickable { showAll = !showAll }
                                } else {
                                    Modifier
                                }
                            ),) {
                            FlowRow(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(0.85f),
                                horizontalArrangement = Arrangement.spacedBy(36.dp),
                                verticalArrangement = Arrangement.Top,
                                maxItemsInEachRow = ROWS,
                            ) {
                                val maxVisibleSchedules = MAX_VISIBLE_SCHEDULES
                                val visibleSchedules = schedules.drop(1).take(maxVisibleSchedules)

                                visibleSchedules.mapIndexed { index, schedule ->
                                    if (index < maxVisibleSchedules) {
                                        NextDepartureItem(
                                            timeStr = formatToHourMinute(schedule.departsAt),
                                            timeDiffStr = getRelativeTimeString(schedule.departsAt)
                                        )
                                    }
                                }
                            }

                            // This Image is now overlaid, aligned to the right
                            if(hiddenSchedules.isNotEmpty()){
                                Image(
                                    Lucide.ChevronRight,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd) // like absolute right
                                        .alpha(0.5f)
                                        .padding(end = 8.dp)
                                        .rotate(degrees)
                                        .size(16.dp)
                                )
                            }
                        }

                        AnimatedVisibility(
                            visible =  showAll,
                            enter = fadeIn() + expandVertically(
                                spring(
                                    stiffness = Spring.StiffnessMediumLow,
                                    visibilityThreshold = IntSize.VisibilityThreshold
                                )
                            ),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            FlowRow(
                                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                maxItemsInEachRow = ROWS_OTHER
                            ) {
                                // Conditionally render the hidden schedules if "View More" is toggled
                                hiddenSchedules.forEach { schedule ->
                                    OtherTimeDepartureItem( // Use a different component for hidden items
                                        timeStr = formatToHourMinute(schedule.departsAt),
                                    )
                                }
                            }
                        }
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .alpha(0.1f)
                        .background(MaterialTheme.colorScheme.onBackground))
                Spacer(Modifier.height(8.dp))
            }
        }
         else {
//            Text(
//                text = stringResource(id = R.string.no_schedules_available),
//                color = MaterialTheme.colorScheme.onBackground,
//                style = Typography.titleSmall
//            )
        }
    }
}