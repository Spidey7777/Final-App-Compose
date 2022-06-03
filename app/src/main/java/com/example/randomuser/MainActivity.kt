package com.example.randomuser

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
                viewModel.empList.value?.let { Employees(databaseEmployees = it) }
                Log.d("Compose", "onCreate: ${viewModel.empList}")
            }
        }
    }

    @Composable
    fun EmployeeCard(databaseEmployee: DatabaseEmployee) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            if(databaseEmployee.gender == "male") {
                if (databaseEmployee.status == "active") {
                    Image(
                        painter = painterResource(id = R.drawable.man),
                        contentDescription = "lol",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MaterialTheme.colors.secondary)
                    )
                }
                else {
                    Image(
                        painter = painterResource(id = R.drawable.man),
                        contentDescription = "lol",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MaterialTheme.colors.secondary)
                    )
                }
            }
            else {
                if (databaseEmployee.status == "active") {
                    Image(
                        painter = painterResource(id = R.drawable.woman),
                        contentDescription = "lol",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MaterialTheme.colors.error)
                    )
                }
                else {
                    Image(
                        painter = painterResource(id = R.drawable.woman),
                        contentDescription = "lol",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MaterialTheme.colors.error)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
            
            Column {
                Text(
                    text = databaseEmployee.name,
                    color = MaterialTheme.colors.secondaryVariant, style = MaterialTheme.typography.h1)
            }
        }
    }

    @Composable
    fun Employees(databaseEmployees: List<DatabaseEmployee>) {
        LazyColumn {
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
            viewModel.empList.value?.let { Employees(databaseEmployees = it) }
        }
    }
}