package com.android.task.data.source.network

import com.android.task.R
import com.android.task.data.source.network.model.LabelItem
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LabelNetworkDataSource {
    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()


    suspend fun getLabel(): List<LabelItem> = accessMutex.withLock {
        return dummyData()
    }

    suspend fun search(text: String): List<LabelItem> = accessMutex.withLock {
        return dummyData().filter { it.text.lowercase().contains(text) }
    }


    private fun dummyData(): List<LabelItem> {
        val images = listOf(R.drawable.cat_1, R.drawable.cat_2, R.drawable.cat_3)
        return List(50) {
            LabelItem(image = images.random(), text = "Label $it ")
        }
    }
}