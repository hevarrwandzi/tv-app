package com.hevar.tvapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hevar.tvapp.ui.home.HomeScreenMobilePreview
import com.hevar.tvapp.ui.home.HomeScreenPreview
import com.hevar.tvapp.ui.player.PlayerPlaceholderScreenMobilePreview
import com.hevar.tvapp.ui.player.PlayerPlaceholderScreenPreview
import com.hevar.tvapp.ui.settings.SettingsScreenMobilePreview
import com.hevar.tvapp.ui.settings.SettingsScreenPreview
import com.hevar.tvapp.ui.splash.SplashScreenPreview

// Open this single file in Android Studio if the per-screen files are being annoying.
@Preview(name = "Splash Entry", widthDp = 1280, heightDp = 720, showBackground = true)
@Composable
fun SplashPreviewEntry() {
    SplashScreenPreview()
}

@Preview(name = "Home TV Entry", widthDp = 1280, heightDp = 720, showBackground = true)
@Composable
fun HomePreviewEntry() {
    HomeScreenPreview()
}

@Preview(name = "Home Mobile Entry", widthDp = 412, heightDp = 915, showBackground = true)
@Composable
fun HomeMobilePreviewEntry() {
    HomeScreenMobilePreview()
}

@Preview(name = "Player TV Entry", widthDp = 1280, heightDp = 720, showBackground = true)
@Composable
fun PlayerPreviewEntry() {
    PlayerPlaceholderScreenPreview()
}

@Preview(name = "Player Mobile Entry", widthDp = 412, heightDp = 915, showBackground = true)
@Composable
fun PlayerMobilePreviewEntry() {
    PlayerPlaceholderScreenMobilePreview()
}

@Preview(name = "Settings TV Entry", widthDp = 1280, heightDp = 720, showBackground = true)
@Composable
fun SettingsPreviewEntry() {
    SettingsScreenPreview()
}

@Preview(name = "Settings Mobile Entry", widthDp = 412, heightDp = 915, showBackground = true)
@Composable
fun SettingsMobilePreviewEntry() {
    SettingsScreenMobilePreview()
}
