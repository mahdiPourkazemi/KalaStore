package com.pourkazemi.mahdi.kalastore.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import timber.log.Timber
import java.lang.reflect.Type

class CustomerDeserializer : JsonDeserializer<Customer> {
    lateinit var customer: Customer
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Customer {
        json?.asJsonObject?.let { jsonObject ->
            customer = Customer(
                jsonObject.get("id").asInt,
                jsonObject.get("email").asString,
                jsonObject.get("first_name").asString,
                jsonObject.get("last_name").asString,
                jsonObject.get("username").asString,
            )
        }
        return customer
    }
}