package com.pourkazemi.mahdi.kalastore.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import timber.log.Timber
import java.lang.reflect.Type

class SoleKalaDeserializer : JsonDeserializer<Kala> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Kala? {
        val kala = json?.asJsonObject?.let { jsonObject ->

            val listOfImage = mutableListOf<String>()
            val images = jsonObject.getAsJsonArray("images")
            for (image in images) {
                Timber.tag("mahdiTest").d("image")

                listOfImage.add(image?.asJsonObject?.get("src")?.asString ?: "null")
            }

            Kala(
                jsonObject.get("id").asInt,
                jsonObject.get("name").asString,
                jsonObject.get("price").asString,
                jsonObject.get("description").asString,
                listOfImage
            )
        }
        return kala
    }
}