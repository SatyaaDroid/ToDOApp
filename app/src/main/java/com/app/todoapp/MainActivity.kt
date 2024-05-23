package com.app.todoapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.todoapp.ui.navigation.Destination
import com.app.todoapp.ui.navigation.Destination.TODOLIST
import com.app.todoapp.ui.navigation.Screens
import com.app.todoapp.ui.screens.EditTodoScreen
import com.app.todoapp.ui.screens.ToDoListScreen
import com.app.todoapp.ui.theme.ToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                MyToDoAPP()
            }
        }
    }
}

@Composable
fun MyToDoAPP() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val context = LocalContext.current

    BackHandler(enabled = currentDestination == TODOLIST) {
        if (currentDestination == TODOLIST) {
            (context as? Activity)?.finish()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        NavHost(navController = navController, startDestination = Screens.ToDoList.route) {
            composable(TODOLIST) {
                ToDoListScreen(
                    navController,
                    getToDOItemsViewModel = hiltViewModel(),
                    context
                )
            }
            composable(Destination.TODOEDIT) {
                EditTodoScreen(
                    navController,
                    saveToDOItemsViewModel = hiltViewModel(),
                    context
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
        MyToDoAPP()
    }
}