package com.myapp.jetsnack.ui.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myapp.jetsnack.ui.theme.JetsnackTheme

@Composable
fun JetsnackDivider(
    modifier: Modifier = Modifier,
    color: Color = JetsnackTheme.colors.uiBorder.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp,
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness,
    )
}

private const val DividerAlpha = 0.12f
