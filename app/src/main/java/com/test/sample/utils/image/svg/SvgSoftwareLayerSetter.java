package com.test.sample.utils.image.svg;

import android.graphics.drawable.PictureDrawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;

/**
 * Listener which updates the {@link ImageView} to be software rendered,
 * because {@link com.caverock.androidsvg.SVG SVG}/{@link android.graphics.Picture Picture}
 * can't render on a hardware backed {@link android.graphics.Canvas Canvas}.
 */
public class SvgSoftwareLayerSetter implements RequestListener<PictureDrawable> {

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<PictureDrawable> target, boolean isFirstResource) {
        setTargetLayerType(target, ImageView.LAYER_TYPE_NONE);
        return false;
    }

    @Override
    public boolean onResourceReady(PictureDrawable resource, Object model, Target<PictureDrawable> target, DataSource dataSource, boolean isFirstResource) {
        setTargetLayerType(target, ImageView.LAYER_TYPE_SOFTWARE);
        return false;
    }

    protected void setTargetLayerType(Target<PictureDrawable> target, int layerType) {
        ImageView view = ((ImageViewTarget<?>) target).getView();
        view.setLayerType(layerType, null);
    }
}
