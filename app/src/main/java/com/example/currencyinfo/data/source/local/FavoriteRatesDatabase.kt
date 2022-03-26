package com.example.currencyinfo.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyinfo.domain.model.roomentity.DbRates

@Database(entities = [DbRates::class], version = 1, exportSchema = false)
abstract class FavoriteRatesDatabase : RoomDatabase() {

    abstract fun getDao(): FavoriteRatesDao

    companion object{
        private var INSTANCE: FavoriteRatesDatabase? = null

        fun getDatabase(context: Context): FavoriteRatesDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteRatesDatabase::class.java,
                    "rates_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}