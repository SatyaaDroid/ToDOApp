package com.app.todoapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.todoapp.data.local.dao.MyToDoDao
import com.app.todoapp.data.local.entity.MyTodoEntity

@Database(
    entities = [MyTodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyTodoDataBase :RoomDatabase(){


    abstract fun MyToDoDao() : MyToDoDao

    companion object{

        @Volatile
        private var INSTANCE : MyTodoDataBase? = null

        fun getInstance(context: Context): MyTodoDataBase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it}
            }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context,
                MyTodoDataBase::class.java,"mytodo.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}