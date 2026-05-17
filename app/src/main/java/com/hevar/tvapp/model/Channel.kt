package com.hevar.tvapp.model

// Channel model stays frontend-first, but now carries enough mock metadata
// to drive a richer player and future real integrations.
data class Channel(
    val id: String,
    val name: String,
    val category: ChannelCategory,
    val tagline: String,
    val description: String,
    val previewStreamUrl: String,
    val currentProgram: ProgramSlot,
    val upcomingPrograms: List<ProgramSlot>,
    val isLive: Boolean = true
)

data class ProgramSlot(
    val title: String,
    val timeRange: String,
    val summary: String
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
