package com.example.learningwidget.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningwidget.data.mapper.Response
import com.example.learningwidget.utils.ErrorType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<Intent,State,Effect,Action>(
    initialState: State
): ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state : StateFlow<State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val effect: SharedFlow<Effect> = _effect

    abstract fun processIntent(intent: Intent)
    fun setEffect(builder:() -> Effect){
        viewModelScope.launch {
            _effect.emit(builder())
        }
    }
    fun setState(reducer:State.() -> State){ // todo thick about it
        _state.value = _state.value.reducer()
    }
    protected abstract fun processAction(action: Action)

    // Helper جنرال برای مدیریت loading/error در عملیات suspend
    protected fun <T> launchWithResponse(
        operation: suspend () -> Response<T>,
        onSuccess: (T) -> Unit,
        onError: (ErrorType) -> Unit
    ) {
        viewModelScope.launch {
            val result = operation()
            when (result) {
                is Response.Success -> onSuccess(result.data)
                is Response.Failure -> onError(result.exception)
            }
        }
    }
}