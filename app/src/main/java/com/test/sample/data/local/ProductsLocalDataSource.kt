package com.farmdrop.customer.data.local

import io.reactivex.Flowable
import io.realm.Case
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.oneOf
import io.realm.kotlin.where
import uk.co.farmdrop.library.utils.Constants.ZERO

private const val ID = "id"
private const val GRAPHQL_ID = "graphQLId"
private const val ID_NAME = "name"
private const val ID_PRODUCER = "producerId"
private const val ID_CATEGORY = "category"

class ProductsLocalDataSource {

    fun saveRealmData(realm: Realm, inputProductsData: List<ProductData>): List<ProductData> {
        val fullProductsData = ArrayList<ProductData>()
        inputProductsData.mapTo(fullProductsData) { productData ->
            val localProductData = productData.graphQLId?.let {
                getSingleEntryPlain(realm, it)
            }
            if (localProductData != null) {
                mergeProductDataInstances(localProductData, productData)
            } else {
                productData
            }
        }

        var returnedProductsData: List<ProductData>? = null
        realm.executeTransaction { transactionRealmInst ->
            returnedProductsData = transactionRealmInst.copyToRealmOrUpdate(fullProductsData)
        }
        return returnedProductsData ?: listOf()
    }

    private fun mergeProductDataInstances(localProductData: ProductData, remoteProductData: ProductData): ProductData {
        if (remoteProductData.introParagraph == null && localProductData.introParagraph != null) {
            remoteProductData.introParagraph = localProductData.introParagraph
        }
        if (remoteProductData.description == null && localProductData.description != null) {
            remoteProductData.description = localProductData.description
        }
        if (remoteProductData.origin == null && localProductData.origin != null) {
            remoteProductData.origin = localProductData.origin
        }
        if (remoteProductData.serves == null && localProductData.serves != null) {
            remoteProductData.serves = localProductData.serves
        }
        if (remoteProductData.shelfLifeDays == null && localProductData.shelfLifeDays != null) {
            remoteProductData.shelfLifeDays = localProductData.shelfLifeDays
        }
        if (remoteProductData.ingredients == null && localProductData.ingredients != null) {
            remoteProductData.ingredients = localProductData.ingredients
        }
        if (remoteProductData.cookingInstructions == null && localProductData.cookingInstructions != null) {
            remoteProductData.cookingInstructions = localProductData.cookingInstructions
        }
        if (remoteProductData.storageInformation == null && localProductData.storageInformation != null) {
            remoteProductData.storageInformation = localProductData.storageInformation
        }
        if (remoteProductData.nutrition == null && localProductData.nutrition != null) {
            remoteProductData.nutrition = localProductData.nutrition
        }
        if (localProductData.selectedVariantIndex != ZERO) {
            remoteProductData.selectedVariantIndex = localProductData.selectedVariantIndex
        }
        if (localProductData.quantity != ZERO) {
            remoteProductData.quantity = localProductData.quantity
        }
        if (remoteProductData.isAlcohol == null && localProductData.isAlcohol != null) {
            remoteProductData.isAlcohol = localProductData.isAlcohol
        }

        remoteProductData.productVariants?.let { remoteProductVariants ->
            val mergedProductVariants = RealmList<ProductData>()
            val localProductVariants = localProductData.productVariants
            remoteProductVariants.forEach { remoteVariantData ->
                val localProductVariant = localProductVariants?.find { it.graphQLId == remoteVariantData.graphQLId }
                if (localProductVariant != null) {
                    mergedProductVariants.add(mergeProductDataInstances(localProductVariant, remoteVariantData))
                } else {
                    mergedProductVariants.add(remoteVariantData)
                }
            }
            remoteProductData.productVariants = mergedProductVariants
        }
        if (remoteProductData.awards.isEmpty() && localProductData.awards.isNotEmpty()) {
            remoteProductData.awards = localProductData.awards
        }
        // if you're adding properties to ProductGraphQLFragment in Products.graphql, please make sure you copy them here.
        return remoteProductData
    }

    fun executeTransaction(realm: Realm, transaction: Realm.Transaction) = realm.executeTransaction(transaction)

    fun searchProducts(realm: Realm, query: String): Flowable<RealmResults<ProductData>> =
        realm.where(ProductData::class.java).contains(ID_NAME, query, Case.INSENSITIVE).findAll().asFlowable()

    fun searchProductsByProducer(realm: Realm, producerId: Int): Flowable<RealmResults<ProductData>> =
        realm.where(ProductData::class.java).equalTo(ID_PRODUCER, producerId).findAll().asFlowable()

    fun getAllEntriesInSpreeIdRangePlain(realm: Realm, spreeIds: Array<Int>): RealmResults<ProductData> =
        realm.where<ProductData>().oneOf(ID, spreeIds).findAll()

    fun getAllEntriesPlainCategory(realm: Realm, category: String): List<ProductData> =
        realm.where(ProductData::class.java).equalTo(ID_CATEGORY, category).findAll()

    private fun findSingleEntry(realm: Realm, id: Int): ProductData? =
        realm.where(ProductData::class.java).equalTo(ID, id).findFirst()

    fun getSingleEntry(realm: Realm, id: Int) = findSingleEntry(realm, id)?.asFlowable<ProductData>()

    fun getSingleEntry(realm: Realm, graphQLID: String) = getSingleEntryPlain(realm, graphQLID)?.asFlowable<ProductData>()

    fun getSingleEntryPlain(realm: Realm, graphQLID: String) = realm.where(ProductData::class.java).equalTo(GRAPHQL_ID, graphQLID).findFirst()
}
