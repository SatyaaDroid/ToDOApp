package com.app.todoapp.ui.navigation

import androidx.navigation.NavController
import com.app.todoapp.ui.navigation.Destination.TODOEDIT
import com.app.todoapp.ui.navigation.Destination.TODOLIST

sealed class Screens(
    val route:String
){
    data object ToDoList :Screens("ToDoListScreen")
    data object EditToDoScreen :Screens("ToDoEdit")
}

object Destination{
    const val TODOLIST = "ToDoListScreen"
    const val TODOEDIT = "ToDoEdit"
}

//class Action(navController: NavController){
//    val TODO_LIST:() -> Unit ={navController.navigate(TODOLIST)}
//    val TODO_EDIT:() -> Unit ={navController.navigate(TODOEDIT)}
//}


