package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.SleekCardBorder
import com.example.ui.theme.SleekCardSurface

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    borderColor: Color = SleekCardBorder,
    backgroundColor: Color = SleekCardSurface,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor, shape)
            .border(BorderStroke(1.dp, borderColor), shape)
    ) {
        content()
    }
}

