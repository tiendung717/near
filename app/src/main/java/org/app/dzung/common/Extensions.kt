package org.app.dzung.common

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T> ViewModel.getState(
    mutableState: MutableState<StateResult<T>>,
    callback: suspend () -> StateResult<T>
) {
    viewModelScope.launch {
        with(this@getState) {
            mutableState.value = StateResult.Loading
            mutableState.value = callback()
        }
    }
}

suspend fun <T> call(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    dataOperation: suspend () -> T
): StateResult<T> {
    return withContext(dispatcher) {
        try {
            StateResult.Success(dataOperation.invoke())
        } catch (throwable: Throwable) {
            StateResult.GenericError(throwable)
        }
    }
}