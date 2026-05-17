package com.hevar.tvapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hevar.tvapp.data.FakeChannelRepository
import com.hevar.tvapp.model.Channel
import com.hevar.tvapp.model.ChannelCategory

// Simple MVVM state holder for category selection and filtered channel data.
class HomeViewModel(
    private val repository: FakeChannelRepository = FakeChannelRepository()
) : ViewModel() {
    var selectedCategory by mutableStateOf(ChannelCategory.All)
        private set

    private val allChannels = repository.getChannels()

    val categories: List<ChannelCategory> = listOf(
        ChannelCategory.All,
        ChannelCategory.News,
        ChannelCategory.Sports,
        ChannelCategory.Kids,
        ChannelCategory.Music,
        ChannelCategory.Kurdish,
        ChannelCategory.Arabic,
        ChannelCategory.Settings
    )

    val visibleChannels: List<Channel>
        get() = when (selectedCategory) {
            ChannelCategory.All -> allChannels
            ChannelCategory.Settings -> emptyList()
            else -> allChannels.filter { it.category == selectedCategory }
        }

    fun selectCategory(category: ChannelCategory) {
        selectedCategory = category
    }
}
