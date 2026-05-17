package com.hevar.tvapp.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.Card
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.hevar.tvapp.theme.AppBackground
import com.hevar.tvapp.theme.SurfaceDark

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val options = listOf("Account", "Language", "App Version", "About")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(40.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Settings", fontSize = 32.sp)
                Text(
                    text = "Placeholder preferences screen",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(onClick = onBack) {
                Text("Back")
            }
        }

        options.forEach { option ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.tv.material3.CardDefaults.shape(RoundedCornerShape(20.dp)),
                onClick = { }
            ) {
                Column(
                    modifier = Modifier
                        .background(SurfaceDark)
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Text(text = option, fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Will be wired later",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
