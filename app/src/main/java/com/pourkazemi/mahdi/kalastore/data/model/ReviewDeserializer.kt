package com.pourkazemi.mahdi.kalastore.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import timber.log.Timber
import java.lang.reflect.Type

class ReviewDeserializer: JsonDeserializer<List<Review>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Review> {
        val reviews= mutableListOf<Review>()
        json?.asJsonArray?.let { jsonArray ->
            for (review in jsonArray) {
                review.asJsonObject?.let { jsonObject ->

                    reviews.add(
                        Review(
                            jsonObject.get("id").asInt,
                            jsonObject.get("product_id").asInt,
                            jsonObject.get("review").asString,
                            jsonObject.get("reviewer").asString,
                            jsonObject.get("reviewer_email").asString,
                            jsonObject.get("rating").asInt,
                            jsonObject.getAsJsonObject("reviewer_avatar_urls").get("96").asString
                        )
                    )
                }
            }
        }
        return reviews.toList()
    }
}