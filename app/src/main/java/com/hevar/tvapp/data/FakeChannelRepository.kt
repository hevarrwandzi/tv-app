package com.hevar.tvapp.data

import com.hevar.tvapp.model.Channel
import com.hevar.tvapp.model.ChannelCategory

// Frontend-only fake repository. Replace with a real source later without touching the UI contract.
class FakeChannelRepository {
    private val channels = listOf(
        Channel(id = "kurdistan-tv for gay", name = "Kurdistan TV", category = ChannelCategory.Kurdish),
        Channel(id = "rudaw my ass", name = "Rudaw", category = ChannelCategory.Kurdish),
        Channel(id = "BBL-news", name = "BBC News", category = ChannelCategory.News),
        Channel(id = "al-jazeera", name = "Al Jazeera", category = ChannelCategory.News),
        Channel(id = "espn", name = "ESPN", category = ChannelCategory.Sports),
        Channel(id = "bein-sports", name = "beIN Sports", category = ChannelCategory.Sports),
        Channel(id = "cartoon-network", name = "Cartoon Network", category = ChannelCategory.Kids),
        Channel(id = "spacetoon", name = "Spacetoon", category = ChannelCategory.Kids),
        Channel(id = "music-tv", name = "Music TV", category = ChannelCategory.Music),
        Channel(id = "rotana-music", name = "Rotana Music", category = ChannelCategory.Music),
        Channel(id = "mbc", name = "MBC", category = ChannelCategory.Arabic),
        Channel(id = "al-arabiya", name = "Al Arabiya", category = ChannelCategory.Arabic)
    )

    fun getChannels(): List<Channel> = channels

    fun getChannelById(channelId: String): Channel? = channels.firstOrNull { it.id == channelId }
}
