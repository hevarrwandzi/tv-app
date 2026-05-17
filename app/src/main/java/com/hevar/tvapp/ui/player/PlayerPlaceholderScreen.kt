package com.hevar.tvapp.ui.player

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.tv.material3.Button
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.hevar.tvapp.data.FakeChannelRepository
import com.hevar.tvapp.model.Channel
import com.hevar.tvapp.model.ChannelCategory
import com.hevar.tvapp.model.ProgramSlot
import com.hevar.tvapp.theme.AccentGreen
import com.hevar.tvapp.theme.AppBackground
import com.hevar.tvapp.theme.BorderFocused
import com.hevar.tvapp.theme.SurfaceDark
import com.hevar.tvapp.theme.SurfaceHighlight
import com.hevar.tvapp.theme.TvAppTheme

// Player screen is still frontend-first, but now it uses a real Media3 media item
// backed by a generic demo stream and richer mock IPTV metadata.
@Composable
fun PlayerPlaceholderScreen(
    channel: Channel,
    onBack: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        val compact = maxWidth < 700.dp
        var favorite by remember { mutableStateOf(false) }
        var playRequested by remember { mutableStateOf(false) }
        val player = rememberChannelPreviewPlayer(channel.previewStreamUrl)
        val playbackState by rememberPlaybackState(player)
        val isPlaying by rememberIsPlaying(player)

        LaunchedEffect(playRequested) {
            player.playWhenReady = playRequested
            if (playRequested) player.play()
        }

        if (compact) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                PlayerHeader(channel = channel, compact = true, playbackState = playbackState)
                Media3PreviewSurface(
                    player = player,
                    channel = channel,
                    compact = true,
                    isPlaying = isPlaying,
                    favorite = favorite,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )
                PlayerControls(
                    compact = true,
                    isPlaying = isPlaying,
                    favorite = favorite,
                    onTogglePlay = { playRequested = !playRequested },
                    onToggleFavorite = { favorite = !favorite },
                    onBack = onBack
                )
                ChannelDetailsPanel(
                    channel = channel,
                    compact = true,
                    playbackState = playbackState,
                    isPlaying = isPlaying
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 48.dp, vertical = 36.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                PlayerHeader(channel = channel, compact = false, playbackState = playbackState)
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1.65f),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        Media3PreviewSurface(
                            player = player,
                            channel = channel,
                            compact = false,
                            isPlaying = isPlaying,
                            favorite = favorite,
                            modifier = Modifier.weight(1f)
                        )
                        PlayerControls(
                            compact = false,
                            isPlaying = isPlaying,
                            favorite = favorite,
                            onTogglePlay = { playRequested = !playRequested },
                            onToggleFavorite = { favorite = !favorite },
                            onBack = onBack
                        )
                    }
                    ChannelDetailsPanel(
                        channel = channel,
                        compact = false,
                        playbackState = playbackState,
                        isPlaying = isPlaying,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun PlayerHeader(
    channel: Channel,
    compact: Boolean,
    playbackState: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(if (compact) 10.dp else 14.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusChip(text = channel.category.label)
            StatusChip(text = if (channel.isLive) "LIVE" else "OFFLINE", accent = channel.isLive)
            StatusChip(text = playbackState)
        }
        Text(text = channel.name, fontSize = if (compact) 28.sp else 38.sp, fontWeight = FontWeight.Bold)
        Text(
            text = channel.tagline,
            fontSize = if (compact) 16.sp else 20.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun Media3PreviewSurface(
    player: ExoPlayer,
    channel: Channel,
    compact: Boolean,
    isPlaying: Boolean,
    favorite: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(if (compact) 24.dp else 32.dp))
            .padding(if (compact) 12.dp else 18.dp)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceHighlight, RoundedCornerShape(if (compact) 18.dp else 24.dp)),
            factory = { context ->
                PlayerView(context).apply {
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    setShutterBackgroundColor(AndroidColor.parseColor("#131313"))
                    setBackgroundColor(AndroidColor.parseColor("#131313"))
                    this.player = player
                }
            },
            update = { view -> view.player = player }
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(if (compact) 16.dp else 22.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (isPlaying) "DEMO STREAM ACTIVE" else "DEMO PLAYER READY",
                color = BorderFocused,
                fontSize = if (compact) 12.sp else 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = channel.name,
                fontSize = if (compact) 24.sp else 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (isPlaying) {
                    "Playing a shared public sample stream while channel data stays mock-only."
                } else {
                    "Press play to start the shared sample stream. Real channel feeds are still intentionally not connected."
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = if (compact) 14.sp else 16.sp
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(if (compact) 16.dp else 22.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatusChip(text = if (favorite) "Favorited" else "Favorite ready")
            StatusChip(text = if (isPlaying) "Preview playing" else "Paused")
            StatusChip(text = channel.currentProgram.title, accent = true)
        }
    }
}

@Composable
private fun PlayerControls(
    compact: Boolean,
    isPlaying: Boolean,
    favorite: Boolean,
    onTogglePlay: () -> Unit,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit
) {
    if (compact) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onTogglePlay, modifier = Modifier.fillMaxWidth()) {
                Text(if (isPlaying) "Pause demo stream" else "Play demo stream")
            }
            Button(onClick = onToggleFavorite, modifier = Modifier.fillMaxWidth()) {
                Text(if (favorite) "Favorited" else "Favorite")
            }
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Back")
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
        ) {
            Button(onClick = onTogglePlay) {
                Text(if (isPlaying) "Pause demo stream" else "Play demo stream")
            }
            Button(onClick = onToggleFavorite) {
                Text(if (favorite) "Favorited" else "Favorite")
            }
            Button(onClick = onBack) {
                Text("Back")
            }
        }
    }
}

@Composable
private fun ChannelDetailsPanel(
    channel: Channel,
    compact: Boolean,
    playbackState: String,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(if (compact) 24.dp else 30.dp))
            .border(1.dp, SurfaceHighlight, RoundedCornerShape(if (compact) 24.dp else 30.dp))
            .padding(if (compact) 18.dp else 24.dp),
        verticalArrangement = Arrangement.spacedBy(if (compact) 16.dp else 20.dp)
    ) {
        Text(
            text = "Channel details",
            fontSize = if (compact) 20.sp else 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = channel.description,
            fontSize = if (compact) 14.sp else 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        InfoRow(label = "Playback", value = if (isPlaying) "Playing sample stream" else playbackState)
        InfoRow(label = "Category", value = channel.category.label)
        InfoRow(label = "Live status", value = if (channel.isLive) "Live badge on" else "Offline")
        InfoRow(label = "Source", value = "Shared demo stream, not a real channel feed")

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Now airing",
                fontSize = if (compact) 18.sp else 22.sp,
                fontWeight = FontWeight.Bold
            )
            ProgramCard(program = channel.currentProgram, compact = compact, highlight = true)
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Up next",
                fontSize = if (compact) 18.sp else 22.sp,
                fontWeight = FontWeight.Bold
            )
            channel.upcomingPrograms.forEach { program ->
                ProgramCard(program = program, compact = compact)
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            modifier = Modifier.width(94.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = value, fontSize = 15.sp)
    }
}

@Composable
private fun ProgramCard(program: ProgramSlot, compact: Boolean, highlight: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (highlight) SurfaceHighlight else AppBackground.copy(alpha = 0.65f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(if (compact) 14.dp else 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = program.timeRange,
            color = if (highlight) BorderFocused else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = program.title, fontSize = if (compact) 17.sp else 19.sp, fontWeight = FontWeight.Bold)
        Text(
            text = program.summary,
            fontSize = if (compact) 13.sp else 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun StatusChip(text: String, accent: Boolean = false) {
    Box(
        modifier = Modifier
            .background(
                color = if (accent) AccentGreen else SurfaceDark,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = if (accent) AppBackground else MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun rememberChannelPreviewPlayer(previewStreamUrl: String): ExoPlayer {
    val context = LocalContext.current
    val player = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = false
        }
    }

    LaunchedEffect(player, previewStreamUrl) {
        player.setMediaItem(MediaItem.fromUri(previewStreamUrl))
        player.prepare()
    }

    DisposableEffect(player) {
        onDispose { player.release() }
    }

    return player
}

@Composable
private fun rememberPlaybackState(player: ExoPlayer) = produceState(initialValue = "Buffering", player) {
    fun resolveState(): String = when (player.playbackState) {
        Player.STATE_IDLE -> "Idle"
        Player.STATE_BUFFERING -> "Buffering"
        Player.STATE_READY -> if (player.playWhenReady) "Ready to play" else "Ready"
        Player.STATE_ENDED -> "Ended"
        else -> "Unknown"
    }

    value = resolveState()
    val listener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            value = resolveState()
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            value = if (isPlaying) "Playing" else resolveState()
        }
    }
    player.addListener(listener)
    awaitDispose { player.removeListener(listener) }
}

@Composable
private fun rememberIsPlaying(player: ExoPlayer) = produceState(initialValue = player.isPlaying, player) {
    value = player.isPlaying
    val listener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            value = isPlaying
        }
    }
    player.addListener(listener)
    awaitDispose { player.removeListener(listener) }
}

private fun fallbackPreviewChannel() = Channel(
    id = "preview-channel",
    name = "Kurdistan TV",
    category = ChannelCategory.Kurdish,
    tagline = "Preview-only mock channel",
    description = "Preview data used when the player screen is opened from Android Studio previews.",
    previewStreamUrl = FakeChannelRepository().getChannels().first().previewStreamUrl,
    currentProgram = ProgramSlot("Preview Bulletin", "Now • 21:00-22:00", "Static preview schedule entry."),
    upcomingPrograms = listOf(
        ProgramSlot("Preview Culture", "Next • 22:00-22:30", "Static preview metadata."),
        ProgramSlot("Preview Recap", "Later • 22:30-23:00", "Static preview metadata.")
    )
)

@Preview(name = "Player TV", widthDp = 1280, heightDp = 720, showBackground = true)
@Composable
fun PlayerPlaceholderScreenPreview() {
    TvAppTheme {
        PlayerPlaceholderScreen(
            channel = fallbackPreviewChannel(),
            onBack = {}
        )
    }
}

@Preview(name = "Player Mobile", widthDp = 412, heightDp = 915, showBackground = true)
@Composable
fun PlayerPlaceholderScreenMobilePreview() {
    TvAppTheme {
        PlayerPlaceholderScreen(
            channel = fallbackPreviewChannel(),
            onBack = {}
        )
    }
}
