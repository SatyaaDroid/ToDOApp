package com.app.todoapp.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.todoapp.data.local.entity.MyTodoEntity
import com.app.todoapp.ui.navigation.Screens
import com.app.todoapp.viewmodel.SaveToDOItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoScreen(
    navController: NavHostController,
    saveToDOItemsViewModel: SaveToDOItemsViewModel,
    context: Context
) {
    BackHandler {
        navController.navigateUp()
    }


    val result = saveToDOItemsViewModel.savedToDOItemsStateHolder.value
    var toDoString by remember { mutableStateOf("") }

    if (result.isLoading) {
        Loading()
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize()
                .imePadding(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Enter New Item")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.surface,
                        navigationIconContentColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            floatingActionButton = { ->
                MySaveFloatingButton(navController, saveToDOItemsViewModel, toDoString,context)
            },
            floatingActionButtonPosition = FabPosition.End,
            content = { paddingValues ->
                EditTodo(
                    paddingValues = paddingValues,
                    values = toDoString,
                    onChange = { toDoString = it }
                )
            }
        )
    }
}

@Composable
private fun MySaveFloatingButton(
    navController: NavHostController,
    saveToDOItemsViewModel: SaveToDOItemsViewModel,
    toDoString: String,
    context: Context
) {
    Column(
        modifier = Modifier.size(50.dp),
    ) {
        FloatingActionButton(
            onClick = {
                Log.v("sdas", toDoString)
                if(toDoString.equals("Error",ignoreCase = true)){
                    showError(navController,toDoString,context)
                }else if (toDoString != "") {
                    saveToDoItem(navController, saveToDOItemsViewModel, toDoString)
                }
            },
            shape = FloatingActionButtonDefaults.largeShape,
            containerColor = FloatingActionButtonDefaults.containerColor,
        ) {
            Icon(Icons.Filled.Save, "Save")
        }
    }
}

fun showError(navController: NavHostController, toDoString: String, context: Context) {
    Toast.makeText(context,"$toDoString - Failed to add TODO",Toast.LENGTH_LONG).show()
    navController.navigateUp()
}

fun saveToDoItem(
    navController: NavHostController,
    saveToDOItemsViewModel: SaveToDOItemsViewModel,
    toDoString: String
) {

    val newTodo = MyTodoEntity(todoDetails = toDoString)
    saveToDOItemsViewModel.saveToDOItem(newTodo)

    navController.navigate(Screens.ToDoList.route)
//    navController.navigateUp()
}

@Composable
private fun EditTodo(
    paddingValues: PaddingValues,
    values: String,
    onChange: (String) -> Unit,

    ) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = values,
            onValueChange = onChange
        )

    }

}


@Preview
@Composable
fun PreviewEditTodo() {
//    EditTodoScreen(navController = rememberNavController(),)
}