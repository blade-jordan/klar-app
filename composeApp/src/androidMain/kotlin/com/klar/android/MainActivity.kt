package com.klar.android

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.klar.android.animations.ProvideHapticFeedback
import com.klar.android.screens.PathScreen
import com.klar.android.ui.theme.KlarTheme
import com.klar.shared.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KlarApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            androidContext(this@KlarApplication)
            modules(appModule)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KlarTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    ProvideHapticFeedback {
        Navigator(PathScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}
