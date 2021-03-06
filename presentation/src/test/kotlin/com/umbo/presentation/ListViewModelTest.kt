package com.umbo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.umbo.data.*
import com.umbo.presentation.list.ListViewModel
import com.umbo.presentation.list.PhotoViewState
import com.umbo.presentation.list.PostToViewStateMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var rule2 = CoroutineTestRule()

    private lateinit var viewModel: ListViewModel

    @Mock
    lateinit var listInteractor: ListInteractor

    @Mock
    lateinit var navigator: Navigator

    lateinit var mapper: PostToViewStateMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mapper = PostToViewStateMapper()
        viewModel = ListViewModel(Dispatchers.IO, navigator, listInteractor, mapper)
    }

    @Test
    fun testInteractorReturnError() {
        //Given
        runBlocking {
            whenever(listInteractor.photos()).thenReturn(Outcome.Error())
        }

        //Given
        val outcome = viewModel.liveData.getOrAwaitValue()

        //Then
        Assert.assertTrue(outcome is Outcome.Error)
    }

    @Test
    fun testInteractorReturnSuccess() {
        //Given
        runBlocking {
            whenever(listInteractor.photos()).thenReturn(Outcome.Success(listOf(Photo(1,1,"success","",""))))
        }

        //Given
        val outcome = viewModel.liveData.getOrAwaitValue()
        val value = (outcome as? Outcome.Success)?.value

        //Then
        Assert.assertTrue(outcome is Outcome.Success)
        Assert.assertTrue(value?.get(0)?.description ?: "" == "success")
    }

    @Test
    fun testNavigationSuccess() {

        val payload = DetailPayload(1)

        //Given
        runBlocking {
            whenever(listInteractor.navigationPayload(1)).thenReturn(Outcome.Success(payload))
        }

        //Given
        viewModel.onItemClick(1)

        //Then
        verify(navigator).routeTo(NavigationCommand(Destination.DETAIL, payload))
    }

    @Test
    fun testNavigationError() {

        val payload = DetailPayload(1)
        var viewstate : Outcome<List<PhotoViewState>>? = null

        //Given
        runBlocking {
            whenever(listInteractor.navigationPayload(1)).thenReturn(Outcome.Error())
        }

        val observer = androidx.lifecycle.Observer<Outcome<List<PhotoViewState>>> {
            viewstate = it
        }
        viewModel.liveData.observeForever(observer)

        //Given
        viewModel.onItemClick(1)

        //Then
        verify(navigator, never()).routeTo(any())
        assert(viewstate is Outcome.Error)

        viewModel.liveData.removeObserver(observer)
    }
}