package com.app.todoapp

import com.app.todoapp.data.Repository.MainRepository
import com.app.todoapp.data.local.entity.MyTodoEntity
import com.app.todoapp.viewmodel.GetToDOItemsViewModel
import com.app.todoapp.viewmodel.SaveToDOItemsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class Test_SaveToDOViewModel {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mainRepository: MainRepository


    private lateinit var saveViewModel: SaveToDOItemsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        saveViewModel = SaveToDOItemsViewModel(mainRepository)
    }


    @Test
    fun returnSuccessWhenAddDataInDB() = runTest {
        val toDOModel = MyTodoEntity(
            toDoId = 1,
            todoDetails = "as"
        )

        Mockito.`when`(mainRepository.saveToDO(toDOModel)).thenAnswer { Unit }


        saveViewModel.saveToDOItem(toDOModel)

        Mockito.verify(mainRepository).saveToDO(toDOModel)

    }
}