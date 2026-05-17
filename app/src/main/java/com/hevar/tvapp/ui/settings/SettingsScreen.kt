package com.hevar.tvapp.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.Card
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.hevar.tvapp.theme.AppBackground
import com.hevar.tvapp.theme.SurfaceDark
import com.hevar.tvapp.theme.TvAppTheme

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    showBackButton: Boolean = true
) {
    val options = listOf("Account", "Language", "App Version", "About")

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        val compact = maxWidth < 700.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (compact) 18.dp else 40.dp),
            verticalArrangement = Arrangement.spacedBy(if (compact) 14.dp else 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Settings", fontSize = if (compact) 26.sp else 32.sp)
                    Text(
                        text = if (compact) "Phone-friendly settings shell" else "Placeholder preferences screen",
                        fontSize = if (compact) 15.sp else 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (showBackButton) {
                    Button(onClick = onBack) {
                        Text("Back")
                    }
                }
            }

            options.forEach { option ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.tv.material3.CardDefaults.shape(RoundedCornerShape(if (compact) 16.dp else 20.dp)),
                    onClick = { }
                ) {
                    Column(
                        modifier = Modifier
                            .background(SurfaceDark)
                            .padding(
                                horizontal = if (compact) 18.dp else 24.dp,
                                vertical = if (compact) 16.dp else 20.dp
                            )
                    ) {
                        Text(text = option, fontSize = if (compact) 19.sp else 22.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Will be wired later",
                            fontSize = if (compact) 14.sp else 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Settings TV", widthDp = 1280, heightDp = 720, showBackground = true)
@Composable
fun SettingsScreenPreview() {
    TvAppTheme {
        SettingsScreen(onBack = {})
    }
}

@Preview(name = "Settings Mobile", widthDp = 412, heightDp = 915, showBackground = true)
@Composable
fun SettingsScreenMobilePreview() {
    TvAppTheme {
        SettingsScreen(onBack = {}, showBackButton = false)
    }
}
