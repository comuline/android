package com.comuline.app.ui.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.comuline.app.R
import com.comuline.app.domain.GROUPED_SCHEDULES_SAMPLES
import com.comuline.app.domain.GroupedSchedule
import com.comuline.app.ui.theme.Typography
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ScheduleItem(
    stationName: String = "Bekasi",
    groupedSchedule: GroupedSchedule = GROUPED_SCHEDULES_SAMPLES,
    isLoading: Boolean = false)
{
    var expanded by remember { mutableStateOf(true) }
    val degrees by animateFloatAsState(if (expanded) -90f else 90f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Column {
            Row(
                modifier = Modifier.clickable { if(!isLoading) expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.station),
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
                if(isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.width(32.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                } else {
                    Image(
                        Lucide.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier.rotate(degrees),
                    )
                }
            }
            AnimatedVisibility(
                visible = if(!isLoading) expanded else false,
                enter = expandVertically(
                    spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntSize.VisibilityThreshold
                    )
                ),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column (
                    Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(top = 12.dp))) {
                    groupedSchedule.map { destination ->
                        destination.value.map { schedule ->
                            DestinationItem(
                                colorStr = destination.key.split("-")[1],
                                stationName = schedule.key,
                                schedules = schedule.value
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.tertiary))
        }
    }
}