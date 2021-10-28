package com.xtanion.splashwalls.utils

import android.util.DisplayMetrics
import com.xtanion.splashwalls.data.category.Category
import com.xtanion.splashwalls.data.photo.Nature

val DISPLAY_HEIGHT = DisplayMetrics().heightPixels
val DISPLAY_WIDTH = DisplayMetrics().widthPixels
const val BOTTOM_SHEET_PEEK_HEIGHT = 400

val GradientWall = Category("Gradient","https://images.unsplash.com/photo-1553356084-58ef4a67b2a7?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=480&q=80","gradient")
val MinimalWall = Category("Minimal","https://images.unsplash.com/photo-1495476479092-6ece1898a101?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwyMjgyMTR8MHwxfHNlYXJjaHwxfHxtaW5pbWFsfGVufDB8fHx8MTYzNTMzODU4MQ&ixlib=rb-1.2.1&q=80&w=480","minimal")
val NatureWall = Category("Nature","https://images.unsplash.com/photo-1501854140801-50d01698950b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=480&q=80","nature")
val MountainWall = Category("Mountain","https://images.unsplash.com/photo-1530569673472-307dc017a82d?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=480&q=80","mountain")
val FogWall = Category("Fog","https://images.unsplash.com/photo-1557683316-973673baf926?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwyMjgyMTR8MHwxfHNlYXJjaHwzfHxncmFkaWVudHxlbnwwfHx8fDE2MzUzMzY5MzU&ixlib=rb-1.2.1&q=80&w=480","fog")
val ForestWall = Category("Forest","https://images.unsplash.com/photo-1503435980610-a51f3ddfee50?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80","forest")
val GeometryWall = Category("Geometry","https://images.unsplash.com/photo-1554965650-378bcfce5cac?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1080s&q=80","geometry")
val ArchitectureWall = Category("Architecture","https://images.unsplash.com/photo-1620937170928-a03bb6261277?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=480&q=80","architecture")

val categories: List<Category> = listOf(GradientWall,
    MinimalWall, NatureWall, MountainWall, ForestWall, FogWall, GeometryWall, ArchitectureWall)