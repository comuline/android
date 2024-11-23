package com.comuline.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.comuline.app.R
import com.comuline.app.ui.theme.Typography

@Composable
fun Header(
    showAddIcon: Boolean = true,
    showSearchIcon: Boolean = true,
    showBackButton: Boolean = false,
    searchDisplay: String = "",
    onSearchDisplayChanged: (String) -> Unit = { },
    onSearchDisplayClosed: () -> Unit = { },
    onExpandedChanged: (Boolean) -> Unit = { },
    onAddButtonTap: () -> Unit = { },
    onBackButtonTap: () -> Unit = { },
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val textFieldFocusRequester = remember { FocusRequester() }
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(searchDisplay, TextRange(searchDisplay.length)))
    }

    fun onSearchToggle() {
        if(expanded){
            onSearchDisplayClosed()
        }
        expanded = expanded.not()
    }

    fun onAddButton() {
        onAddButtonTap()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (showBackButton){
                    Icon(
                        modifier = Modifier
                            .size(72.dp)
                            .padding(vertical = 16.dp)
                            .clickable { onBackButtonTap() }
                        ,
                        painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                    )
                } else {
                    AppLogo()
                }
                Spacer(modifier = Modifier.weight(1.0f))
                if(showSearchIcon){
                    Icon(
                        modifier = Modifier
                            .size(72.dp)
                            .padding(vertical = 16.dp)
                            .clickable { onSearchToggle() }
                        ,
                        painter = if (expanded) painterResource(R.drawable.baseline_close_24) else painterResource(R.drawable.baseline_search_24),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                    )
                }
                if (showAddIcon && !expanded){
                    Icon(
                        modifier = Modifier
                            .size(72.dp)
                            .padding(vertical = 16.dp)
                            .clickable { onAddButton() }
                        ,
                        painter = painterResource(R.drawable.baseline_add_24),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntSize.VisibilityThreshold
                    )
                ),
                exit = fadeOut() + shrinkVertically()
            ) {
                TextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        onSearchDisplayChanged(it.text)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(textFieldFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
                SideEffect {
                    textFieldFocusRequester.requestFocus()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewWithAdd() {
    Header()
}

@Preview
@Composable
fun PreviewWithoutAdd() {
    Header(
        showAddIcon = false
    )
}

@Preview
@Composable
fun HeaderWithBackButton() {
    Header(
        showBackButton = true,
        showAddIcon = true,
        showSearchIcon = true
    )
}