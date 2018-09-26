package com.farmdrop.customer.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ProducerData() : RealmObject() {

    constructor(producer: Producer) : this() {
        id = producer.id
        name = producer.name
        wholesalerName = producer.wholesalerName
        longDescription = producer.longDescription
        shortDescription = producer.shortDescription
        permalink = producer.permalink
        location = producer.location

        val imageDataList: RealmList<ImageData> = RealmList()
        val imageList = producer.images
        if (!(imageList == null || imageList.isEmpty())) {
            imageList.map(::ImageData).forEach {
                imageDataList.add(it)
            }
            images = imageDataList
        }
    }

    constructor(producerData: ProducerData) : this() {
        id = producerData.id
        name = producerData.name
        wholesalerName = producerData.wholesalerName
        longDescription = producerData.longDescription
        shortDescription = producerData.shortDescription
        permalink = producerData.permalink
        location = producerData.location
        images = producerData.images
    }

    @PrimaryKey
    open var id: Int = 0

    open var name: String? = null

    open var wholesalerName: String? = null

    open var longDescription: String? = null

    open var shortDescription: String? = null

    open var permalink: String? = null

    open var location: String? = null

    open var images: RealmList<ImageData>? = null

    open var isProducerOfWeek = false

    fun isShortDescriptionEmpty(): Boolean {
        return shortDescription.isNullOrEmpty()
    }

    fun isLongDescriptionEmpty(): Boolean {
        return longDescription.isNullOrEmpty()
    }
}