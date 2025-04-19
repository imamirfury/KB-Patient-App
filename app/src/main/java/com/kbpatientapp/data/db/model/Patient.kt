package com.kbpatientapp.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: Int,
    val gender: String,
    val contactNumber: String?,
    val location: String,
    val healthId: String,
    val isSynced : Boolean
)