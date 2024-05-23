package com.app.todoapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.todoapp.data.Repository.MainRepository
import com.app.todoapp.data.local.entity.MyTodoEntity
import com.app.todoapp.domain.SavedStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveToDOItemsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) :ViewModel(){

    private val _savedToDOItemsStateHolder = mutableStateOf(SavedStateHolder())
    val savedToDOItemsStateHolder: State<SavedStateHolder> = _savedToDOItemsStateHolder


    fun saveToDOItem(myTodoEntity: MyTodoEntity){
        viewModelScope.launch {
            _savedToDOItemsStateHolder.value = SavedStateHolder(isLoading = true)
            try{
                mainRepository.saveToDO(myTodoEntity)
                delay(3000)
                _savedToDOItemsStateHolder.value =SavedStateHolder(data =  null)
            }catch (e:Exception){
                _savedToDOItemsStateHolder.value = SavedStateHolder(error = e.message.toString())
            }
        }
    }
}