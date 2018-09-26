package com.farmdrop.customer.ui.products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.FullScreenPhotoActivityContract
import com.farmdrop.customer.presenters.products.FullScreenPhotoActivityPresenter
import com.farmdrop.customer.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_full_screen_photo.photoView
import kotlinx.android.synthetic.main.activity_full_screen_photo.toolbar
import uk.co.farmdrop.library.di.activity.HasActivitySubcomponentBuilders
import uk.co.farmdrop.library.utils.image.ImageManager
import javax.inject.Inject

class FullScreenPhotoActivity : BaseActivity(), FullScreenPhotoActivityContract.View {

    companion object {
        private const val KEY_PHOTO_URL = "photo_url"

        fun getStartingIntent(context: Context, photoUrl: String): Intent {
            val intent = Intent(context, FullScreenPhotoActivity::class.java)
            intent.putExtra(KEY_PHOTO_URL, photoUrl)
            return intent
        }
    }

    @Inject
    lateinit var mPresenter: FullScreenPhotoActivityPresenter

    private var mPhotoUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_photo)
        initToolbar()
        mPresenter.attachView(this)
        mPresenter.displayPhoto(mPhotoUrl)
    }

    override fun retrieveIntentBundle(bundle: Bundle) {
        if (bundle.containsKey(KEY_PHOTO_URL)) {
            mPhotoUrl = bundle.getString(KEY_PHOTO_URL)
        }
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders) {
        (hasActivitySubComponentBuilders
                .getActivityComponentBuilder(FullScreenPhotoActivity::class.java) as FullScreenPhotoActivityComponent.Builder)
                .activityModule(FullScreenPhotoActivityComponent.FullScreenPhotoActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun createFragment() {
    }

    private fun initToolbar() {
        setToolbar(toolbar)
        setHomeAsUpEnabled(R.drawable.ic_arrow_back_white)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun displayPhoto(photoUrl: String) {
        ImageManager.loadImage(this, photoUrl, photoView, R.drawable.ic_placeholder_image_selfie_carrot, null)
    }

    override fun displayPlaceHolder() {
        photoView.setImageResource(R.drawable.ic_placeholder_image_selfie_carrot)
    }
}