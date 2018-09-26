package com.farmdrop.customer.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CategoryData() : RealmObject() {

    @PrimaryKey
    open var id: Int = 0

    open var name: String? = null

    open var permalink: String? = null

    open var imageUrl: String? = null

    open var children: RealmList<SubCategoryData>? = null

    constructor(category: Category) : this () {
        id = category.id
        name = category.name
        permalink = category.permalink
        imageUrl = category.imageUrl

        val categories = category.children
        if (categories != null && !categories.isEmpty()) {

            val children: RealmList<SubCategoryData> = RealmList()
            categories.forEach {
                children.add(SubCategoryData(it))
            }
            this.children = children
        }
    }
}