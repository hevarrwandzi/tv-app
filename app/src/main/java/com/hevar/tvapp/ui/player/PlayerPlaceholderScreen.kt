package com.hevar.tvapp.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.hevar.tvapp.theme.AppBackground
import com.hevar.tvapp.theme.TvAppTheme

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = if (compact) 20.dp else 48.dp,
                    vertical = if (compact) 24.dp else 36.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(if (compact) 10.dp else 16.dp)) {
                Text(text = channelName, fontSize = if (compact) 28.sp else 38.sp)
                Text(
                    text = "Player will be added later",
                    fontSize = if (compact) 18.sp else 22.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (compact) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("Play/Pause") }
                    Button(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("Favorite") }
                    Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
                ) {
                    Button(onClick = { }) { Text("Play/Pause") }
                    Button(onClick = { }) { Text("Favorite") }
                    Button(onClick = onBack) { Text("Back") }
                }
            }
        }
    }
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
