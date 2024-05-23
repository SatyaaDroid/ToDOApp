package com.app.todoapp.ui.screens

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.todoapp.R
import com.app.todoapp.data.local.entity.MyTodoEntity
import com.app.todoapp.ui.navigation.Destination
import com.app.todoapp.ui.navigation.Screens
import com.app.todoapp.viewmodel.GetToDOItemsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    navController: NavHostController,
    getToDOItemsViewModel: GetToDOItemsViewModel,
    context: Context
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    BackHandler(enabled = currentDestination == Screens.ToDoList.route) {
        if (currentDestination == Destination.TODOLIST) {
            (context as? Activity)?.finish()
        }
    }

    var toDOItems by remember { mutableStateOf<List<MyTodoEntity>>(emptyList()) }
    val result = getToDOItemsViewModel.getSavedToDOItemsStateHolder.value
    if(result.error.isNotBlank()){
        // show error
    }
    result.data?.let {
        toDOItems = it
    }

    if(result.isLoading){
        Loading()
    }else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.todo_app))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.surface,
                        navigationIconContentColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            floatingActionButton = { ->
                MyFloatingButton(navController)
            },
            floatingActionButtonPosition = FabPosition.End,
//        content = { paddingValues ->
//            CheckTodo(
//                paddingValues = paddingValues
//            )
//        }
        ) { paddingValues ->
            CheckTodo(paddingValues, toDOItems)
        }
    }
}

@Composable
private fun MyFloatingButton(navController: NavHostController) {
    Column(
        modifier = Modifier.size(50.dp),
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(Screens.EditToDoScreen.route)
            },
            shape = FloatingActionButtonDefaults.largeShape,
            containerColor = FloatingActionButtonDefaults.containerColor,
        ) {
            Icon(Icons.Filled.Add, "")
        }
    }
}

@Composable
private fun CheckTodo(paddingValues: PaddingValues, todoItem: List<MyTodoEntity>,modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {

        Log.v("TODO","CheckTodo itemSize ${todoItem.size}")
        val textState = remember {
            mutableStateOf(TextFieldValue(""))
        }
        SearchView(state = textState, placeHolder = stringResource(R.string.search), modifier = modifier)

        val searchedText = textState.value.text
        val filteredTodoItems = if (searchedText.isEmpty()) {
            todoItem
        } else {
            todoItem.filter {
                it.todoDetails.contains(searchedText,ignoreCase = true)
            }
        }
        if(filteredTodoItems.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = stringResource(R.string.press_the_button_to_add_a_todo_item))
            }
        }else{
            LazyColumn(
            ) {
                items(filteredTodoItems){ item->
                    ToDoItem(item)
                }
//                items(count = todoItem.size, itemContent = { index ->
//                    ToDoItem(myTodoEntity = todoItem[index])
//                })
            }
        }

    }
}

@Composable
fun SearchView(
    state: MutableState<TextFieldValue>,
    placeHolder: String,
    modifier: Modifier
) {
    TextField(
        value = state.value,
        onValueChange = {value->
            state.value = value
        },
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(2.dp, Color.LightGray, RoundedCornerShape(15.dp)),
        placeholder = {
            Text(text = placeHolder)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
        ),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black, fontSize = 20.sp
        )
    )

}

@Composable
private fun ToDoItem(
    myTodoEntity: MyTodoEntity
) {
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = myTodoEntity.todoDetails, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
private fun PrevItem() {
//    ToDoItem(myTodoEntity = MyTodoEntity(1,"sajkdhsk"))
//    ToDoListScreen(navController = rememberNavController(), getToDOItemsViewModel = hiltViewModel())
}

