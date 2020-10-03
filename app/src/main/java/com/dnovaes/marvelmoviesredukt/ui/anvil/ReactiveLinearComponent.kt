package com.dnovaes.marvelmoviesredukt.ui.anvil

import android.content.Context
import com.dnovaes.marvelmoviesredukt.MarvelMoviesApplication
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.github.raulccabreu.redukt.states.StateListener

/**
 * We should not call the general Anvil.render() calls in every state changed.
 * Now each individual component should call ReactiveView#render()
 * when appropriated.
 *
 * Note: Recycler view doesn't work with individual render calls, so for screens
 * with recycler view, we should call the general Anvil.render() method.
 *
 * Remove native Anvil callbacks from the components, because those
 * callbacks calls Anvil.render() by default, and this can reduce the
 * performance a lot.
 */
abstract class ReactiveLinearComponent(context: Context) : LinearLayoutComponent(context),
        StateListener<AppState>, AnvilRenderable {

    private var stateChange = StateComponentHelper()
    private var anvilRenderListener: AnvilRenderListener? = null
    private var state: AppState? = null

    init {
        initialState()
    }

    protected open fun initialState() {
        stateChange.onRegisterOnStateChange(this)
    }

    override fun setAnvilRenderListener(listener: AnvilRenderListener) {
        anvilRenderListener = listener
    }

    protected fun onAnvilRendered() {
        anvilRenderListener?.onAnvilRendered()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        stateChange.onFocusChanged(hasWindowFocus, this, this, state, MarvelMoviesApplication.redukt.state)
        state = MarvelMoviesApplication.redukt.state
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stateChange.onUnregisterOnStateChange(this)
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState != state
    }
}
