package com.dnovaes.marvelmoviesredukt.ui.anvil

import android.content.Context
import android.widget.RelativeLayout
import trikita.anvil.Anvil

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
abstract class RelativeLayoutComponent(context: Context) : RelativeLayout(context),
        RenderListener, Anvil.Renderable {

    private val uiRenderHandler = UiRenderHandler()
    protected var hasChanged = false

    override fun render() {
        uiRenderHandler.render(this)
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Anvil.mount(this, this)
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Anvil.unmount(this, false)
    }

    fun renderIfChanged() {
        if(!this.hasChanged) return
        this.hasChanged = false
        render()
    }
}