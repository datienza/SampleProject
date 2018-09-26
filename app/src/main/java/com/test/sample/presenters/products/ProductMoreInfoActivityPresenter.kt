package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.ProductMoreInfoActivityContract
import com.farmdrop.customer.data.model.nutrition.NutritionContents
import com.farmdrop.customer.data.model.nutrition.NutritionData
import com.farmdrop.customer.data.model.nutrition.NutritionFieldData
import com.farmdrop.customer.utils.constants.CALORIES
import com.farmdrop.customer.utils.constants.CARBOHYDRATES
import com.farmdrop.customer.utils.constants.CARBOHYDRATES_POLYOLS
import com.farmdrop.customer.utils.constants.CARBOHYDRATES_STARCH
import com.farmdrop.customer.utils.constants.CARBOHYDRATES_SUGAR
import com.farmdrop.customer.utils.constants.CORE
import com.farmdrop.customer.utils.constants.ENERGY_KJ
import com.farmdrop.customer.utils.constants.FAT
import com.farmdrop.customer.utils.constants.FAT_MONOUNSATURATES
import com.farmdrop.customer.utils.constants.FAT_POLYUNSATURATES
import com.farmdrop.customer.utils.constants.FAT_SATURATES
import com.farmdrop.customer.utils.constants.FIBRE
import com.farmdrop.customer.utils.constants.MINERALS
import com.farmdrop.customer.utils.constants.PROTEIN
import com.farmdrop.customer.utils.constants.SALT
import com.farmdrop.customer.utils.constants.VITAMINS
import com.farmdrop.customer.utils.constants.COOKING_INSTRUCTIONS
import com.farmdrop.customer.utils.constants.INGREDIENTS
import com.farmdrop.customer.utils.constants.NUTRITIONAL_INFO
import com.farmdrop.customer.utils.constants.ProductMoreInfoTypes
import com.farmdrop.customer.utils.constants.STORAGE_INFO
import timber.log.Timber
import uk.co.farmdrop.library.ui.base.BasePresenter
import uk.co.farmdrop.library.utils.Constants.ZERO_F

class ProductMoreInfoActivityPresenter(private val productsRepository: ProductsRepository, private val productGraphQLId: String?, private val currentDeliverySlot: CurrentDeliverySlot) : BasePresenter<ProductMoreInfoActivityContract.View>() {

    fun loadData(@ProductMoreInfoTypes type: Int) {
        if (productGraphQLId != null) {
            productsRepository.getProductGraphQL(productGraphQLId, currentDeliverySlot.getCurrentDropCycleId(), false)?.let { productFlowable ->
                addDisposable(productFlowable.subscribe({ productData: ProductData ->
                    displayData(type, productData)
                }, {
                    Timber.e(it)
                }))
            }
        }
    }

    private fun displayData(type: Int, productData: ProductData) {

        when (type) {
            INGREDIENTS -> {
                mvpView.displayMoreInfoText(productData.ingredients)
            }
            COOKING_INSTRUCTIONS -> {
                mvpView.displayMoreInfoText(productData.cookingInstructions)
            }
            STORAGE_INFO -> {
                mvpView.displayMoreInfoText(productData.storageInformation)
                productData.shelfLifeDays?.let {
                    mvpView.displayShelfLife(productData.shelfLifeDays)
                }
            }
            NUTRITIONAL_INFO -> {
                productData.nutrition?.let {
                    displayNutritionalInfo(it)
                }
            }
            else -> {
                print("incorrect product extra info type")
            }
        }
    }

    private fun displayNutritionalInfo(nutritionData: NutritionData) {
        mvpView.initNutritionRecyclerView()
        val nutritionContents = ArrayList<NutritionContents>()
        nutritionData.servingUnit?.let { servingUnit ->
            nutritionContents.add(NutritionContents.Header(servingUnit))
        }
        nutritionContents.add(NutritionContents.Category(CORE))

        val doubleBottomPadding = mvpView.getDoubleNutrientLayoutBottomPadding()
        val singleBottomPadding = mvpView.getSingleNutrientLayoutBottomPadding()

        nutritionData.findCoreNutritionField(FAT)?.let { fatNutritionData ->
            addPrimaryNutrientsWithSecondaryNutrients(nutritionContents, fatNutritionData, doubleBottomPadding, singleBottomPadding, createCoreNutritionDataListFromType(nutritionData, FAT_MONOUNSATURATES, FAT_POLYUNSATURATES, FAT_SATURATES))
        }

        nutritionData.findCoreNutritionField(CARBOHYDRATES)?.let { carbsNutritionData ->
            addPrimaryNutrientsWithSecondaryNutrients(nutritionContents, carbsNutritionData, doubleBottomPadding, singleBottomPadding, createCoreNutritionDataListFromType(nutritionData, CARBOHYDRATES_POLYOLS, CARBOHYDRATES_STARCH, CARBOHYDRATES_SUGAR))
        }

        nutritionData.findCoreNutritionField(CALORIES)?.let { calories ->
            nutritionContents.add(NutritionContents.PrimaryNutrient(calories, doubleBottomPadding))
        }
        createCoreNutritionDataListFromType(nutritionData, ENERGY_KJ, FIBRE, PROTEIN, SALT).forEach { nutritionFieldData: NutritionFieldData ->
            if (nutritionFieldData.measurement?.value != null) {
                nutritionContents.add(NutritionContents.PrimaryNutrient(nutritionFieldData, doubleBottomPadding))
            }
        }
        addNutritionFields(nutritionContents, nutritionData.minerals, doubleBottomPadding, MINERALS)
        addNutritionFields(nutritionContents, nutritionData.vitamins, doubleBottomPadding, VITAMINS)
        mvpView.setNutritionContents(nutritionContents)
    }

    private fun addNutritionFields(nutritionContents: MutableList<NutritionContents>, nutritionFieldsData: List<NutritionFieldData>?, bottomPadding: Int, nutritionType: String? = null) {
        nutritionFieldsData?.let {
            val headerIndex = nutritionContents.size
            it.forEach { nutritionFieldData ->
                nutritionFieldData.measurement?.value?.let { value ->
                    if (value > ZERO_F) {
                        nutritionContents.add(NutritionContents.PrimaryNutrient(nutritionFieldData, bottomPadding))
                    }
                }
            }
            if (nutritionType != null && headerIndex != nutritionContents.size) {
                nutritionContents.add(headerIndex, NutritionContents.Category(nutritionType))
            }
        }
    }

    private fun addPrimaryNutrientsWithSecondaryNutrients(
        nutritionContents: MutableList<NutritionContents>,
        primaryNutritionFieldData: NutritionFieldData,
        doubleBottomPadding: Int,
        singleBottomPadding: Int,
        secondaryNutritionFieldData: List<NutritionFieldData>? = null
    ) {
        val bottomPadding = if (secondaryNutritionFieldData != null && secondaryNutritionFieldData.isNotEmpty()) {
            singleBottomPadding
        } else {
            doubleBottomPadding
        }
        nutritionContents.add(NutritionContents.PrimaryNutrient(primaryNutritionFieldData, bottomPadding))
        if (secondaryNutritionFieldData != null && secondaryNutritionFieldData.isNotEmpty()) {
            secondaryNutritionFieldData.forEach {
                nutritionContents.add(NutritionContents.SecondaryNutrient(it))
            }
        }
    }

    private fun createCoreNutritionDataListFromType(nutritionData: NutritionData, vararg secondaryTypes: String): List<NutritionFieldData> =
        ArrayList<NutritionFieldData>().apply {
            secondaryTypes.forEach { type ->
                nutritionData.findCoreNutritionField(type)?.let { nutritionFieldData ->
                    if (nutritionFieldData.measurement?.value != null) {
                        add(nutritionFieldData)
                    }
                }
            }
        }
}
