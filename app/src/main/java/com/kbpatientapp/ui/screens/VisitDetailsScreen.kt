package com.kbpatientapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kbpatientapp.data.db.model.Visit
import com.kbpatientapp.data.viewmodel.PatientViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitDetailsScreen(
    navController: NavHostController,
    patientId: Long,
    viewModel: PatientViewModel = hiltViewModel()
) {
    val dateFormat = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) }
    val today = dateFormat.format(Date())

    var symptoms by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }
    var medicineName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    val isFormValid = symptoms.isNotBlank() && diagnosis.isNotBlank() &&
            medicineName.isNotBlank() && dosage.isNotBlank() &&
            frequency.isNotBlank() && duration.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Visit Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ReadOnlyField("Visit Date", today)

            CustomTextField("Symptoms", symptoms, { if (it.length < 300) symptoms = it })
            CustomTextField("Diagnosis", diagnosis, { if (it.length < 300) diagnosis = it })
            CustomTextField(
                "Medicine Name",
                medicineName,
                { if (it.length < 50) medicineName = it },
                singleLine = true
            )
            CustomTextField(
                "Dosage",
                dosage,
                { if (it.length < 50) dosage = it },
                singleLine = true,
                keyboardType = KeyboardType.Number
            )
            CustomTextField(
                "Frequency",
                frequency,
                { if (it.length < 50) frequency = it },
                singleLine = true
            )
            CustomTextField(
                "Duration",
                duration,
                { if (it.length < 50) duration = it },
                singleLine = true, imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val visit = Visit(
                        patientId = patientId.toInt(),
                        visitDate = today,
                        symptoms = cleanInput(symptoms),
                        diagnosis = cleanInput(diagnosis),
                        medicineName = medicineName,
                        dosage = dosage,
                        frequency = frequency,
                        duration = duration,
                        isCompleted = false,
                        isSynced = false
                    )
                    viewModel.addVisit(visit) {
                        navController.popBackStack()
                    }
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Register")
            }
        }
    }
}

@Composable
fun ReadOnlyField(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        enabled = false,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black,
            disabledLabelColor = Color.Gray
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else 4,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = imeAction
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

fun cleanInput(input: String): String {
    return input.replace(Regex("[\\s\\n]+"), " ").trim()
}
