package com.app.todoapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.todoapp.data.Repository.MainRepository
import com.app.todoapp.data.local.entity.MyTodoEntity
import com.app.todoapp.domain.GetSavedToDOStateHolder
import com.app.todoapp.domain.SavedStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetToDOItemsViewModel  @Inject constructor(
    private val mainRepository: MainRepository

) :ViewModel(){

    private val _getSavedToDOItemsStateHolder = mutableStateOf(GetSavedToDOStateHolder())
    val getSavedToDOItemsStateHolder: State<GetSavedToDOStateHolder> = _getSavedToDOItemsStateHolder

//    private val _savedToDOItemsStateHolder = mutableStateOf(SavedStateHolder())
//    val savedToDOItemsStateHolder: State<SavedStateHolder> = _savedToDOItemsStateHolder

    init {
        getSavedToDOItems()
    }

    fun getSavedToDOItems() {
        viewModelScope.launch {
            _getSavedToDOItemsStateHolder.value = GetSavedToDOStateHolder(isLoading = true)
            try{
                mainRepository.getToDOItemsFromDataBase.collect{
                    _getSavedToDOItemsStateHolder.value = GetSavedToDOStateHolder(
                        data = it
                    )
                }

            }catch (e:Exception){
                _getSavedToDOItemsStateHolder.value = GetSavedToDOStateHolder(error = e.message.toString())
            }
        }
    }

//    fun saveToDOItem(myTodoEntity: MyTodoEntity){
//        viewModelScope.launch {
//            _savedToDOItemsStateHolder.value = SavedStateHolder(isLoading = true)
//            delay(3000)
//            try{
//                mainRepository.saveToDO(myTodoEntity)
//                _savedToDOItemsStateHolder.value =SavedStateHolder(data =  null)
//            }catch (e:Exception){
//                _savedToDOItemsStateHolder.value = SavedStateHolder(error = e.message.toString())
//            }
//        }
//    }

}