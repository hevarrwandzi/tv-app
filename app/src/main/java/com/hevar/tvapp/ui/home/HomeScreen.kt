package com.hevar.tvapp.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hevar.tvapp.model.Channel
import com.hevar.tvapp.model.ChannelCategory
import com.hevar.tvapp.theme.AccentGreen
import com.hevar.tvapp.theme.AppBackground
import com.hevar.tvapp.theme.BorderFocused
import com.hevar.tvapp.theme.SurfaceDark
import com.hevar.tvapp.theme.SurfaceHighlight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@Composable
fun HomeScreen(
    onOpenChannel: (Channel) -> Unit,
    onOpenSettings: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val sidebarScroll = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.25f)
                .background(SurfaceDark, RoundedCornerShape(28.dp))
                .padding(20.dp)
                .verticalScroll(sidebarScroll),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Browse",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "tv-app",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            viewModel.categories.forEach { category ->
                SidebarItem(
                    label = category.label,
                    selected = viewModel.selectedCategory == category,
                    onClick = {
                        viewModel.selectCategory(category)
                        if (category == ChannelCategory.Settings) onOpenSettings()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.size(24.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.75f)
                .background(SurfaceDark, RoundedCornerShape(28.dp))
                .padding(28.dp)
        ) {
            Text(
                text = "Live Channels",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (viewModel.selectedCategory == ChannelCategory.All) {
                    "All categories"
                } else {
                    "${viewModel.selectedCategory.label} channels"
                },
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                items(viewModel.visibleChannels, key = { it.id }) { channel ->
                    ChannelCard(channel = channel, onClick = { onOpenChannel(channel) })
                }
            }
        }
    }
}

@Composable
private fun SidebarItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.05f else 1f,
        animationSpec = tween(160),
        label = "sidebarScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .background(
                color = when {
                    isFocused -> SurfaceHighlight
                    selected -> SurfaceHighlight.copy(alpha = 0.85f)
                    else -> SurfaceDark
                },
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = if (isFocused || selected) 2.dp else 0.dp,
                color = if (isFocused) BorderFocused else AccentGreen,
                shape = RoundedCornerShape(18.dp)
            )
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ChannelCard(
    channel: Channel,
    onClick: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.06f else 1f,
        animationSpec = tween(180),
        label = "channelCardScale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .background(
                color = if (isFocused) SurfaceHighlight else SurfaceDark,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = if (isFocused) BorderFocused else SurfaceHighlight,
                shape = RoundedCornerShape(24.dp)
            )
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(18.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(AccentGreen.copy(alpha = if (isFocused) 0.28f else 0.16f), RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = channel.name.take(2).uppercase(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = channel.name, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = channel.category.label,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .background(AccentGreen, RoundedCornerShape(50))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "LIVE",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AppBackground
            )
        }
    }
}
