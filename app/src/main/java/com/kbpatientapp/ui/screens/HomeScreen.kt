package com.kbpatientapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kbpatientapp.data.viewmodel.PatientViewModel
import com.kbpatientapp.navigation.Screen
import com.kbpatientapp.ui.items.PatientListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: PatientViewModel = hiltViewModel()
) {
    val patients by viewModel.patients.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Patients") })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = "Register",
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                onClick = {
                    navController.navigate(Screen.RegistrationScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp),
                expanded = true
            )

        }
    ) { paddings ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            if (patients.isEmpty()) {
                Text(
                    text = "No Patients registered yet!",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(patients) { patient ->
                        PatientListItem(patient = patient, onClick = {
                            navController.navigate(Screen.VisitsScreen.createRoute(patient.id.toLong()))
                        })
                    }
                }
            }
        }
    }
}