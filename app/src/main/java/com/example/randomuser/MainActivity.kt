package com.example.randomuser

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.randomuser.database.DatabaseEmployee
import com.example.randomuser.network.EmployeeDetails
import com.example.randomuser.ui.theme.RandomUserTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, EmployeeViewModel.Factory(application)).get(EmployeeViewModel::class.java)
        setContent {
            RandomUserTheme {
//                viewModel.empList.value?.let { Employees(databaseEmployees = it) }
                val items by viewModel.empList.observeAsState()
                items?.let { Employees(databaseEmployees = it) }

            }
        }
    }

    @Composable
    fun EmployeeCard(databaseEmployee: DatabaseEmployee) {
        val gender = databaseEmployee.gender
        var drawable = 0
        if (gender == "male") {
            drawable = R.drawable.man
        }
        else {
            drawable = R.drawable.woman
        }
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = drawable),
                contentDescription = "man",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape))


            Spacer(modifier = Modifier.width(15.dp))
            
            Column {
                Text(
                    text = databaseEmployee.name,
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h5)
//                    modifier = Modifier.size(200.dp))
//                    modifier = Modifier.padding(10.dp))
            }
        }
    }

    @Composable
    fun Employees(databaseEmployees: List<DatabaseEmployee>) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 30.dp)
        ) {
            items(databaseEmployees) { empl ->
                EmployeeCard(databaseEmployee = empl)
            }
        }
    }

    @Preview
    @Composable
    fun PreviewEmployees() {
        RandomUserTheme {
            viewModel = ViewModelProvider(this, EmployeeViewModel.Factory(application!!)).get(EmployeeViewModel::class.java)
            val items by viewModel.empList.observeAsState()
            items?.let { Employees(databaseEmployees = it) }
        }
    }
}