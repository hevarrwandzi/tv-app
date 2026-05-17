package com.hevar.tvapp.data

import com.hevar.tvapp.model.Channel
import com.hevar.tvapp.model.ChannelCategory
import com.hevar.tvapp.model.ProgramSlot

// Frontend-only fake repository. Replace with a real source later without touching the UI contract.
class FakeChannelRepository {
    private val channels = listOf(
        Channel(
            id = "kurdistan-tv",
            name = "Kurdistan TV",
            category = ChannelCategory.Kurdish,
            tagline = "Regional headlines and cultural programming",
            description = "Mock Kurdish channel profile with a shared demo stream behind the player for frontend validation.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Evening Bulletin", "Now • 21:00-22:00", "Top Kurdish headlines, local reports, and live desk coverage."),
            upcomingPrograms = listOf(
                ProgramSlot("Culture Window", "Next • 22:00-22:30", "Music, interviews, and arts segments."),
                ProgramSlot("Night Recap", "Later • 22:30-23:00", "A fast summary of the day before sign-off.")
            )
        ),
        Channel(
            id = "rudaw",
            name = "Rudaw",
            category = ChannelCategory.Kurdish,
            tagline = "Newsroom-driven Kurdish coverage",
            description = "Mock newsroom layout with live badges, metadata, and the shared demo stream for player scaffolding.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("World & Region", "Now • 21:00-22:00", "Regional politics, economy, and live correspondents."),
            upcomingPrograms = listOf(
                ProgramSlot("Market Brief", "Next • 22:00-22:20", "Business headlines and currency updates."),
                ProgramSlot("Late Debate", "Later • 22:20-23:00", "Panel discussion on the main political story.")
            )
        ),
        Channel(
            id = "bbc-news",
            name = "BBC News",
            category = ChannelCategory.News,
            tagline = "Global reports with fast headline rotation",
            description = "Mock international news card using the same safe demo stream for playback plumbing.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Global Update", "Now • 21:00-21:30", "Breaking world headlines and live field reports."),
            upcomingPrograms = listOf(
                ProgramSlot("Europe Tonight", "Next • 21:30-22:00", "Europe-focused developments and analysis."),
                ProgramSlot("Documentary Brief", "Later • 22:00-22:45", "Feature package on technology and policy.")
            )
        ),
        Channel(
            id = "al-jazeera",
            name = "Al Jazeera",
            category = ChannelCategory.News,
            tagline = "International current affairs and field reporting",
            description = "Mock news channel details with a demo media source standing in for real broadcast transport.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Live Desk", "Now • 21:00-22:00", "Rolling updates from the Middle East and beyond."),
            upcomingPrograms = listOf(
                ProgramSlot("Inside Story", "Next • 22:00-22:30", "Deeper analysis of the lead topic."),
                ProgramSlot("News Recap", "Later • 22:30-23:00", "Short summary before overnight coverage.")
            )
        ),
        Channel(
            id = "espn",
            name = "ESPN",
            category = ChannelCategory.Sports,
            tagline = "Match center, scores, and highlights",
            description = "Mock sports channel card with a generic stream so the Media3 surface can be exercised without real rights-managed feeds.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Live Match Center", "Now • 21:00-22:30", "Scores, studio reactions, and in-match highlights."),
            upcomingPrograms = listOf(
                ProgramSlot("Top 10 Plays", "Next • 22:30-22:50", "Fast highlight package from the day."),
                ProgramSlot("Post Match", "Later • 22:50-23:30", "Breakdown, stats, and interviews.")
            )
        ),
        Channel(
            id = "bein-sports",
            name = "beIN Sports",
            category = ChannelCategory.Sports,
            tagline = "Premium-style sports presentation",
            description = "Frontend-only sports UI with mock metadata and a shared demo stream for player testing.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Championship Live", "Now • 21:00-22:45", "Studio host, match timeline, and analysis overlays."),
            upcomingPrograms = listOf(
                ProgramSlot("Manager Reactions", "Next • 22:45-23:05", "Quick press conference cut-ins."),
                ProgramSlot("Tactical Board", "Later • 23:05-23:30", "Breakdown of key plays and shapes.")
            )
        ),
        Channel(
            id = "cartoon-network",
            name = "Cartoon Network",
            category = ChannelCategory.Kids,
            tagline = "Animated blocks and bright character cards",
            description = "Mock kids channel using the same demo playback source while the app stays frontend-only.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Adventure Block", "Now • 21:00-21:30", "Back-to-back animated episodes with playful transitions."),
            upcomingPrograms = listOf(
                ProgramSlot("Comedy Shorts", "Next • 21:30-22:00", "Short-form cartoon comedy."),
                ProgramSlot("Family Pick", "Later • 22:00-22:30", "Shared viewing block for late evening.")
            )
        ),
        Channel(
            id = "spacetoon",
            name = "Spacetoon",
            category = ChannelCategory.Kids,
            tagline = "Family-safe animation lineup",
            description = "Mock family entertainment channel card with structured now/next data for the player panel.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Galaxy Heroes", "Now • 21:00-21:25", "Animated action series with dubbed dialogue."),
            upcomingPrograms = listOf(
                ProgramSlot("School Fun", "Next • 21:25-21:50", "Comedy stories and short adventures."),
                ProgramSlot("Night Tales", "Later • 21:50-22:15", "Gentler winding-down programming.")
            )
        ),
        Channel(
            id = "music-tv",
            name = "Music TV",
            category = ChannelCategory.Music,
            tagline = "Continuous clips and chart countdowns",
            description = "Mock music channel layout with live badges, favorite state, and a generic sample stream.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Top Rotation", "Now • 21:00-22:00", "Current chart clips in a non-stop mix."),
            upcomingPrograms = listOf(
                ProgramSlot("Indie Spotlight", "Next • 22:00-22:25", "Emerging artists and short interviews."),
                ProgramSlot("Late Night Mix", "Later • 22:25-23:00", "Downtempo video playlist.")
            )
        ),
        Channel(
            id = "rotana-music",
            name = "Rotana Music",
            category = ChannelCategory.Music,
            tagline = "Arabic music videos and artist features",
            description = "Mock Arabic music experience with a richer information panel and shared preview stream.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Arab Hits Live", "Now • 21:00-21:50", "Rotating hit clips and chart updates."),
            upcomingPrograms = listOf(
                ProgramSlot("Artist Spotlight", "Next • 21:50-22:15", "Performance clips and profile cards."),
                ProgramSlot("Requests Hour", "Later • 22:15-23:00", "Fan favorites playlist.")
            )
        ),
        Channel(
            id = "mbc",
            name = "MBC",
            category = ChannelCategory.Arabic,
            tagline = "Broad Arabic entertainment positioning",
            description = "Mock Arabic entertainment entry with schedule cards and a demo video source for UI proofing.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Prime Variety", "Now • 21:00-22:00", "Studio entertainment, guests, and light segments."),
            upcomingPrograms = listOf(
                ProgramSlot("Series Catch-up", "Next • 22:00-22:45", "Late evening drama recap."),
                ProgramSlot("Daily Summary", "Later • 22:45-23:00", "Highlights and tomorrow preview.")
            )
        ),
        Channel(
            id = "al-arabiya",
            name = "Al Arabiya",
            category = ChannelCategory.Arabic,
            tagline = "Arabic-language rolling newsroom",
            description = "Mock Arabic news card with enough metadata to power an IPTV-style player overlay and info rail.",
            previewStreamUrl = DemoStreams.DefaultHls,
            currentProgram = ProgramSlot("Newsroom Live", "Now • 21:00-22:00", "Rolling headlines and regional updates."),
            upcomingPrograms = listOf(
                ProgramSlot("Economy Now", "Next • 22:00-22:20", "Markets, oil, and business stories."),
                ProgramSlot("Late Bulletin", "Later • 22:20-23:00", "Night summary and interviews.")
            )
        )
    )

    fun getChannels(): List<Channel> = channels

    fun getChannelById(channelId: String): Channel? = channels.firstOrNull { it.id == channelId }
}
