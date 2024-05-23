package com.app.todoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.todoapp.data.local.entity.MyTodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(myTodoEntity: MyTodoEntity)

    @Query("SELECT * FROM my_todo ORDER BY toDoId DESC")
    fun getAllSaved(): Flow<List<MyTodoEntity>>
}