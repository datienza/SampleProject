package com.farmdrop.customer.config

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.module.AppGlideModule
import com.caverock.androidsvg.SVG
import uk.co.farmdrop.library.utils.image.svg.SvgDecoder
import uk.co.farmdrop.library.utils.image.svg.SvgDrawableTranscoder
import java.io.InputStream

@com.bumptech.glide.annotation.GlideModule
class CustomerAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
                .append(InputStream::class.java, SVG::class.java, SvgDecoder())
    }
}