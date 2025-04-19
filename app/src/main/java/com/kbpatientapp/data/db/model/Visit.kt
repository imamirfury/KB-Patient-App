package com.kbpatientapp.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visits")
data class Visit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: Int,
    val visitDate: String,
    val symptoms : String,
    val diagnosis : String,
    val medicineName: String,
    val dosage: String,
    val frequency: String,
    val duration: String,
    var isCompleted: Boolean = false,
    val isSynced : Boolean
)