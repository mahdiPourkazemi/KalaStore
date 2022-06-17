package com.pourkazemi.mahdi.kalastore.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ReceiveOrderDeserializer : JsonDeserializer<List<ReceiveOrder>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<ReceiveOrder> {
        val orders = mutableListOf<ReceiveOrder>()
        json?.asJsonArray?.let { jsonArray ->
            for (oder in jsonArray) {
                oder.asJsonObject?.let { jsonObject ->

                    val itemList = mutableListOf<Product>()

                    jsonObject.getAsJsonArray("line_items").let { line_items ->

                        for (item in line_items) {
                            item.asJsonObject?.let {
                                itemList.add(
                                    Product(
                                        it.get("product_id").asInt,
                                        it.get("quantity").asInt
                                    )
                                )
                            }
                        }
                    }
                    orders.add(
                        ReceiveOrder(
                            jsonObject.get("id").asInt,
                            jsonObject.get("status").asString,
                            itemList
                        )
                    )
                }
            }
        }
        return orders
    }
}