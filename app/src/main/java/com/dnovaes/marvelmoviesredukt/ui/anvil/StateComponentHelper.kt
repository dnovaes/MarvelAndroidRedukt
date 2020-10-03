package com.dnovaes.marvelmoviesredukt.ui.anvil

import com.dnovaes.marvelmoviesredukt.MarvelMoviesApplication
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.github.raulccabreu.redukt.states.StateListener

class StateComponentHelper {

    private var isRegisteredOnStateChange = false

    fun onFocusChanged(hasFocus: Boolean, render: RenderListener, listener: StateListener<AppState>,
                       actualState: AppState?, newState: AppState) {
        if (hasFocus) {
            onRegisterOnStateChange(listener)
            if (actualState == null || listener.hasChanged(newState, actualState)) {
                listener.onChanged(newState)
                render.render()
            }
        }
    }

    fun onRegisterOnStateChange(listener: StateListener<AppState>) {
        if (isRegisteredOnStateChange) return

        isRegisteredOnStateChange = true
        MarvelMoviesApplication.redukt.listeners.add(listener)
    }

    fun onUnregisterOnStateChange(listener: StateListener<AppState>) {
        isRegisteredOnStateChange = false
        MarvelMoviesApplication.redukt.listeners.remove(listener)
    }
}