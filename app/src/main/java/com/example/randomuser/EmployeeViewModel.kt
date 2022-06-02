package com.example.randomuser

import android.app.Application
import androidx.lifecycle.*
import com.example.randomuser.database.getDatabase
import com.example.randomuser.repository.EmployeesRepository
import kotlinx.coroutines.launch

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = EmployeesRepository(database)

    init {
        viewModelScope.launch {
            repository.refreshList()
        }
    }

    val empList = repository.empList


    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EmployeeViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}