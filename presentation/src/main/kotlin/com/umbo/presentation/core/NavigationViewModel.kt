package com.umbo.presentation.core

import androidx.lifecycle.ViewModel
import com.umbo.data.NavigationCommand
import com.umbo.data.Navigator
import javax.inject.Inject

class NavigationViewModel @Inject constructor(private val navigator: Navigator) : ViewModel() {

    val liveData = SingleLiveEvent<NavigationCommand>()

    init {
        navigator.listener = {
            liveData.postValue(it)
        }
    }

    override fun onCleared() {
        navigator.listener = null
        super.onCleared()
    }
}