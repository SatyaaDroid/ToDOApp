package com.app.todoapp.domain

import com.app.todoapp.data.local.entity.MyTodoEntity

data class GetSavedToDOStateHolder(
    val isLoading :Boolean = false,
    val data:List<MyTodoEntity>? = null,
    val error: String = ""
)
