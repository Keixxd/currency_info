package com.example.currencyinfo.data.source.local

import androidx.room.*
import com.example.currencyinfo.domain.model.apientity.Rates
import com.example.currencyinfo.domain.model.roomentity.DbRates

@Dao
interface FavoriteRatesDao {

    @Query("SELECT * FROM db_rates ")
    suspend fun getRatesFromDb(): List<DbRates>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRate(rate: DbRates)

    @Update
    suspend fun updateRate(rate: DbRates)

    @Query("DELETE FROM db_rates")
    fun deleteAllRates()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRates(rates: List<DbRates>)

    @Transaction
    fun updateRatesTransaction(rates: List<DbRates>){
        deleteAllRates()
        insertAllRates(rates)
    }

}