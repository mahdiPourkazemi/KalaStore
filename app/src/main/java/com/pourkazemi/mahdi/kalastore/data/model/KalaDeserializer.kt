package com.pourkazemi.mahdi.kalastore.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class KalaDeserializer : JsonDeserializer<List<Kala>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Kala> {
        val kalas = mutableListOf<Kala>()
        json?.asJsonArray?.let { jsonArray ->
            for (kala in jsonArray) {
                kala.asJsonObject?.let { jsonObject ->

                    val images = jsonObject.getAsJsonArray("images")?.asJsonObject
                    if (images?.has("src") == true) {
                        kalas.add(
                            Kala(
                                jsonObject.get("id").asInt,
                                jsonObject.get("name").asString,
                                images.get("src").asString
                            )
                        )
                    }
                }
            }
        }
        return kalas
    }
}