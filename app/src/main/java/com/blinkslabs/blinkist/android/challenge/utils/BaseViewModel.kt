package com.blinkslabs.blinkist.android.challenge.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Extend this BaseViewModel class in all your ViewModel classes
 * to handle UiStates, UiEffects and UiEvents
 */
abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect>() : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    val currentState: State get() = uiState.value

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    /**
     * Collect and listen to each event emission
     */
    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    /**
     * Handle each Ui event
     */
    abstract fun handleEvent(event: Event)

    /**
     * This function is used to set a new event
     */
    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch {
            _event.emit(
                newEvent,
            )
        }
    }

    /**
     * This function is used to set new Ui state
     */
    protected fun setState(reduce: State.() -> State) {
        val newState: State = currentState.reduce()
        _uiState.value = newState
    }

    /**
     * This function is used to set new effect value
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch {
            _effect.send(
                effectValue,
            )
        }
    }
}