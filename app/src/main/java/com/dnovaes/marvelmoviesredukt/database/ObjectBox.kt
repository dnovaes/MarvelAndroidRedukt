package com.dnovaes.marvelmoviesredukt.database

import android.content.Context
import com.dnovaes.marvelmoviesredukt.models.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    var boxStore: BoxStore? = null
        private set

    fun build(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()
    }
}