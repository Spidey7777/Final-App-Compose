package com.example.randomuser.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EmployeeDao {
    @Query(value = "select * from databaseemployee")
    fun getEmployees(): LiveData<List<DatabaseEmployee>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(employees_list : ArrayList<DatabaseEmployee>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg employees:DatabaseEmployee)

}

@Database(entities = [DatabaseEmployee::class], version = 1)
abstract class EmployeesDatabase: RoomDatabase() {
    abstract val employeeDao: EmployeeDao
}

private lateinit var INSTANCE: EmployeesDatabase

fun getDatabase(context: Context): EmployeesDatabase {
    synchronized(EmployeesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, EmployeesDatabase::class.java, "DatabaseEmployee").build()
        }
    }
    return INSTANCE
}