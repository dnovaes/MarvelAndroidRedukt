package com.dnovaes.marvelmoviesredukt.reducers

import com.dnovaes.marvelmoviesredukt.actions.Actions.UPDATE_SYNC
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class SyncReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(UPDATE_SYNC)
    fun startSync(state: AppState, status: Boolean): AppState {
        return state.copy(syncRunning = status)
    }
}
