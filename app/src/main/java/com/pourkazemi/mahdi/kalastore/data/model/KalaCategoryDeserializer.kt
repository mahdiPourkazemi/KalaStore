package com.pourkazemi.mahdi.kalastore.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import timber.log.Timber
import java.lang.reflect.Type

class KalaCategoryDeserializer: JsonDeserializer<List<KalaCategory>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<KalaCategory> {
        val kalaCategories = mutableListOf<KalaCategory>()
        json?.asJsonArray?.let { jsonArray ->
            for (kala in jsonArray) {
                kala.asJsonObject?.let { jsonObject ->

                    kalaCategories.add(
                        KalaCategory(
                            jsonObject.get("id").asInt,
                            jsonObject.get("name").asString,
                           jsonObject.getAsJsonObject("image")?.get("src")?.asString ?: "null"
                        )
                    )
                }
            }
        }
        return kalaCategories.toList()
    }
}