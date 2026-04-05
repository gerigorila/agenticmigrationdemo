package com.gerigorila.agenticmigrationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gerigorila.agenticmigrationdemo.presentation.navigation.AppNavigation
import com.gerigorila.agenticmigrationdemo.presentation.theme.AgenticMigrationDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgenticMigrationDemoTheme {
                AppNavigation()
            }
        }
    }
}
