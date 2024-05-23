package com.app.todoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "my_todo")
data class MyTodoEntity(
    @PrimaryKey(autoGenerate = true) @field:SerializedName("TODO_ID") val toDoId: Int = 0,
    @field:SerializedName("TODO_DETAILS") val todoDetails: String =""
)
