package com.pourkazemi.mahdi.kalastore.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import timber.log.Timber
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

                    val listOfImage = mutableListOf<String>()
                    val images = jsonObject.getAsJsonArray("images")
                    for (image in images) {
                        Timber.tag("mahdiTest").d("image")

                        listOfImage.add(image?.asJsonObject?.get("src")?.asString ?: "null")
                    }

                    kalas.add(
                        Kala(
                            jsonObject.get("id").asInt,
                            jsonObject.get("name").asString,
                         listOfImage
                        )
                    )
                }
            }
        }
        return kalas.toList()
    }
}