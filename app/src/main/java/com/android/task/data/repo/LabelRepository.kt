package com.android.task.data.repo

import com.android.task.data.source.network.LabelNetworkDataSource
import com.android.task.data.source.network.model.LabelItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


interface LabelRepository {
    fun getLabel(): Flow<List<LabelItem>>
    fun searchForLabel(text: String): Flow<List<LabelItem>>
}

class DefaultLabelRepository(
    private val remoteDataSource: LabelNetworkDataSource,
) : LabelRepository {
    override fun getLabel() = flow {
        emit(remoteDataSource.getLabel())
    }

    override fun searchForLabel(text: String) = flow {
        emit(remoteDataSource.search(text))
    }

}