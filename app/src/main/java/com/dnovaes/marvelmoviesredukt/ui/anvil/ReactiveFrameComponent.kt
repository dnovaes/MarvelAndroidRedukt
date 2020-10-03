package com.dnovaes.marvelmoviesredukt.ui.anvil

import android.content.Context
import com.dnovaes.marvelmoviesredukt.MarvelMoviesApplication
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.github.raulccabreu.redukt.states.StateListener

/**
 * Copyright 26/10/2016
 * Felipe Piñeiro (fpbitencourt@gmail.com),
 * Nilton Vasques (nilton.vasques@gmail.com) and
 * Raul Abreu (raulccabreu@gmail.com)
 *
 *
 * Be free to ask for help, email us!
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or
 * implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

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
abstract class ReactiveFrameComponent(context: Context) : FrameLayoutComponent(context),
        StateListener<AppState>, AnvilRenderable {

    private var stateChange = StateComponentHelper()
    private var anvilRenderListener: AnvilRenderListener? = null
    private var state: AppState? = null

    init {
        initialState()
        registered()
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

        if (hasWindowFocus) registered()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stateChange.onUnregisterOnStateChange(this)
        unregistered()
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState != state
    }

    open fun registered() { }

    open fun unregistered() { }
}
