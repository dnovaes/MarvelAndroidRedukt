package com.dnovaes.marvelmoviesredukt.database

import android.content.Context
import com.dnovaes.marvelmoviesredukt.models.MyObjectBox
import io.objectbox.BoxStore
import timber.log.Timber

object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun build(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()
    }

    fun <T> put(movie: T, clazz: Class<T>) {
        val box = boxStore.boxFor(clazz)
        box.remove(movie)
        box.put(movie)
    }

    fun <T> putAll(list: List<T>, clazz: Class<T>) {
        if (list.isEmpty()) return
        boxStore.boxFor(clazz).put(list)
    }

    fun <T> getAll(clazz: Class<T>, offset: Long, limit: Long): List<T> {
        val result = boxStore.boxFor(clazz)?.query()?.build()?.find(offset, limit)
        Timber.v("Get all ${clazz.simpleName}, returned ${result?.count() ?: 0} items, $result")
        return result ?: listOf()
    }

    fun <T>removeAll(clazz: Class<T>) {
        boxStore.boxFor(clazz).removeAll()
    }
}