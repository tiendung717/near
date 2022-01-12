package org.app.dzung.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.app.dzung.ui.event.NavEvent
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor() : ViewModel() {
    var event by mutableStateOf<NavEvent>(NavEvent.None)
}