package com.example.yemekcim.data.entity

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val route: String? = null,
    val selectedVectorIcon: ImageVector? = null,
    val unselectedVectorIcon: ImageVector? = null,
    val selectedPainterIcon: Painter? = null,
    val unselectedPainterIcon: Painter? = null
)
