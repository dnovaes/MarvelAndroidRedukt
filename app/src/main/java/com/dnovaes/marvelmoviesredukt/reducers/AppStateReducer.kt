package com.dnovaes.marvelmoviesredukt.reducers

import com.dnovaes.marvelmoviesredukt.actions.Actions.STATE_LOADED
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class AppStateReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(STATE_LOADED)
    fun loadStateStarted(state: AppState, empty: Any?): AppState {
        return state.copy(stateLoaded = true)
    }
}
