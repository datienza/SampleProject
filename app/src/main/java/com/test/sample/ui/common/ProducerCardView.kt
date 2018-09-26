package com.farmdrop.customer.ui.common

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.common.ProducerCardViewContract
import com.farmdrop.customer.data.model.ProducerData
import kotlinx.android.synthetic.main.producer_card_view.view.producerDescription
import kotlinx.android.synthetic.main.producer_card_view.view.producerImage
import kotlinx.android.synthetic.main.producer_card_view.view.producerOfTheWeekMarketingTag
import kotlinx.android.synthetic.main.producer_card_view.view.producerTitle
import uk.co.farmdrop.library.di.view.HasViewSubcomponentBuilders
import uk.co.farmdrop.library.utils.image.ImageManager
import javax.inject.Inject

class ProducerCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        CardView(context, attrs, defStyleAttr), ProducerCardViewContract.View {

    @Inject
    lateinit var mPresenter: ProducerCardViewPresenter

    init {
        setupViewComponent()
        mPresenter.attachView(this)
    }

    private fun setupViewComponent() {
        ((context as HasViewSubcomponentBuilders).getViewComponentBuilder(ProducerCardView::class.java) as ProducerCardViewComponent.Builder)
                .viewModule(ProducerCardViewComponent.ProducerCardViewModule(this))
                .build()
                .injectMembers(this)
    }

    fun bind(producerData: ProducerData) {
        mPresenter.bind(producerData)
    }

    override fun loadImage(imagePath: String) {
        ImageManager.loadImageFromDimenResSize(context, imagePath, producerImage, R.drawable.ic_placeholder_image_selfie_carrot,
                R.dimen.producer_image_width, R.dimen.producer_image_height, null, ImageManager.CROP_MODE_FACE)
    }

    override fun loadPlaceHolderImage(placeHolderRes: Int) {
        producerImage.setImageResource(R.drawable.ic_placeholder_image_selfie_carrot)
    }

    override fun setName(name: String) {
        producerTitle.text = name
    }

    override fun setDescription(description: String) {
        producerDescription.text = description
    }

    override fun showProducerOfTheWeekTag() {
        producerOfTheWeekMarketingTag.visibility = View.VISIBLE
    }

    override fun hideProducerOfTheWeekTag() {
        producerOfTheWeekMarketingTag.visibility = View.GONE
    }
}
