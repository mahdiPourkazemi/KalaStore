package com.pourkazemi.mahdi.kalastore.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey
    @Expose(serialize = false, deserialize = true)
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val username: String,
    @Expose(serialize = true, deserialize = false)
    val password:String=""
)
