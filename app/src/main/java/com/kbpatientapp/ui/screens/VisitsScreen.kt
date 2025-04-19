package com.kbpatientapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kbpatientapp.data.viewmodel.PatientViewModel
import com.kbpatientapp.ui.items.VisitListItem
import com.kbpatientapp.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitsScreen(
    navController: NavController,
    patientId: Long,
    viewModel: PatientViewModel = hiltViewModel()
) {
    viewModel.selectPatient(patientId.toInt())
    val patient = viewModel.selectedPatient.collectAsState()
    val visits = viewModel.visits.collectAsState()
    val patientName = patient.value?.name


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("$patientName's Visits") }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            })
        },
        floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = "Add Visit",
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
                        navController.navigate(Screen.VisitDetailsScreen.createRoute(patientId))
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(16.dp),
                    expanded = true
                )
        }) { paddings ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            if (visits.value.isEmpty()) {
                Text(
                    text = "No Visits yet!",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(visits.value) { visit ->
                        VisitListItem(visit) {
                            navController.navigate(
                                Screen.SummeryScreen.createRoute(
                                    patientId,
                                    visit.id.toLong()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}