package com.codeboxlk.tutorial.edgetoedge

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codeboxlk.tutorial.edgetoedge.ui.theme.EdgeToEdgeTheme
import com.codeboxlk.tutorial.edgetoedge.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )

        super.onCreate(savedInstanceState)

        setContent {
            MainView(mainViewModel)
        }
    }
}

@Composable
fun MainView(viewModel: MainViewModel) {
    val darkTheme by viewModel.getDarkModeState.collectAsState(initial = true)
    val dynamicColor by viewModel.getDynamicColorState.collectAsState(initial = true)

    EdgeToEdgeTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ThemeSettings(
                darkTheme = darkTheme,
                dynamicColor = dynamicColor,
                onDarkThemeChange = { viewModel.setDarkMode(it) },
                onDynamicColorChange = { viewModel.setDynamicColor(it) }
            )
        }
    }
}

@Composable
fun ThemeSettings(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onDynamicColorChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .safeDrawingPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Dark Theme")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = darkTheme,
                onCheckedChange = { onDarkThemeChange(!darkTheme) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Dynamic Color")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = dynamicColor,
                onCheckedChange = { onDynamicColorChange(!dynamicColor) }
            )
        }
    }
}