package com.farmdrop.customer.ui.products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.ProductMoreInfoActivityContract
import com.farmdrop.customer.data.model.nutrition.NutritionContents
import com.farmdrop.customer.presenters.products.ProductMoreInfoActivityPresenter
import com.farmdrop.customer.ui.common.BaseActivity
import com.farmdrop.customer.utils.constants.Constants
import com.farmdrop.customer.utils.constants.ProductMoreInfoTypes
import kotlinx.android.synthetic.main.activity_product_more_info.nutritionRecyclerView
import kotlinx.android.synthetic.main.activity_product_more_info.productMoreInfoText
import kotlinx.android.synthetic.main.activity_product_more_info.productShelflifeText
import kotlinx.android.synthetic.main.activity_product_more_info.productShelflifeTitleText
import kotlinx.android.synthetic.main.activity_product_more_info.toolbar
import uk.co.farmdrop.library.di.activity.HasActivitySubcomponentBuilders
import javax.inject.Inject

class ProductMoreInfoActivity : BaseActivity(), ProductMoreInfoActivityContract.View {

    @Inject
    lateinit var presenter: ProductMoreInfoActivityPresenter

    private var productGraphQLId: String? = null

    @ProductMoreInfoTypes
    private var productMoreInfoType: Int = 0

    private var productMoreInfoTitle: String? = null

    private var nutritionAdapter: NutritionAdapter? = null

    companion object {
        fun getStartingIntent(
            context: Context,
            productGraphQLID: String,
            @ProductMoreInfoTypes productMoreInfoType: Int,
            productMoreInfoTitle: String
        ) =
            Intent(context, ProductMoreInfoActivity::class.java).apply {
                putExtra(Constants.PRODUCT_ID, productGraphQLID)
                putExtra(Constants.PRODUCT_EXTRA_INFO_TYPE, productMoreInfoType)
                putExtra(Constants.PRODUCT_EXTRA_INFO_TITLE, productMoreInfoTitle)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_more_info)
        presenter.attachView(this)
        initToolbar()
        presenter.loadData(productMoreInfoType)
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders) {
        (hasActivitySubComponentBuilders
            .getActivityComponentBuilder(ProductMoreInfoActivity::class.java) as ProductMoreInfoActivityComponent.Builder)
            .activityModule(ProductMoreInfoActivityComponent.ProductMoreInfoActivityModule(this, productGraphQLId))
            .build()
            .injectMembers(this)
    }

    override fun createFragment() {
        // nothing to do
    }

    private fun initToolbar() {
        setToolbar(toolbar)
        setHomeAsUpEnabled(R.drawable.ic_arrow_back_green)
        supportActionBar?.title = productMoreInfoTitle
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onPause() {
        super.onPause()
        presenter.destroyAllDisposables()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    override fun retrieveIntentBundle(bundle: Bundle) {
        productGraphQLId = bundle.getString(Constants.PRODUCT_ID)
        productMoreInfoType = bundle.getInt(Constants.PRODUCT_EXTRA_INFO_TYPE)
        productMoreInfoTitle = bundle.getString(Constants.PRODUCT_EXTRA_INFO_TITLE)
    }

    override fun displayMoreInfoText(moreInfoText: String?) {
        productMoreInfoText.visibility = View.VISIBLE
        productMoreInfoText.text = moreInfoText
    }

    override fun displayShelfLife(shelfLifeDays: String?) {
        productShelflifeTitleText.visibility = View.VISIBLE
        productShelflifeText.visibility = View.VISIBLE
        productShelflifeText.text = getString(R.string.shelf_life_minimum, shelfLifeDays)
    }

    override fun getSingleNutrientLayoutBottomPadding(): Int = R.dimen.grid_spacing_half

    override fun getDoubleNutrientLayoutBottomPadding(): Int = R.dimen.grid_spacing_double

    override fun initNutritionRecyclerView() {
        nutritionAdapter = NutritionAdapter(this)
        nutritionRecyclerView.visibility = View.VISIBLE
        nutritionRecyclerView.setHasFixedSize(true)
        nutritionRecyclerView.adapter = nutritionAdapter
    }

    override fun setNutritionContents(nutritionContent: List<NutritionContents>) {
        nutritionAdapter?.setData(nutritionContent)
        nutritionAdapter?.notifyDataSetChanged()
    }
}
