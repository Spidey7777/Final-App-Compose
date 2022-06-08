package com.example.randomuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.randomuser.database.DatabaseEmployee
import com.example.randomuser.database.EmployeesDatabase
import com.example.randomuser.database.getDatabase
import com.example.randomuser.network.EmployeeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList

class EmployeesRepository {
    lateinit var empList: LiveData<List<DatabaseEmployee>>
    lateinit var  database : EmployeesDatabase

    constructor(application: Application){
        database = getDatabase(application)
        empList = database.employeeDao.getEmployees()

    }


    suspend fun refreshList() {
        withContext(Dispatchers.IO) {
            val employeeList = EmployeeApi.RETROFIT_SERVICE.getValues().await()
//            Log.d("log ", "refreshList() called" + employeeList)

            val inthalist : ArrayList<DatabaseEmployee> = arrayListOf()

            employeeList.forEach {

                val inthaItem = DatabaseEmployee(it.id, it.name, it.email, it.gender, it.status)
                inthalist.add(inthaItem)

            }

            database.employeeDao.insertAll(inthalist)
        }
    }
}