package com.hevar.tvapp.ui.player

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.tv.material3.Button
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.hevar.tvapp.theme.AccentGreen
import com.hevar.tvapp.theme.AppBackground
import com.hevar.tvapp.theme.BorderFocused
import com.hevar.tvapp.theme.SurfaceDark
import com.hevar.tvapp.theme.SurfaceHighlight
import com.hevar.tvapp.theme.TvAppTheme

// Media3 is wired in here, but playback is still intentionally mocked.
@Composable
fun PlayerPlaceholderScreen(
    channelName: String,
    onBack: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        val compact = maxWidth < 700.dp
        var favorite by remember { mutableStateOf(false) }
        var fakePlaying by remember { mutableStateOf(false) }

        val player = rememberMedia3PlaceholderPlayer()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = if (compact) 20.dp else 48.dp,
                    vertical = if (compact) 24.dp else 36.dp
                ),
            verticalArrangement = Arrangement.spacedBy(if (compact) 18.dp else 24.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(if (compact) 10.dp else 16.dp)) {
                Text(text = channelName, fontSize = if (compact) 28.sp else 38.sp)
                Text(
                    text = "Media3 surface is ready. Real stream hookup comes next.",
                    fontSize = if (compact) 17.sp else 22.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Media3PlaceholderSurface(
                player = player,
                channelName = channelName,
                compact = compact,
                fakePlaying = fakePlaying,
                favorite = favorite,
                modifier = Modifier.weight(1f)
            )

            if (compact) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { fakePlaying = !fakePlaying },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (fakePlaying) "Pause placeholder" else "Play placeholder")
                    }
                    Button(
                        onClick = { favorite = !favorite },
                        modifier = Modifier.fillMaxWidth()
                    ) {
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
                    Button(onClick = { fakePlaying = !fakePlaying }) {
                        Text(if (fakePlaying) "Pause placeholder" else "Play placeholder")
                    }
                    Button(onClick = { favorite = !favorite }) {
                        Text(if (favorite) "Favorited" else "Favorite")
                    }
                    Button(onClick = onBack) { Text("Back") }
                }
            }
        }
    }
}

@Composable
private fun Media3PlaceholderSurface(
    player: ExoPlayer,
    channelName: String,
    compact: Boolean,
    fakePlaying: Boolean,
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
            update = { view ->
                view.player = player
            }
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(if (compact) 16.dp else 22.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (fakePlaying) "NOW MOCK PLAYING" else "MEDIA3 PLACEHOLDER",
                color = BorderFocused,
                fontSize = if (compact) 12.sp else 14.sp
            )
            Text(
                text = channelName,
                fontSize = if (compact) 24.sp else 30.sp
            )
            Text(
                text = if (fakePlaying) {
                    "Controller state is live, but no stream URL is attached yet."
                } else {
                    "No media item loaded yet. This screen is ready for ExoPlayer hookup later."
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
            StatusChip(text = if (favorite) "Favorite saved" else "Favorite ready")
            StatusChip(text = if (fakePlaying) "Preview playing" else "Idle preview")
            StatusChip(text = "Media3 connected", accent = true)
        }
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
            fontSize = 12.sp
        )
    }
}

@Composable
private fun rememberMedia3PlaceholderPlayer(): ExoPlayer {
    val context = LocalContext.current
    val player = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_OFF
            playWhenReady = false
            addListener(
                object : Player.Listener {
                    override fun onPlayerError(error: PlaybackException) {
                        // Intentionally ignored for now. No real media item is attached yet.
                    }
                }
            )
        }
    }

    DisposableEffect(player) {
        onDispose { player.release() }
    }

    return player
}

@Preview(name = "Player TV", widthDp = 1280, heightDp = 720, showBackground = true)
@Composable
fun PlayerPlaceholderScreenPreview() {
    TvAppTheme {
        PlayerPlaceholderScreen(
            channelName = "Kurdistan TV",
            onBack = {}
        )
    }
}

@Preview(name = "Player Mobile", widthDp = 412, heightDp = 915, showBackground = true)
@Composable
fun PlayerPlaceholderScreenMobilePreview() {
    TvAppTheme {
        PlayerPlaceholderScreen(
            channelName = "Kurdistan TV",
            onBack = {}
        )
    }
}
