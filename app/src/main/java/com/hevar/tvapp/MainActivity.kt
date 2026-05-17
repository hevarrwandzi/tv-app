package com.hevar.tvapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hevar.tvapp.navigation.TvAppNavigation
import com.hevar.tvapp.theme.TvAppTheme

// App entry point. Keeps setup intentionally small and delegates UI wiring to navigation.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvAppTheme {
                TvAppNavigation()
            }
        }
    }
}
