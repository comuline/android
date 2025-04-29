package com.comuline.app.ui.components
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comuline.app.R
import com.comuline.app.ui.theme.Typography

@Preview
@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    iconSize: Dp = 48.dp,
    iconPadding: Dp = 16.dp,
    iconTint: Color = MaterialTheme.colorScheme.onBackground,
    textStyle: TextStyle = Typography.titleMedium,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    contentDescription: String? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .padding(vertical = iconPadding),
            painter = painterResource(R.drawable.main_logo),
            tint = iconTint,
            contentDescription = contentDescription,
        )
        Text(
            text = stringResource(id = R.string.app_name),
            color = textColor,
            style = textStyle,
            fontSize = 20.sp
        )
    }
}
