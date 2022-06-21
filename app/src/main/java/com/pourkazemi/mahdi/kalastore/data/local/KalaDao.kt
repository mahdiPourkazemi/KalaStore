package com.pourkazemi.mahdi.kalastore.data.local

import androidx.room.*
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface KalaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKala(kala: Kala)

    @Delete
    suspend fun deleteKala(kala:Kala)

    @Query("SELECT * FROM kala")
    fun getAllKala(): Flow<List<Kala>>
}*/
