package com.hevar.tvapp.navigation

object Routes {
    const val Splash = "splash"
    const val Home = "home"
    const val Settings = "settings"
    const val Player = "player/{channelId}"

    fun player(channelId: String): String = "player/$channelId"
}
