package com.farmdrop.customer.contracts.products

import com.farmdrop.customer.data.model.MediaData
import com.farmdrop.customer.data.model.ProducerData
import com.farmdrop.customer.data.model.TagData

/**
 * Define contract methods between ProductDetailsActivity & ProductDetailsActivityPresenter
 */
interface ProductDetailsActivityContract {

    interface View : BaseProductContract.View {
        fun displayProductIntroParagraph(introParagraph: String)
        fun displayProductDescription(description: String)
        fun showEmptyDetailsText()
        fun hideDetailsText()
        fun displayOrigin(origin: String)
        fun displayServes(serves: String)
        fun displayShelfLife(shelfLifeDays: String)
        fun showPropertiesLayout()
        fun hidePropertiesLayout()
        fun addProperty(propertyData: TagData)
        fun displayAwards(awards: List<MediaData>)
        fun displayIngredients()
        fun displayNutritionalInformation()
        fun displayCookingInstructions()
        fun displayStorageInformation()
        fun displayProducerDetailsView(producerData: ProducerData)
        fun hideMeetProducerTopLine()
        fun showMeetProducerTopLine()
        fun displayProducerProductsCarousel(producerId: Int)
        fun formatVariantDisplayNameWithUnitWithPricePerUnit(displayNameWithUnit: String, pricePerUnit: String): String
        fun displayDiscountedPrice(price: Double, oldPrice: Double)
    }
}
