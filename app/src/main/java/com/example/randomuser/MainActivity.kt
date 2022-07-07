package com.example.randomuser

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.randomuser.database.DatabaseEmployee
import com.example.randomuser.network.EmployeeDetails
import com.example.randomuser.ui.theme.RandomUserTheme
import org.intellij.lang.annotations.JdkConstants

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, EmployeeViewModel.Factory(application)).get(EmployeeViewModel::class.java)
        setContent {
            RandomUserTheme {
                val items by viewModel.empList.observeAsState()
                items?.let { Employees(databaseEmployees = it) }
            }
        }
    }

    @Composable
    fun EmployeeCard(databaseEmployee: DatabaseEmployee) {

        val gender = databaseEmployee.gender
        var set_gender = 0
        if (gender == "male") {
            set_gender = R.drawable.man
        }
        else {
            set_gender = R.drawable.woman
        }


        val status = databaseEmployee.status
        var set_status = 0
        if (status == "active") {
            set_status = R.drawable.gtu
        }
        else {
            set_status = R.drawable.rtd
        }


        Row {
            Image(
                painter = painterResource(id = set_gender),
                contentDescription = "gender",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape))


            Spacer(modifier = Modifier.width(3.dp))
            
            Column(modifier = Modifier.padding(start = 7.dp, end = 0.dp)) {
                Text(
                    text = databaseEmployee.name,
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.width(250.dp)
                )
//                    modifier = Modifier.size(200.dp))
//                    modifier = Modifier.padding(10.dp))
                Text(
                    text = databaseEmployee.email,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    modifier = Modifier.width(250.dp)
                )
            }

            Spacer(modifier = Modifier.width(1.dp))

            Image(
                painter = painterResource(id = set_status),
                contentDescription = "status",
                modifier = Modifier
                    .size(50.dp)
            )
        }
    }

    @Composable
    fun Employees(databaseEmployees: List<DatabaseEmployee>) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            items(databaseEmployees) { empl ->
                EmployeeCard(databaseEmployee = empl)
                Divider(color = Color.LightGray)
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