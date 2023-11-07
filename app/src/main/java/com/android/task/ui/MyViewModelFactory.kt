package com.android.task.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.task.data.repo.DefaultLabelRepository

class MyViewModelFactory(private val labelRepository: DefaultLabelRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityVM::class.java)) {
            return MainActivityVM(labelRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
