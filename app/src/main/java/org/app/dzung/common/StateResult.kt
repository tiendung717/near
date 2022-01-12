package org.app.dzung.common

sealed class StateResult<out T> {

    data class Success<out T>(val value: T) : StateResult<T>()

    data class GenericError(val throwable: Throwable? = null) : StateResult<Nothing>()

    object None : StateResult<Nothing>()

    object Loading : StateResult<Nothing>()

    @Throws(Exception::class)
    fun takeValueOrThrow(): T {
        return when (this) {
            is Success -> value
            is GenericError -> throw throwable ?: Throwable()
            else -> throw Throwable("Unknown the result type")
        }
    }
}