package com.app.todoapp.data.Repository

import com.app.todoapp.data.local.dao.MyToDoDao
import com.app.todoapp.data.local.entity.MyTodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val myToDoDao: MyToDoDao
) {

    suspend fun saveToDO(myTodoEntity: MyTodoEntity){
        myToDoDao.insert(myTodoEntity)
    }

    val getToDOItemsFromDataBase: Flow<List<MyTodoEntity>> = myToDoDao.getAllSaved()

}