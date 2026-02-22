package com.petshelter.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object PetShelterIcons {
    val Homepage: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Homepage",
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
                    moveTo(3f, 10.5f)
                    lineTo(10f, 4f)
                    lineTo(17f, 10.5f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(5f, 9f)
                    verticalLineTo(16f)
                    horizontalLineTo(8f)
                    verticalLineTo(12f)
                    horizontalLineTo(12f)
                    verticalLineTo(16f)
                    horizontalLineTo(15f)
                    verticalLineTo(9f)
                }
            }.build()
    }

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

    val StarOutline: ImageVector by lazy {
        // Same shape as Star but explicitly no fill (stroke only)
        Star
    }

    val SharedWithMe: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "SharedWithMe",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Left person circle
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    // circle cx=7, cy=6, r=2.5
                    moveTo(9.5f, 6f)
                    curveTo(9.5f, 7.38f, 8.38f, 8.5f, 7f, 8.5f)
                    curveTo(5.62f, 8.5f, 4.5f, 7.38f, 4.5f, 6f)
                    curveTo(4.5f, 4.62f, 5.62f, 3.5f, 7f, 3.5f)
                    curveTo(8.38f, 3.5f, 9.5f, 4.62f, 9.5f, 6f)
                    close()
                }
                // Left person body
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(2f, 16f)
                    curveTo(2f, 13.5f, 4f, 12f, 7f, 12f)
                    curveTo(10f, 12f, 12f, 13.5f, 12f, 16f)
                }
                // Right person circle
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    // circle cx=14, cy=7, r=2
                    moveTo(16f, 7f)
                    curveTo(16f, 8.1f, 15.1f, 9f, 14f, 9f)
                    curveTo(12.9f, 9f, 12f, 8.1f, 12f, 7f)
                    curveTo(12f, 5.9f, 12.9f, 5f, 14f, 5f)
                    curveTo(15.1f, 5f, 16f, 5.9f, 16f, 7f)
                    close()
                }
                // Right person body
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(13f, 12f)
                    curveTo(15f, 12f, 17f, 13f, 17f, 15f)
                }
            }.build()
    }

    val Drafts: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Drafts",
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
                    moveTo(4f, 4f)
                    curveTo(4f, 3.448f, 4.448f, 3f, 5f, 3f)
                    horizontalLineTo(11f)
                    lineTo(16f, 8f)
                    verticalLineTo(16f)
                    curveTo(16f, 16.552f, 15.552f, 17f, 15f, 17f)
                    horizontalLineTo(5f)
                    curveTo(4.448f, 17f, 4f, 16.552f, 4f, 16f)
                    verticalLineTo(4f)
                    close()
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(11f, 3f)
                    verticalLineTo(8f)
                    horizontalLineTo(16f)
                }
            }.build()
    }

    val Documents: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Documents",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(3f, 5f)
                    curveTo(3f, 3.895f, 3.895f, 3f, 5f, 3f)
                    horizontalLineTo(15f)
                    curveTo(16.105f, 3f, 17f, 3.895f, 17f, 5f)
                    verticalLineTo(15f)
                    curveTo(17f, 16.105f, 16.105f, 17f, 15f, 17f)
                    horizontalLineTo(5f)
                    curveTo(3.895f, 17f, 3f, 16.105f, 3f, 15f)
                    verticalLineTo(5f)
                    close()
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(3f, 7f)
                    horizontalLineTo(17f)
                }
            }.build()
    }

    val Trash: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Trash",
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
                    moveTo(4f, 6f)
                    horizontalLineTo(16f)
                    // Lid handle
                    moveTo(7f, 6f)
                    verticalLineTo(5f)
                    curveTo(7f, 4.448f, 7.448f, 4f, 8f, 4f)
                    horizontalLineTo(12f)
                    curveTo(12.552f, 4f, 13f, 4.448f, 13f, 5f)
                    verticalLineTo(6f)
                    // Body
                    moveTo(15f, 6f)
                    verticalLineTo(15f)
                    curveTo(15f, 16.105f, 14.105f, 17f, 13f, 17f)
                    horizontalLineTo(7f)
                    curveTo(5.895f, 17f, 5f, 16.105f, 5f, 15f)
                    verticalLineTo(6f)
                    horizontalLineTo(15f)
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

    val HelpCenter: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "HelpCenter",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Outer circle
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    // circle cx=10, cy=10, r=7
                    moveTo(17f, 10f)
                    curveTo(17f, 13.866f, 13.866f, 17f, 10f, 17f)
                    curveTo(6.134f, 17f, 3f, 13.866f, 3f, 10f)
                    curveTo(3f, 6.134f, 6.134f, 3f, 10f, 3f)
                    curveTo(13.866f, 3f, 17f, 6.134f, 17f, 10f)
                    close()
                }
                // Question mark
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(8f, 8f)
                    curveTo(8f, 6.895f, 8.895f, 6f, 10f, 6f)
                    curveTo(11.105f, 6f, 12f, 6.895f, 12f, 8f)
                    curveTo(12f, 9.105f, 11.105f, 10f, 10f, 10f)
                    verticalLineTo(11f)
                }
                // Dot
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    // circle cx=10, cy=13.5, r=0.75
                    moveTo(10.75f, 13.5f)
                    curveTo(10.75f, 13.914f, 10.414f, 14.25f, 10f, 14.25f)
                    curveTo(9.586f, 14.25f, 9.25f, 13.914f, 9.25f, 13.5f)
                    curveTo(9.25f, 13.086f, 9.586f, 12.75f, 10f, 12.75f)
                    curveTo(10.414f, 12.75f, 10.75f, 13.086f, 10.75f, 13.5f)
                    close()
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

    val CloudUpload: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "CloudUpload",
                defaultWidth = 64.dp,
                defaultHeight = 64.dp,
                viewportWidth = 64f,
                viewportHeight = 64f,
            ).apply {
                // Document body
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 2f,
                ) {
                    moveTo(12f, 8f)
                    curveTo(12f, 5.791f, 13.791f, 4f, 16f, 4f)
                    horizontalLineTo(48f)
                    curveTo(50.209f, 4f, 52f, 5.791f, 52f, 8f)
                    verticalLineTo(52f)
                    curveTo(52f, 54.209f, 50.209f, 56f, 48f, 56f)
                    horizontalLineTo(16f)
                    curveTo(13.791f, 56f, 12f, 54.209f, 12f, 52f)
                    verticalLineTo(8f)
                    close()
                }
                // Document lines
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 2f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(24f, 24f)
                    horizontalLineTo(40f)
                    moveTo(24f, 32f)
                    horizontalLineTo(40f)
                    moveTo(24f, 40f)
                    horizontalLineTo(32f)
                }
            }.build()
    }

    val CheckCircle: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "CheckCircle",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Circle
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(17f, 10f)
                    curveTo(17f, 13.866f, 13.866f, 17f, 10f, 17f)
                    curveTo(6.134f, 17f, 3f, 13.866f, 3f, 10f)
                    curveTo(3f, 6.134f, 6.134f, 3f, 10f, 3f)
                    curveTo(13.866f, 3f, 17f, 6.134f, 17f, 10f)
                    close()
                }
                // Checkmark
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(7f, 10f)
                    lineTo(9f, 12f)
                    lineTo(13f, 8f)
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

    val BulletList: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "BulletList",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f,
            ).apply {
                // Bullets (filled circles)
                path(fill = SolidColor(Color.Black)) {
                    // circle cx=3, cy=4, r=1.5
                    moveTo(4.5f, 4f)
                    curveTo(4.5f, 4.828f, 3.828f, 5.5f, 3f, 5.5f)
                    curveTo(2.172f, 5.5f, 1.5f, 4.828f, 1.5f, 4f)
                    curveTo(1.5f, 3.172f, 2.172f, 2.5f, 3f, 2.5f)
                    curveTo(3.828f, 2.5f, 4.5f, 3.172f, 4.5f, 4f)
                    close()
                }
                path(fill = SolidColor(Color.Black)) {
                    // circle cx=3, cy=8, r=1.5
                    moveTo(4.5f, 8f)
                    curveTo(4.5f, 8.828f, 3.828f, 9.5f, 3f, 9.5f)
                    curveTo(2.172f, 9.5f, 1.5f, 8.828f, 1.5f, 8f)
                    curveTo(1.5f, 7.172f, 2.172f, 6.5f, 3f, 6.5f)
                    curveTo(3.828f, 6.5f, 4.5f, 7.172f, 4.5f, 8f)
                    close()
                }
                path(fill = SolidColor(Color.Black)) {
                    // circle cx=3, cy=12, r=1.5
                    moveTo(4.5f, 12f)
                    curveTo(4.5f, 12.828f, 3.828f, 13.5f, 3f, 13.5f)
                    curveTo(2.172f, 13.5f, 1.5f, 12.828f, 1.5f, 12f)
                    curveTo(1.5f, 11.172f, 2.172f, 10.5f, 3f, 10.5f)
                    curveTo(3.828f, 10.5f, 4.5f, 11.172f, 4.5f, 12f)
                    close()
                }
                // Lines
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(6f, 4f)
                    horizontalLineTo(14f)
                    moveTo(6f, 8f)
                    horizontalLineTo(14f)
                    moveTo(6f, 12f)
                    horizontalLineTo(14f)
                }
            }.build()
    }

    val NumberedList: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "NumberedList",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f,
            ).apply {
                // Numbers as simple strokes
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.2f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    // "1"
                    moveTo(2.5f, 2.5f)
                    lineTo(3f, 2f)
                    verticalLineTo(5.5f)
                    // "2"
                    moveTo(1.8f, 7f)
                    curveTo(1.8f, 6.5f, 2.3f, 6f, 3f, 6f)
                    curveTo(3.7f, 6f, 4.2f, 6.5f, 4.2f, 7f)
                    curveTo(4.2f, 7.5f, 3.5f, 8.5f, 1.8f, 9.5f)
                    horizontalLineTo(4.2f)
                    // "3"
                    moveTo(1.8f, 10.5f)
                    horizontalLineTo(3.5f)
                    lineTo(2.5f, 12f)
                    curveTo(3.5f, 12f, 4.2f, 12.5f, 4.2f, 13f)
                    curveTo(4.2f, 13.5f, 3.7f, 14f, 3f, 14f)
                    curveTo(2.3f, 14f, 1.8f, 13.5f, 1.8f, 13f)
                }
                // Lines
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(6f, 4f)
                    horizontalLineTo(14f)
                    moveTo(6f, 8f)
                    horizontalLineTo(14f)
                    moveTo(6f, 12f)
                    horizontalLineTo(14f)
                }
            }.build()
    }

    val AlignLeft: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "AlignLeft",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(2f, 3f)
                    horizontalLineTo(14f)
                    moveTo(2f, 6f)
                    horizontalLineTo(10f)
                    moveTo(2f, 9f)
                    horizontalLineTo(14f)
                    moveTo(2f, 12f)
                    horizontalLineTo(8f)
                }
            }.build()
    }

    val AlignCenter: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "AlignCenter",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(2f, 3f)
                    horizontalLineTo(14f)
                    moveTo(4f, 6f)
                    horizontalLineTo(12f)
                    moveTo(2f, 9f)
                    horizontalLineTo(14f)
                    moveTo(5f, 12f)
                    horizontalLineTo(11f)
                }
            }.build()
    }

    val AlignRight: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "AlignRight",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(2f, 3f)
                    horizontalLineTo(14f)
                    moveTo(6f, 6f)
                    horizontalLineTo(14f)
                    moveTo(2f, 9f)
                    horizontalLineTo(14f)
                    moveTo(8f, 12f)
                    horizontalLineTo(14f)
                }
            }.build()
    }

    val Menu: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Menu",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(3f, 6f)
                    horizontalLineTo(17f)
                    moveTo(3f, 10f)
                    horizontalLineTo(17f)
                    moveTo(3f, 14f)
                    horizontalLineTo(17f)
                }
            }.build()
    }

    val ViewSidebar: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "ViewSidebar",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Top-left rect
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(4f, 3f)
                    horizontalLineTo(7f)
                    curveTo(7.552f, 3f, 8f, 3.448f, 8f, 4f)
                    verticalLineTo(8f)
                    curveTo(8f, 8.552f, 7.552f, 9f, 7f, 9f)
                    horizontalLineTo(4f)
                    curveTo(3.448f, 9f, 3f, 8.552f, 3f, 8f)
                    verticalLineTo(4f)
                    curveTo(3f, 3.448f, 3.448f, 3f, 4f, 3f)
                    close()
                }
                // Bottom-left rect
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(4f, 11f)
                    horizontalLineTo(7f)
                    curveTo(7.552f, 11f, 8f, 11.448f, 8f, 12f)
                    verticalLineTo(16f)
                    curveTo(8f, 16.552f, 7.552f, 17f, 7f, 17f)
                    horizontalLineTo(4f)
                    curveTo(3.448f, 17f, 3f, 16.552f, 3f, 16f)
                    verticalLineTo(12f)
                    curveTo(3f, 11.448f, 3.448f, 11f, 4f, 11f)
                    close()
                }
                // Right lines
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(10f, 5f)
                    horizontalLineTo(17f)
                    moveTo(10f, 10f)
                    horizontalLineTo(17f)
                    moveTo(10f, 15f)
                    horizontalLineTo(15f)
                }
            }.build()
    }

    val ZoomIn: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "ZoomIn",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f,
            ).apply {
                // Magnifying glass circle
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(12f, 7f)
                    curveTo(12f, 9.761f, 9.761f, 12f, 7f, 12f)
                    curveTo(4.239f, 12f, 2f, 9.761f, 2f, 7f)
                    curveTo(2f, 4.239f, 4.239f, 2f, 7f, 2f)
                    curveTo(9.761f, 2f, 12f, 4.239f, 12f, 7f)
                    close()
                }
                // Handle
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(11f, 11f)
                    lineTo(14f, 14f)
                }
                // Plus
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(5f, 7f)
                    horizontalLineTo(9f)
                    moveTo(7f, 5f)
                    verticalLineTo(9f)
                }
            }.build()
    }

    val SidebarCollapse: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "SidebarCollapse",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Outer frame
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(4f, 3f)
                    horizontalLineTo(16f)
                    curveTo(16.552f, 3f, 17f, 3.448f, 17f, 4f)
                    verticalLineTo(16f)
                    curveTo(17f, 16.552f, 16.552f, 17f, 16f, 17f)
                    horizontalLineTo(4f)
                    curveTo(3.448f, 17f, 3f, 16.552f, 3f, 16f)
                    verticalLineTo(4f)
                    curveTo(3f, 3.448f, 3.448f, 3f, 4f, 3f)
                    close()
                }
                // Vertical divider
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(8f, 3f)
                    verticalLineTo(17f)
                }
                // Left arrow (chevron pointing left)
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(14f, 8f)
                    lineTo(11.5f, 10f)
                    lineTo(14f, 12f)
                }
            }.build()
    }

    val SidebarExpand: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "SidebarExpand",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Outer frame
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(4f, 3f)
                    horizontalLineTo(16f)
                    curveTo(16.552f, 3f, 17f, 3.448f, 17f, 4f)
                    verticalLineTo(16f)
                    curveTo(17f, 16.552f, 16.552f, 17f, 16f, 17f)
                    horizontalLineTo(4f)
                    curveTo(3.448f, 17f, 3f, 16.552f, 3f, 16f)
                    verticalLineTo(4f)
                    curveTo(3f, 3.448f, 3.448f, 3f, 4f, 3f)
                    close()
                }
                // Vertical divider
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(8f, 3f)
                    verticalLineTo(17f)
                }
                // Right arrow (chevron pointing right)
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(11.5f, 8f)
                    lineTo(14f, 10f)
                    lineTo(11.5f, 12f)
                }
            }.build()
    }

    val Sparkle: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Sparkle",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(10f, 2f)
                    lineTo(11.5f, 7.5f)
                    lineTo(17f, 10f)
                    lineTo(11.5f, 12.5f)
                    lineTo(10f, 18f)
                    lineTo(8.5f, 12.5f)
                    lineTo(3f, 10f)
                    lineTo(8.5f, 7.5f)
                    close()
                }
            }.build()
    }

    val Whiteboard: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Whiteboard",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Board
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(3f, 4f)
                    horizontalLineTo(17f)
                    verticalLineTo(13f)
                    horizontalLineTo(3f)
                    verticalLineTo(4f)
                    close()
                }
                // Stand legs
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(7f, 13f)
                    lineTo(5f, 17f)
                    moveTo(13f, 13f)
                    lineTo(15f, 17f)
                    moveTo(10f, 13f)
                    verticalLineTo(15f)
                }
            }.build()
    }

    val DocumentText: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "DocumentText",
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
                    moveTo(5f, 3f)
                    horizontalLineTo(15f)
                    curveTo(15.552f, 3f, 16f, 3.448f, 16f, 4f)
                    verticalLineTo(16f)
                    curveTo(16f, 16.552f, 15.552f, 17f, 15f, 17f)
                    horizontalLineTo(5f)
                    curveTo(4.448f, 17f, 4f, 16.552f, 4f, 16f)
                    verticalLineTo(4f)
                    curveTo(4f, 3.448f, 4.448f, 3f, 5f, 3f)
                    close()
                }
                // Text lines
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(7f, 7f)
                    horizontalLineTo(13f)
                    moveTo(7f, 10f)
                    horizontalLineTo(13f)
                    moveTo(7f, 13f)
                    horizontalLineTo(10f)
                }
            }.build()
    }

    val RecordCircle: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "RecordCircle",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Outer circle
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(17f, 10f)
                    curveTo(17f, 13.866f, 13.866f, 17f, 10f, 17f)
                    curveTo(6.134f, 17f, 3f, 13.866f, 3f, 10f)
                    curveTo(3f, 6.134f, 6.134f, 3f, 10f, 3f)
                    curveTo(13.866f, 3f, 17f, 6.134f, 17f, 10f)
                    close()
                }
                // Inner filled circle
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(13f, 10f)
                    curveTo(13f, 11.657f, 11.657f, 13f, 10f, 13f)
                    curveTo(8.343f, 13f, 7f, 11.657f, 7f, 10f)
                    curveTo(7f, 8.343f, 8.343f, 7f, 10f, 7f)
                    curveTo(11.657f, 7f, 13f, 8.343f, 13f, 10f)
                    close()
                }
            }.build()
    }

    val DownloadArrow: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "DownloadArrow",
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
                    // Arrow down
                    moveTo(10f, 3f)
                    verticalLineTo(13f)
                    moveTo(6f, 10f)
                    lineTo(10f, 14f)
                    lineTo(14f, 10f)
                    // Baseline
                    moveTo(4f, 17f)
                    horizontalLineTo(16f)
                }
            }.build()
    }

    val Share: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Share",
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
                    // Upward arrow
                    moveTo(10f, 3f)
                    verticalLineTo(12f)
                    moveTo(6f, 6f)
                    lineTo(10f, 2f)
                    lineTo(14f, 6f)
                }
                // Box
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(6f, 9f)
                    horizontalLineTo(4f)
                    verticalLineTo(17f)
                    horizontalLineTo(16f)
                    verticalLineTo(9f)
                    horizontalLineTo(14f)
                }
            }.build()
    }

    val MoreVert: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "MoreVert",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Top dot
                path(fill = SolidColor(Color.Black)) {
                    moveTo(11.5f, 5f)
                    curveTo(11.5f, 5.828f, 10.828f, 6.5f, 10f, 6.5f)
                    curveTo(9.172f, 6.5f, 8.5f, 5.828f, 8.5f, 5f)
                    curveTo(8.5f, 4.172f, 9.172f, 3.5f, 10f, 3.5f)
                    curveTo(10.828f, 3.5f, 11.5f, 4.172f, 11.5f, 5f)
                    close()
                }
                // Middle dot
                path(fill = SolidColor(Color.Black)) {
                    moveTo(11.5f, 10f)
                    curveTo(11.5f, 10.828f, 10.828f, 11.5f, 10f, 11.5f)
                    curveTo(9.172f, 11.5f, 8.5f, 10.828f, 8.5f, 10f)
                    curveTo(8.5f, 9.172f, 9.172f, 8.5f, 10f, 8.5f)
                    curveTo(10.828f, 8.5f, 11.5f, 9.172f, 11.5f, 10f)
                    close()
                }
                // Bottom dot
                path(fill = SolidColor(Color.Black)) {
                    moveTo(11.5f, 15f)
                    curveTo(11.5f, 15.828f, 10.828f, 16.5f, 10f, 16.5f)
                    curveTo(9.172f, 16.5f, 8.5f, 15.828f, 8.5f, 15f)
                    curveTo(8.5f, 14.172f, 9.172f, 13.5f, 10f, 13.5f)
                    curveTo(10.828f, 13.5f, 11.5f, 14.172f, 11.5f, 15f)
                    close()
                }
            }.build()
    }

    val TextHighlight: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "TextHighlight",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(10f, 4f)
                    verticalLineTo(14f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(7f, 4f)
                    horizontalLineTo(13f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(7f, 14f)
                    horizontalLineTo(13f)
                }
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 0.3f,
                ) {
                    moveTo(5f, 15f)
                    horizontalLineTo(15f)
                    verticalLineTo(18f)
                    horizontalLineTo(5f)
                    close()
                }
            }.build()
    }

    val TextUnderline: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "TextUnderline",
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
                    moveTo(6.96f, 9.85f)
                    horizontalLineTo(13.04f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(4.94f, 13.9f)
                    lineTo(9.26f, 4.39f)
                    curveTo(9.5f, 3.87f, 9.61f, 3.61f, 9.78f, 3.53f)
                    curveTo(9.92f, 3.46f, 10.08f, 3.46f, 10.22f, 3.53f)
                    curveTo(10.39f, 3.61f, 10.5f, 3.87f, 10.74f, 4.39f)
                    lineTo(15.06f, 13.9f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(17.08f, 17.94f)
                    horizontalLineTo(2.92f)
                }
            }.build()
    }

    val Strikethrough: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Strikethrough",
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
                    moveTo(5f, 13.33f)
                    curveTo(5f, 15.17f, 6.49f, 16.67f, 8.33f, 16.67f)
                    horizontalLineTo(11.67f)
                    curveTo(13.51f, 16.67f, 15f, 15.17f, 15f, 13.33f)
                    curveTo(15f, 11.49f, 13.51f, 10f, 11.67f, 10f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(15f, 6.67f)
                    curveTo(15f, 4.83f, 13.51f, 3.33f, 11.67f, 3.33f)
                    horizontalLineTo(8.33f)
                    curveTo(6.49f, 3.33f, 5f, 4.83f, 5f, 6.67f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(2.5f, 10f)
                    horizontalLineTo(17.5f)
                }
            }.build()
    }

    val Highlighter: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Highlighter",
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
                    moveTo(3f, 14f)
                    lineTo(14f, 3f)
                    lineTo(17f, 6f)
                    lineTo(6f, 17f)
                    horizontalLineTo(3f)
                    verticalLineTo(14f)
                    close()
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(11f, 6f)
                    lineTo(14f, 9f)
                }
            }.build()
    }

    val Pen: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Pen",
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
                    moveTo(14.5f, 3.5f)
                    lineTo(16.5f, 5.5f)
                    lineTo(6f, 16f)
                    horizontalLineTo(4f)
                    verticalLineTo(14f)
                    lineTo(14.5f, 3.5f)
                    close()
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
                // Dog head and ear
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
                // Nose
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
                // Eyes
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
                // Cat head with ears
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
                // Eyes
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
                // Nose
                path(
                    fill = SolidColor(Color.Black),
                ) {
                    moveTo(10f, 11f)
                    lineTo(9.5f, 11.5f)
                    lineTo(10f, 12f)
                    lineTo(10.5f, 11.5f)
                    close()
                }
                // Whiskers
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
                // Main building
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
                // Extension building
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
                // Baseline
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(2f, 17f)
                    horizontalLineTo(18f)
                }
                // Windows
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
                // Door
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

    val Eraser: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Eraser",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(7f, 16f)
                    horizontalLineTo(17f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(3f, 13f)
                    lineTo(11f, 5f)
                    lineTo(15f, 9f)
                    lineTo(7f, 17f)
                    horizontalLineTo(3f)
                    verticalLineTo(13f)
                    close()
                }
            }.build()
    }

    val Undo: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Undo",
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
                    moveTo(4f, 8f)
                    lineTo(7f, 5f)
                    moveTo(4f, 8f)
                    lineTo(7f, 11f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(4f, 8f)
                    horizontalLineTo(12f)
                    curveTo(14.21f, 8f, 16f, 9.79f, 16f, 12f)
                    curveTo(16f, 14.21f, 14.21f, 16f, 12f, 16f)
                    horizontalLineTo(10f)
                }
            }.build()
    }

    val Redo: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "Redo",
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
                    moveTo(16f, 8f)
                    lineTo(13f, 5f)
                    moveTo(16f, 8f)
                    lineTo(13f, 11f)
                }
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(16f, 8f)
                    horizontalLineTo(8f)
                    curveTo(5.79f, 8f, 4f, 9.79f, 4f, 12f)
                    curveTo(4f, 14.21f, 5.79f, 16f, 8f, 16f)
                    horizontalLineTo(10f)
                }
            }.build()
    }

    val BugReport: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "BugReport",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f,
            ).apply {
                // Bug body
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineJoin = StrokeJoin.Round,
                ) {
                    moveTo(7f, 8f)
                    curveTo(7f, 6.343f, 8.343f, 5f, 10f, 5f)
                    curveTo(11.657f, 5f, 13f, 6.343f, 13f, 8f)
                    verticalLineTo(13f)
                    curveTo(13f, 14.657f, 11.657f, 16f, 10f, 16f)
                    curveTo(8.343f, 16f, 7f, 14.657f, 7f, 13f)
                    verticalLineTo(8f)
                    close()
                }
                // Antennae
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(8.5f, 5f)
                    lineTo(7f, 3f)
                    moveTo(11.5f, 5f)
                    lineTo(13f, 3f)
                }
                // Legs
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(7f, 9f)
                    lineTo(4f, 8f)
                    moveTo(7f, 12f)
                    lineTo(4f, 13f)
                    moveTo(13f, 9f)
                    lineTo(16f, 8f)
                    moveTo(13f, 12f)
                    lineTo(16f, 13f)
                }
                // Middle line
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(7f, 10.5f)
                    horizontalLineTo(13f)
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

    val ZoomOut: ImageVector by lazy {
        ImageVector
            .Builder(
                name = "ZoomOut",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 16f,
                viewportHeight = 16f,
            ).apply {
                // Magnifying glass circle
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                ) {
                    moveTo(12f, 7f)
                    curveTo(12f, 9.761f, 9.761f, 12f, 7f, 12f)
                    curveTo(4.239f, 12f, 2f, 9.761f, 2f, 7f)
                    curveTo(2f, 4.239f, 4.239f, 2f, 7f, 2f)
                    curveTo(9.761f, 2f, 12f, 4.239f, 12f, 7f)
                    close()
                }
                // Handle
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(11f, 11f)
                    lineTo(14f, 14f)
                }
                // Minus
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                ) {
                    moveTo(5f, 7f)
                    horizontalLineTo(9f)
                }
            }.build()
    }
}
