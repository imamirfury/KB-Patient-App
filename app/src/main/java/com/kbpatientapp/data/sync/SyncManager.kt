package com.kbpatientapp.data.sync

import com.kbpatientapp.data.db.dao.PatientDao
import com.kbpatientapp.data.db.dao.VisitDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SyncManager @Inject constructor(
    private val visitDao: VisitDao,
    private val patientDao : PatientDao
) {

    fun syncUnSyncedData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val unSyncedPatients = patientDao.getUnSyncedPatients().first()
                val unSyncedVisits = visitDao.getUnSyncedVisits().first()
                unSyncedPatients.forEach { patient ->
                    // TODO: Sync data to remote server
                    // TODO: update UnSynced patients in to db
                    patientDao.updatePatient(patient.copy(isSynced = true))
                    Timber.d("Syncing Patient")
                }

                unSyncedVisits.forEach { visit ->
                    // TODO: Sync data to remote server
                    // TODO: update UnSynced visits in to db
                    visitDao.updateVisit(visit.copy(isSynced = true))
                    Timber.d("Syncing visit")
                }

            } catch (e: Exception) {
                Timber.d("Error syncing visits ${e.message}")
            }
        }
    }
}