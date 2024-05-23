package com.app.todoapp.domain

data class SavedStateHolder(
    val isLoading: Boolean = false,
    val data: Any? = null,
    val error: String = ""
)
