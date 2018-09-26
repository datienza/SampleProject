package com.farmdrop.customer.ui.home

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.home.SpotlightViewContract
import kotlinx.android.synthetic.main.view_spotlight.view.spotlightBackground
import kotlinx.android.synthetic.main.view_spotlight.view.spotlightDescText
import kotlinx.android.synthetic.main.view_spotlight.view.spotlightMarketingTag
import kotlinx.android.synthetic.main.view_spotlight.view.spotlightTitleText
import uk.co.farmdrop.library.di.view.HasViewSubcomponentBuilders
import uk.co.farmdrop.library.utils.image.ImageManager
import uk.co.farmdrop.library.utils.image.ImageManager.CROP_MODE_ENTROPY
import javax.inject.Inject

class SpotlightView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        CardView(context, attrs, defStyleAttr), SpotlightViewContract.View {

    @Inject
    lateinit var mPresenter: SpotlightViewPresenter

    private var mOnSpotlightViewClickedListener: OnSpotlightViewClickedListener? = null

    init {
        setupViewComponent()
        mPresenter.attachView(this)
    }

    private fun setupViewComponent() {
        ((context as HasViewSubcomponentBuilders).getViewComponentBuilder(SpotlightView::class.java) as SpotlightViewComponent.Builder)
                .viewModule(SpotlightViewComponent.SpotlightViewModule(this))
                .build()
                .injectMembers(this)
    }

    fun bind() {
        mPresenter.bind()
    }

    fun destroyAllDisposables() {
        mPresenter.destroyAllDisposables()
    }

    fun detachView() {
        mPresenter.detachView()
    }

    override fun setPillText(pillText: String) {
        spotlightMarketingTag.text = pillText
    }

    override fun setTitleText(titleText: String) {
        spotlightTitleText.text = titleText
    }

    override fun setDescriptionText(descriptionText: String) {
        spotlightDescText.text = descriptionText
    }

    override fun setImage(imageUrl: String) {
        ImageManager.loadImageFromPixelSize(context, imageUrl, spotlightBackground, R.drawable.ic_placeholder_image_selfie_carrot, R.dimen.spotlight_image_width, R.dimen.spotlight_view_height, null, CROP_MODE_ENTROPY)
    }

    override fun hideView() {
        visibility = View.GONE
    }

    override fun showPillText() = spotlightMarketingTag.show()

    override fun setListenerForProduct(productId: Int) {
        setOnClickListener {
            mOnSpotlightViewClickedListener?.onProductSpotlightClicked(productId)
        }
    }

    override fun setListenerForProducer(producerId: Int) {
        setOnClickListener {
            mOnSpotlightViewClickedListener?.onProducerSpotlightClicked(producerId)
        }
    }

    fun setOnSpotlightViewClickedListener(listener: OnSpotlightViewClickedListener) {
        mOnSpotlightViewClickedListener = listener
    }
}
