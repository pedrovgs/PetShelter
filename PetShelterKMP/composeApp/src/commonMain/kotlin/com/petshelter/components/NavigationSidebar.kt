package com.petshelter.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petshelter.designsystem.AnimationDuration
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Layout
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing
import com.petshelter.designsystem.icons.PetShelterIcons
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.app_name
import petshelter.composeapp.generated.resources.petshelter_logo
import petshelter.composeapp.generated.resources.nav_homepage
import petshelter.composeapp.generated.resources.sidebar_version
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

enum class SidebarItem {
    Homepage,
}

@Composable
fun NavigationSidebar(
    selectedItem: SidebarItem,
    onItemSelected: (SidebarItem) -> Unit,
    modifier: Modifier = Modifier,
    collapsed: Boolean = false,
    onToggleCollapsed: (() -> Unit)? = null,
) {
    val borderColor = PetShelterTheme.colors.BorderLight

    val sidebarWidth by animateDpAsState(
        targetValue = if (collapsed) Layout.SidebarCollapsedWidth else Layout.SidebarWidth,
        animationSpec = tween(AnimationDuration.NORMAL),
    )

    Column(
        modifier =
            modifier
                .width(sidebarWidth)
                .fillMaxHeight()
                .background(PetShelterTheme.colors.BackgroundPrimary)
                .drawBehind {
                    drawLine(
                        color = borderColor,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx(),
                    )
                }.padding(vertical = Spacing.Large, horizontal = if (collapsed) Spacing.Small else Spacing.Large),
    ) {
        // Logo + toggle button
        if (collapsed) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(Res.drawable.petshelter_logo),
                    contentDescription = stringResource(Res.string.app_name),
                    modifier =
                        Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(Radii.Medium))
                            .clickable { onToggleCollapsed?.invoke() },
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = Spacing.XSmall),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(Res.drawable.petshelter_logo),
                    contentDescription = stringResource(Res.string.app_name),
                    modifier =
                        Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(Radii.Medium))
                            .clickable { onToggleCollapsed?.invoke() },
                )
                Spacer(Modifier.width(Spacing.Small))
                Text(
                    text = stringResource(Res.string.app_name),
                    style = PetShelterTypography.Heading3.copy(fontWeight = FontWeight.SemiBold),
                    color = PetShelterTheme.colors.TextPrimary,
                    modifier = Modifier.weight(1f).graphicsLayer { alpha = if (collapsed) 0f else 1f },
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                )
            }
        }

        Spacer(Modifier.height(Spacing.XLarge))

        // Navigation items
        Column(verticalArrangement = Arrangement.spacedBy(Spacing.XSmall)) {
            SidebarNavItem(
                icon = PetShelterIcons.Homepage,
                label = stringResource(Res.string.nav_homepage),
                selected = selectedItem == SidebarItem.Homepage,
                onClick = { onItemSelected(SidebarItem.Homepage) },
                collapsed = collapsed,
            )
        }

        Spacer(Modifier.weight(1f))

        // Footer
        HorizontalDivider(color = PetShelterTheme.colors.BorderLight)
        Spacer(Modifier.height(Spacing.Large))

        // Version number
        Text(
            text = stringResource(Res.string.sidebar_version, "1.0.0"),
            style = PetShelterTypography.Caption,
            color = PetShelterTheme.colors.TextTertiary,
            modifier =
                if (collapsed) {
                    Modifier.align(Alignment.CenterHorizontally)
                } else {
                    Modifier.padding(horizontal = Spacing.Medium)
                },
            maxLines = 1,
        )
    }
}

@Composable
private fun SidebarNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    collapsed: Boolean,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor by animateColorAsState(
        targetValue =
            when {
                selected -> PetShelterTheme.colors.BackgroundTertiary
                isHovered -> PetShelterTheme.colors.BackgroundTertiary
                else -> PetShelterTheme.colors.BackgroundPrimary
            },
        animationSpec = tween(AnimationDuration.FAST),
    )

    val contentColor = if (selected) PetShelterTheme.colors.TextPrimary else PetShelterTheme.colors.TextSecondary

    if (collapsed) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Radii.Medium))
                    .background(backgroundColor)
                    .clickable(interactionSource = interactionSource, indication = null) { onClick() }
                    .padding(vertical = Spacing.Small),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(20.dp),
                tint = contentColor,
            )
        }
    } else {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Radii.Medium))
                    .background(backgroundColor)
                    .clickable(interactionSource = interactionSource, indication = null) { onClick() }
                    .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = contentColor,
            )
            Spacer(Modifier.width(Spacing.Medium))
            Text(
                text = label,
                style =
                    PetShelterTypography.Body.copy(
                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                    ),
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Clip,
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedItem: SidebarItem,
    onItemSelected: (SidebarItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = PetShelterTheme.colors.BorderLight

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(Layout.BottomBarHeight + Spacing.Small)
                .background(PetShelterTheme.colors.BackgroundPrimary)
                .drawBehind {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx(),
                    )
                }.padding(horizontal = Spacing.Medium),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BottomNavItem(
            icon = PetShelterIcons.Homepage,
            label = stringResource(Res.string.nav_homepage),
            selected = selectedItem == SidebarItem.Homepage,
            onClick = { onItemSelected(SidebarItem.Homepage) },
        )
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (selected) PetShelterTheme.colors.Primary else PetShelterTheme.colors.TextSecondary

    Column(
        modifier =
            modifier
                .clip(RoundedCornerShape(Radii.Medium))
                .clickable(onClick = onClick)
                .padding(horizontal = Spacing.Medium, vertical = Spacing.XSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.XXSmall),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = contentColor,
        )
        Text(
            text = label,
            style = PetShelterTypography.Caption,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun BottomNavigationBarPreview() {
    PetShelterTheme {
        BottomNavigationBar(
            selectedItem = SidebarItem.Homepage,
            onItemSelected = {},
        )
    }
}

@Preview
@Composable
private fun NavigationSidebarExpandedPreview() {
    NavigationSidebar(
        selectedItem = SidebarItem.Homepage,
        onItemSelected = {},
        onToggleCollapsed = {},
    )
}

@Preview
@Composable
private fun NavigationSidebarCollapsedPreview() {
    NavigationSidebar(
        selectedItem = SidebarItem.Homepage,
        onItemSelected = {},
        collapsed = true,
        onToggleCollapsed = {},
    )
}
