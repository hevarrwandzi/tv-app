package com.hevar.tvapp.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.hevar.tvapp.data.FakeChannelRepository
import com.hevar.tvapp.model.Channel
import com.hevar.tvapp.model.ChannelCategory
import com.hevar.tvapp.theme.AccentBlue
import com.hevar.tvapp.theme.AccentGreen
import com.hevar.tvapp.theme.AppBackground
import com.hevar.tvapp.theme.BorderFocused
import com.hevar.tvapp.theme.SurfaceDark
import com.hevar.tvapp.theme.SurfaceHighlight
import com.hevar.tvapp.theme.TvAppTheme

@Composable
fun HomeScreen(
    onOpenChannel: (Channel) -> Unit,
    onOpenSettings: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    HomeScreenContent(
        categories = viewModel.categories,
        selectedCategory = viewModel.selectedCategory,
        channels = viewModel.visibleChannels,
        onSelectCategory = { category ->
            viewModel.selectCategory(category)
            if (category == ChannelCategory.Settings) onOpenSettings()
        },
        onOpenChannel = onOpenChannel
    )
}

@Composable
fun HomeScreenContent(
    categories: List<ChannelCategory>,
    selectedCategory: ChannelCategory,
    channels: List<Channel>,
    onSelectCategory: (ChannelCategory) -> Unit,
    onOpenChannel: (Channel) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        val compact = maxWidth < 700.dp

        if (compact) {
            MobileHomeLayout(
                categories = categories,
                selectedCategory = selectedCategory,
                channels = channels,
                onSelectCategory = onSelectCategory,
                onOpenChannel = onOpenChannel
            )
        } else {
            TvHomeLayout(
                categories = categories,
                selectedCategory = selectedCategory,
                channels = channels,
                onSelectCategory = onSelectCategory,
                onOpenChannel = onOpenChannel
            )
        }
    }
}

@Composable
private fun TvHomeLayout(
    categories: List<ChannelCategory>,
    selectedCategory: ChannelCategory,
    channels: List<Channel>,
    onSelectCategory: (ChannelCategory) -> Unit,
    onOpenChannel: (Channel) -> Unit
) {
    val sidebarScroll = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxSize()
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
            HomeSidebarHeader()
            categories.forEach { category ->
                SidebarItem(
                    label = category.label,
                    selected = selectedCategory == category,
                    compact = false,
                    onClick = { onSelectCategory(category) }
                )
            }
        }

        Spacer(modifier = Modifier.size(24.dp))

        ChannelPane(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.75f),
            selectedCategory = selectedCategory,
            channels = channels,
            onOpenChannel = onOpenChannel,
            compact = false
        )
    }
}

@Composable
private fun MobileHomeLayout(
    categories: List<ChannelCategory>,
    selectedCategory: ChannelCategory,
    channels: List<Channel>,
    onSelectCategory: (ChannelCategory) -> Unit,
    onOpenChannel: (Channel) -> Unit
) {
    val categoryScroll = rememberScrollState()
    val featuredChannel = channels.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MobileHomeHero(
            selectedCategory = selectedCategory,
            featuredChannel = featuredChannel,
            onOpenChannel = { featuredChannel?.let(onOpenChannel) }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark, RoundedCornerShape(24.dp))
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            HomeSidebarHeader(compact = true)
            Text(
                text = "Fast mobile browse with live channels up front.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(categoryScroll),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                categories.filter { it != ChannelCategory.Settings }.forEach { category ->
                    Box(modifier = Modifier.padding(end = 10.dp)) {
                        SidebarItem(
                            label = category.label,
                            selected = selectedCategory == category,
                            compact = true,
                            onClick = { onSelectCategory(category) }
                        )
                    }
                }
            }
        }

        ChannelPane(
            modifier = Modifier.fillMaxWidth(),
            selectedCategory = selectedCategory,
            channels = channels,
            onOpenChannel = onOpenChannel,
            compact = true
        )
    }
}

@Composable
private fun MobileHomeHero(
    selectedCategory: ChannelCategory,
    featuredChannel: Channel?,
    onOpenChannel: () -> Unit
) {
    val categoryLabel = if (selectedCategory == ChannelCategory.All) "Featured now" else selectedCategory.label
    val channelName = featuredChannel?.name ?: "No channels yet"
    val channelTag = featuredChannel?.category?.label ?: "Mock"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        AccentGreen.copy(alpha = 0.95f),
                        SurfaceHighlight,
                        AccentBlue.copy(alpha = 0.90f)
                    )
                )
            )
            .clickable(onClick = onOpenChannel)
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(AppBackground.copy(alpha = 0.22f), RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = categoryLabel,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Box(
                    modifier = Modifier
                        .background(AppBackground.copy(alpha = 0.18f), RoundedCornerShape(50))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "LIVE NOW",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text(
                text = "Premium mock IPTV",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = channelName,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$channelTag • Frontend only • Player comes later",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MobileHeroButton(
                    label = "Watch Placeholder",
                    filled = true,
                    modifier = Modifier.weight(1f),
                    onClick = onOpenChannel
                )
                MobileHeroButton(
                    label = "12 live channels",
                    filled = false,
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun MobileHeroButton(
    label: String,
    filled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(if (filled) AppBackground else AppBackground.copy(alpha = 0.18f))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (filled) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.size(6.dp))
        }
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun HomeSidebarHeader(compact: Boolean = false) {
    Text(
        text = "Browse",
        fontSize = if (compact) 14.sp else 18.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
        text = "tv-app",
        fontSize = if (compact) 24.sp else 28.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ChannelPane(
    modifier: Modifier,
    selectedCategory: ChannelCategory,
    channels: List<Channel>,
    onOpenChannel: (Channel) -> Unit,
    compact: Boolean
) {
    val columns = if (compact) 2 else 3

    Column(
        modifier = modifier
            .background(SurfaceDark, RoundedCornerShape(if (compact) 24.dp else 28.dp))
            .padding(if (compact) 16.dp else 28.dp)
    ) {
        Text(
            text = if (compact) "For you" else "Live Channels",
            fontSize = if (compact) 26.sp else 34.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = if (selectedCategory == ChannelCategory.All) {
                if (compact) "Clean mobile cards with live-first browsing" else "All categories"
            } else {
                "${selectedCategory.label} channels"
            },
            fontSize = if (compact) 15.sp else 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(if (compact) 16.dp else 24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(if (compact) 12.dp else 20.dp),
            verticalArrangement = Arrangement.spacedBy(if (compact) 12.dp else 20.dp),
            userScrollEnabled = !compact,
            contentPadding = PaddingValues(bottom = 12.dp),
            modifier = if (compact) Modifier.height(700.dp) else Modifier.fillMaxSize()
        ) {
            items(channels, key = { it.id }) { channel ->
                ChannelCard(
                    channel = channel,
                    onClick = { onOpenChannel(channel) },
                    compact = compact
                )
            }
        }
    }
}

@Composable
private fun SidebarItem(
    label: String,
    selected: Boolean,
    compact: Boolean,
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
            .then(if (compact) Modifier else Modifier.fillMaxWidth())
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
            .padding(
                horizontal = if (compact) 14.dp else 18.dp,
                vertical = if (compact) 12.dp else 16.dp
            )
    ) {
        Text(
            text = label,
            fontSize = if (compact) 16.sp else 20.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ChannelCard(
    channel: Channel,
    onClick: () -> Unit,
    compact: Boolean
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.04f else 1f,
        animationSpec = tween(180),
        label = "channelCardScale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .background(
                color = if (isFocused) SurfaceHighlight else SurfaceDark,
                shape = RoundedCornerShape(if (compact) 22.dp else 24.dp)
            )
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = if (isFocused) BorderFocused else SurfaceHighlight,
                shape = RoundedCornerShape(if (compact) 22.dp else 24.dp)
            )
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(if (compact) 12.dp else 18.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (compact) 116.dp else 110.dp)
                .clip(RoundedCornerShape(if (compact) 18.dp else 18.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = if (compact) {
                            listOf(
                                AccentGreen.copy(alpha = 0.92f),
                                SurfaceHighlight,
                                AccentBlue.copy(alpha = 0.88f)
                            )
                        } else {
                            listOf(
                                AccentGreen.copy(alpha = if (isFocused) 0.30f else 0.18f),
                                AccentBlue.copy(alpha = if (isFocused) 0.22f else 0.10f)
                            )
                        }
                    )
                )
                .padding(if (compact) 12.dp else 0.dp),
            contentAlignment = if (compact) Alignment.TopStart else Alignment.Center
        ) {
            if (compact) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .background(AppBackground.copy(alpha = 0.20f), RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = channel.category.label,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(AppBackground.copy(alpha = 0.18f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tv,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(AppBackground.copy(alpha = 0.20f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = channel.name.take(2).uppercase(),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = if (channel.isLive) "LIVE" else "OFFLINE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "HD Placeholder",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = channel.name.take(2).uppercase(),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(if (compact) 12.dp else 16.dp))
        Text(
            text = channel.name,
            fontSize = if (compact) 16.sp else 22.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(6.dp))

        if (compact) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = channel.category.label,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Box(
                    modifier = Modifier
                        .background(AccentBlue.copy(alpha = 0.18f), RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Remote ready",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        } else {
            Text(
                text = channel.category.label,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        Box(
            modifier = Modifier
                .background(AccentGreen, RoundedCornerShape(50))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "LIVE",
                fontSize = if (compact) 11.sp else 13.sp,
                fontWeight = FontWeight.Bold,
                color = AppBackground
            )
        }
    }
}

@Preview(name = "Home TV", widthDp = 1280, heightDp = 720, showBackground = true)
@Composable
fun HomeScreenPreview() {
    val repository = FakeChannelRepository()
    TvAppTheme {
        HomeScreenContent(
            categories = listOf(
                ChannelCategory.All,
                ChannelCategory.News,
                ChannelCategory.Sports,
                ChannelCategory.Kids,
                ChannelCategory.Music,
                ChannelCategory.Kurdish,
                ChannelCategory.Arabic,
                ChannelCategory.Settings
            ),
            selectedCategory = ChannelCategory.All,
            channels = repository.getChannels(),
            onSelectCategory = {},
            onOpenChannel = {}
        )
    }
}

@Preview(name = "Home Mobile", widthDp = 412, heightDp = 915, showBackground = true)
@Composable
fun HomeScreenMobilePreview() {
    val repository = FakeChannelRepository()
    TvAppTheme {
        HomeScreenContent(
            categories = listOf(
                ChannelCategory.All,
                ChannelCategory.News,
                ChannelCategory.Sports,
                ChannelCategory.Kids,
                ChannelCategory.Music,
                ChannelCategory.Kurdish,
                ChannelCategory.Arabic,
                ChannelCategory.Settings
            ),
            selectedCategory = ChannelCategory.All,
            channels = repository.getChannels(),
            onSelectCategory = {},
            onOpenChannel = {}
        )
    }
}
