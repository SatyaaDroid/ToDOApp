package com.app.todoapp.data.di

import android.content.Context
import com.app.todoapp.data.local.dao.MyToDoDao
import com.app.todoapp.data.local.database.MyTodoDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNewsDao(@ApplicationContext context: Context): MyToDoDao =
        MyTodoDataBase.getInstance(context).MyToDoDao()

}