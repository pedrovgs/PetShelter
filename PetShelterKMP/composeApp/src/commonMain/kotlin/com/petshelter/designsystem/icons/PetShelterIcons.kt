package com.petshelter.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object PetShelterIcons {
    val Star: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Star",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(10f, 3f)
                    lineTo(12.09f, 7.26f)
                    lineTo(17f, 7.97f)
                    lineTo(13.5f, 11.35f)
                    lineTo(14.18f, 16.23f)
                    lineTo(10f, 14.02f)
                    lineTo(5.82f, 16.23f)
                    lineTo(6.5f, 11.35f)
                    lineTo(3f, 7.97f)
                    lineTo(7.91f, 7.26f)
                    lineTo(10f, 3f)
                    close()
                }
            }.build()
    }

    val ChevronDown: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "ChevronDown",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(4f, 6f)
                    lineTo(8f, 10f)
                    lineTo(12f, 6f)
                }
            }.build()
    }

    val Close: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Close",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 2f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(15f, 5f)
                    lineTo(5f, 15f)
                    moveTo(5f, 5f)
                    lineTo(15f, 15f)
                }
            }.build()
    }

    val ArrowBack: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "ArrowBack",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 2f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(12f, 15f)
                    lineTo(7f, 10f)
                    lineTo(12f, 5f)
                }
            }.build()
    }

    val Heart: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Heart",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(10f, 17f)
                    curveTo(10f, 17f, 2.5f, 12.5f, 2.5f, 7.5f)
                    curveTo(2.5f, 5.5f, 4f, 3.5f, 6.25f, 3.5f)
                    curveTo(8f, 3.5f, 9.5f, 4.5f, 10f, 6f)
                    curveTo(10.5f, 4.5f, 12f, 3.5f, 13.75f, 3.5f)
                    curveTo(16f, 3.5f, 17.5f, 5.5f, 17.5f, 7.5f)
                    curveTo(17.5f, 12.5f, 10f, 17f, 10f, 17f)
                    close()
                }
            }.build()
    }

    val Dog: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Dog",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(5f, 8f)
                    lineTo(3f, 4f)
                    lineTo(6f, 7f)
                    curveTo(7f, 5f, 9f, 4f, 10f, 4f)
                    curveTo(11f, 4f, 13f, 5f, 14f, 7f)
                    lineTo(17f, 4f)
                    lineTo(15f, 8f)
                    curveTo(15.5f, 9f, 16f, 10f, 16f, 11f)
                    curveTo(16f, 13.5f, 13.5f, 15f, 10f, 15f)
                    curveTo(6.5f, 15f, 4f, 13.5f, 4f, 11f)
                    curveTo(4f, 10f, 4.5f, 9f, 5f, 8f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(10f, 10f)
                    curveTo(10f, 10f, 9f, 10.5f, 9f, 11f)
                    curveTo(9f, 11.5f, 9.5f, 12f, 10f, 12f)
                    curveTo(10.5f, 12f, 11f, 11.5f, 11f, 11f)
                    curveTo(11f, 10.5f, 10f, 10f, 10f, 10f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(8.25f, 8.5f)
                    curveTo(8.25f, 8.914f, 7.914f, 9.25f, 7.5f, 9.25f)
                    curveTo(7.086f, 9.25f, 6.75f, 8.914f, 6.75f, 8.5f)
                    curveTo(6.75f, 8.086f, 7.086f, 7.75f, 7.5f, 7.75f)
                    curveTo(7.914f, 7.75f, 8.25f, 8.086f, 8.25f, 8.5f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(13.25f, 8.5f)
                    curveTo(13.25f, 8.914f, 12.914f, 9.25f, 12.5f, 9.25f)
                    curveTo(12.086f, 9.25f, 11.75f, 8.914f, 11.75f, 8.5f)
                    curveTo(11.75f, 8.086f, 12.086f, 7.75f, 12.5f, 7.75f)
                    curveTo(12.914f, 7.75f, 13.25f, 8.086f, 13.25f, 8.5f)
                    close()
                }
            }.build()
    }

    val Cat: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Cat",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(4f, 10f)
                    lineTo(3f, 4f)
                    lineTo(7f, 7f)
                    curveTo(8f, 6f, 9f, 5.5f, 10f, 5.5f)
                    curveTo(11f, 5.5f, 12f, 6f, 13f, 7f)
                    lineTo(17f, 4f)
                    lineTo(16f, 10f)
                    curveTo(16f, 13f, 13.5f, 15.5f, 10f, 15.5f)
                    curveTo(6.5f, 15.5f, 4f, 13f, 4f, 10f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(8.25f, 9.5f)
                    curveTo(8.25f, 9.914f, 7.914f, 10.25f, 7.5f, 10.25f)
                    curveTo(7.086f, 10.25f, 6.75f, 9.914f, 6.75f, 9.5f)
                    curveTo(6.75f, 9.086f, 7.086f, 8.75f, 7.5f, 8.75f)
                    curveTo(7.914f, 8.75f, 8.25f, 9.086f, 8.25f, 9.5f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(13.25f, 9.5f)
                    curveTo(13.25f, 9.914f, 12.914f, 10.25f, 12.5f, 10.25f)
                    curveTo(12.086f, 10.25f, 11.75f, 9.914f, 11.75f, 9.5f)
                    curveTo(11.75f, 9.086f, 12.086f, 8.75f, 12.5f, 8.75f)
                    curveTo(12.914f, 8.75f, 13.25f, 9.086f, 13.25f, 9.5f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(10f, 11f)
                    lineTo(9.5f, 11.5f)
                    lineTo(10f, 12f)
                    lineTo(10.5f, 11.5f)
                    close()
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(6f, 11.5f)
                    lineTo(3.5f, 11f)
                    moveTo(6f, 12.5f)
                    lineTo(3.5f, 13f)
                    moveTo(14f, 11.5f)
                    lineTo(16.5f, 11f)
                    moveTo(14f, 12.5f)
                    lineTo(16.5f, 13f)
                }
            }.build()
    }

    val Building: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Building",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(3f, 17f)
                    verticalLineTo(5f)
                    horizontalLineTo(12f)
                    verticalLineTo(17f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(12f, 9f)
                    horizontalLineTo(17f)
                    verticalLineTo(17f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(2f, 17f)
                    horizontalLineTo(18f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(5.5f, 8f)
                    verticalLineTo(9.5f)
                    moveTo(7.5f, 8f)
                    verticalLineTo(9.5f)
                    moveTo(9.5f, 8f)
                    verticalLineTo(9.5f)
                    moveTo(5.5f, 11.5f)
                    verticalLineTo(13f)
                    moveTo(7.5f, 11.5f)
                    verticalLineTo(13f)
                    moveTo(9.5f, 11.5f)
                    verticalLineTo(13f)
                    moveTo(14f, 11.5f)
                    verticalLineTo(13f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(6f, 17f)
                    verticalLineTo(14.5f)
                    horizontalLineTo(9f)
                    verticalLineTo(17f)
                }
            }.build()
    }

    val Phone: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Phone",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(3f, 5f)
                    curveTo(3f, 4f, 3.5f, 3f, 5f, 3f)
                    horizontalLineTo(7f)
                    lineTo(8.5f, 7f)
                    lineTo(7f, 8f)
                    curveTo(7f, 8f, 7.5f, 10.5f, 9.5f, 12.5f)
                    curveTo(11.5f, 14.5f, 14f, 15f, 14f, 15f)
                    lineTo(15f, 13.5f)
                    lineTo(17f, 15f)
                    curveTo(17f, 16.5f, 16f, 17f, 15f, 17f)
                    curveTo(11f, 17f, 3f, 11f, 3f, 5f)
                    close()
                }
            }.build()
    }

    val PlayArrow: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "PlayArrow",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(8f, 5f)
                    lineTo(19f, 12f)
                    lineTo(8f, 19f)
                    close()
                }
            }.build()
    }
}
