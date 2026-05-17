package com.nammahasiru.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SurvivalPieChart(
    survivalPercent: Int,
    modifier: Modifier = Modifier
) {
    val clamped = survivalPercent.coerceIn(0, 100)
    val survivedAngle = 360f * (clamped / 100f)

    val survivedColor = MaterialTheme.colorScheme.primary
    val otherColor = MaterialTheme.colorScheme.surfaceVariant
    val ringColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)

    Canvas(modifier = modifier.size(120.dp)) {
        val stroke = Stroke(width = 18.dp.toPx())
        val diameter = size.minDimension
        val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
        val arcSize = Size(diameter, diameter)

        // Background (not survived / unknown)
        drawArc(
            color = otherColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = stroke
        )

        // Survived section
        drawArc(
            color = survivedColor,
            startAngle = -90f,
            sweepAngle = survivedAngle,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = stroke
        )

        // Thin ring edge
        drawCircle(
            color = ringColor,
            radius = diameter / 2f,
            center = Offset(size.width / 2f, size.height / 2f),
            style = Stroke(width = 1.dp.toPx())
        )
    }
}

