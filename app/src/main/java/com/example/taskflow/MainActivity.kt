package com.example.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import com.example.taskflow.ui.theme.BackgroundLight
import com.example.taskflow.ui.theme.OnPurpleContainer
import com.example.taskflow.ui.theme.PrimaryPurple
import com.example.taskflow.ui.theme.PurpleContainer
import com.example.taskflow.ui.theme.SurfaceCard

private val PurpleColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    primaryContainer = PurpleContainer,
    onPrimaryContainer = OnPurpleContainer,
    background = BackgroundLight,
    surface = SurfaceCard
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = PurpleColorScheme) {
                Surface {
                    TaskFlowApp()
                }
            }
        }
    }
}