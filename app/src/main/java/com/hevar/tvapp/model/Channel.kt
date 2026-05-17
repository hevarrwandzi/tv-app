package com.hevar.tvapp.model

data class Channel(
    val id: String,
    val name: String,
    val category: ChannelCategory,
    val isLive: Boolean = true
)

enum class ChannelCategory(val label: String) {
    All("All"),
    News("News"),
    Sports("Sports"),
    Kids("Kids"),
    Music("Music"),
    Kurdish("Kurdish"),
    Arabic("Arabic"),
    Settings("Settings")
}
