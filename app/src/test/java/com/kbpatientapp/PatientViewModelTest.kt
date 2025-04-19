package com.kbpatientapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kbpatientapp.data.db.model.Patient
import com.kbpatientapp.data.db.model.Visit
import com.kbpatientapp.data.repository.PatientRepository
import com.kbpatientapp.data.viewmodel.PatientViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever


// PatientViewModelTest.kt
@ExperimentalCoroutinesApi
class PatientViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: PatientRepository
    private lateinit var viewModel: PatientViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = PatientViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `selectPatient loads patient and visits correctly`() = runTest {
        // Arrange
        val patientId = 1
        val dummyPatient = Patient(
            id = patientId,
            name = "Alice",
            age = 28,
            gender = "Male",
            contactNumber = "8824449552",
            location = "Jaipur",
            healthId = "KB-38383838",
            isSynced = false
        )
        val dummyVisits = listOf(
            Visit(
                id = 10,
                patientId = patientId,
                visitDate = "19-4-2025",
                symptoms = "Corona",
                diagnosis = "Medicines",
                medicineName = "Citaphil",
                dosage = "5",
                frequency = "Constant",
                duration = "5 Days",
                isCompleted = false,
                isSynced = false
            ),
            Visit(
                id = 11,
                patientId = patientId,
                visitDate = "19-4-2025",
                symptoms = "Corona",
                diagnosis = "Medicines",
                medicineName = "Citaphil",
                dosage = "3",
                frequency = "Constant",
                duration = "6 Days",
                isCompleted = false,
                isSynced = false
            )
        )

        whenever(repository.getPatient(patientId)).thenReturn(dummyPatient)
        whenever(repository.getVisitsForPatient(patientId)).thenReturn(flowOf(dummyVisits))

        // Act
        viewModel.selectPatient(patientId)
        advanceUntilIdle()

        // Assert
        assertEquals(dummyPatient, viewModel.selectedPatient.value)
        assertEquals(dummyVisits, viewModel.visits.value)
    }
}