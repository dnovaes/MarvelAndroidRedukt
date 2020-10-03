package com.dnovaes.marvelmoviesredukt.ui.activities.base

import android.content.Intent
import android.graphics.Color.WHITE
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dnovaes.marvelmoviesredukt.MarvelMoviesApplication
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.ui.anvil.AnvilRenderListener
import com.dnovaes.marvelmoviesredukt.ui.anvil.AnvilRenderable
import com.dnovaes.marvelmoviesredukt.ui.anvil.LinearLayoutComponent
import trikita.anvil.Anvil
import trikita.anvil.DSL.backgroundColor
import trikita.anvil.DSL.orientation

abstract class ReactiveActivity : AppCompatActivity(), AnvilRenderable {

    private var activityResultCallback: ((Int, Int, Intent?) -> Unit)? = null
    var anvilRenderable: AnvilRenderListener? = null

    var layout: LinearLayoutComponent? = null

    protected val state: AppState
        get() = MarvelMoviesApplication.redukt.state

    override fun onResume() {
        super.onResume()
        layout?.render()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (hasTab()) supportActionBar?.elevation = 0F
        setContentView(object : LinearLayoutComponent(this) {
            override fun view() {
                orientation(VERTICAL)
                backgroundColor(WHITE)
                buildActionBar()
                content()
                layout = Anvil.currentView()
                anvilRenderable?.onAnvilRendered()
            }
        })
    }

    open fun buildActionBar() {}

    abstract fun content()

    open fun hasTab() = false

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResultCallback?.invoke(requestCode, resultCode, data)
    }

    fun setOnActivityResult(callback: ((Int, Int, Intent?) -> Unit)?) {
        activityResultCallback = callback
    }

    override fun setAnvilRenderListener(listener: AnvilRenderListener) {
        anvilRenderable = listener
    }
}
