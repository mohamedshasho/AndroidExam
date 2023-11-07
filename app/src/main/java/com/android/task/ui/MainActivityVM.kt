package com.android.task.ui

import androidx.lifecycle.ViewModel
import com.android.task.R
import com.android.task.data.repo.LabelRepository
import com.android.task.data.source.network.model.ImageSlider
import com.android.task.data.source.network.model.LabelItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivityVM(
    private val repo: LabelRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _items = MutableStateFlow<List<LabelItem>>(emptyList())
    val items: Flow<List<LabelItem>> = _items


    init {
        scope.launch {
            val labelsFlow = repo.getLabel()
            labelsFlow.collect {
                _items.value = it
            }
        }
    }

    fun search(text: String) {
        scope.launch {
            val labelsFlow = repo.searchForLabel(text)
            labelsFlow.collect { _items.value = it }
        }
    }

    fun getHeaders(): List<ImageSlider> {
        return listOf(
            ImageSlider(R.drawable.cat_1),
            ImageSlider(R.drawable.cat_2),
            ImageSlider(R.drawable.cat_3),
        )
    }
}