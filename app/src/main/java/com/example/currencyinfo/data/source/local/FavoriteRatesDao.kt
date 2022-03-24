package com.example.currencyinfo.data.source.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FavoriteRatesDao {

    @Query("")
    suspend fun getFavorites()
    
}