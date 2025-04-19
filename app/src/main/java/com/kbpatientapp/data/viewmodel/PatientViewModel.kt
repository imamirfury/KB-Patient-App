package com.kbpatientapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kbpatientapp.data.db.model.Patient
import com.kbpatientapp.data.db.model.Visit
import com.kbpatientapp.data.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val repository: PatientRepository,
) :
    ViewModel() {


    val patients: StateFlow<List<Patient>> = repository.getAllPatients().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), emptyList()
    )

    private val _selectedPatient = MutableStateFlow<Patient?>(null)
    val selectedPatient: StateFlow<Patient?> = _selectedPatient

    private val _visits = MutableStateFlow<List<Visit>>(emptyList())
    val visits: StateFlow<List<Visit>> = _visits

    fun addPatient(patient: Patient, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repository.insertPatient(patient)
            onResult.invoke(id)
        }
    }

    fun getVisit(visitId: Int): Flow<Visit> {
        return repository.getVisitById(visitId)
    }

    fun selectPatient(id: Int) {
        viewModelScope.launch {
            val patient = repository.getPatient(id)
            _selectedPatient.value = patient
            repository.getVisitsForPatient(id).collect { _visits.value = it }
        }
    }

    fun addVisit(visit: Visit, onResult: (Long) -> Unit) {
        viewModelScope.launch {
            val visitId = repository.insertVisit(visit)
            onResult.invoke(visitId)
        }
    }

    fun markVisitAsCompletedOrUnCompleted(visit: Visit, status: Boolean) {
        visit.isCompleted = status
        viewModelScope.launch {
            repository.updateVisit(visit)
        }
    }

}