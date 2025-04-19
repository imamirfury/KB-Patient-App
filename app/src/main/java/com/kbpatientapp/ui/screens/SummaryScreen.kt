package com.kbpatientapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kbpatientapp.data.db.model.Patient
import com.kbpatientapp.data.db.model.Visit
import com.kbpatientapp.data.viewmodel.PatientViewModel
import com.kbpatientapp.utils.shareOnWhatsApp
import com.kbpatientapp.utils.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    navController: NavHostController,
    patientId: Long,
    visitId: Long,
    viewModel: PatientViewModel = hiltViewModel()
) {
    viewModel.selectPatient(patientId.toInt())
    val visit = viewModel.getVisit(visitId.toInt()).collectAsState(initial = null).value
    val patient = viewModel.selectedPatient.collectAsState().value
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Visit Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PatientInfoCard(patient)
            VisitInfoCard(visit)
            PrescriptionCard(visit)

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { context.showToast("Printing...") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Print, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Print")
                }

                OutlinedButton(
                    onClick = { context.shareOnWhatsApp("Prescription") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Whatsapp, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Share")
                }
            }

            visit?.let {
                Button(
                    onClick = {
                        viewModel.markVisitAsCompletedOrUnCompleted(it, !it.isCompleted)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(if (!it.isCompleted) "Mark as Completed" else "Mark as Uncompleted")
                }
            }
        }
    }
}

@Composable
fun PatientInfoCard(patient: Patient?) {
    InfoCard(title = "Patient Information") {
        InfoRow("Name", patient?.name)
        InfoRow("Age", "${patient?.age}")
        InfoRow("Gender", patient?.gender)
        patient?.contactNumber?.let {
            InfoRow("Contact", it)
        }
        InfoRow("Location", patient?.location)
        InfoRow("Health ID", patient?.healthId)
    }
}

@Composable
fun VisitInfoCard(visit: Visit?) {
    InfoCard(title = "Visit Details") {
        InfoRow("Date", visit?.visitDate)
        InfoRow("Symptoms", visit?.symptoms, maxLines = Int.MAX_VALUE)
        InfoRow("Diagnosis", visit?.diagnosis, maxLines = Int.MAX_VALUE)
    }
}

@Composable
fun PrescriptionCard(visit: Visit?) {
    InfoCard(title = "Prescription") {
        InfoRow("Medicine", visit?.medicineName)
        InfoRow("Dosage", visit?.dosage)
        InfoRow("Frequency", visit?.frequency)
        InfoRow("Duration", visit?.duration)
    }
}

@Composable
fun InfoCard(title: String, content: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?, maxLines: Int = 2) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value?.ifBlank { "-" } ?: "-",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1.2f)
        )
    }
}