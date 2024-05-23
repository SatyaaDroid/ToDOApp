package com.app.todoapp

import com.app.todoapp.data.Repository.MainRepository
import com.app.todoapp.domain.GetSavedToDOStateHolder
import com.app.todoapp.viewmodel.GetToDOItemsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import androidx.lifecycle.Observer
import com.app.todoapp.data.local.entity.MyTodoEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito
import org.mockito.Mockito.*


class Test_GetToDOViewModel {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mainRepository: MainRepository

    private lateinit var viewModel: GetToDOItemsViewModel

    @Mock
    private lateinit var observer: Observer<GetSavedToDOStateHolder>


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = GetToDOItemsViewModel(mainRepository)
    }


    @Test
    fun returnSuccessWhenAddDataInDB() = runTest {
        val roverPhotoUiModel = MyTodoEntity(
           toDoId = 1,
            todoDetails = "asdds"
        )

        `when`(mainRepository.getToDOItemsFromDataBase).thenAnswer { Unit }


        viewModel.getSavedToDOItems()
//        getMarsRoverPhotoUseCase.savePhoto(roverPhotoUiModel)

        Mockito.verify(mainRepository).getToDOItemsFromDataBase.collect()

    }


}