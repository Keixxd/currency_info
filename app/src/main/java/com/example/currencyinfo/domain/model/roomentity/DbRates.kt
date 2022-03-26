package com.example.currencyinfo.domain.model.roomentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_rates")
data class DbRates(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "rate_name")
    val name: String,
    @ColumnInfo(name = "rate_value")
    val value: Double,
    @ColumnInfo(name = "rate_favorite")
    val favorite: Boolean
)