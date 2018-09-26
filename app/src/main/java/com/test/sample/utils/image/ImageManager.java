package com.test.sample.utils.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.test.sample.utils.image.svg.SvgSoftwareLayerSetter;

import timber.log.Timber;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ImageManager {

    public static final String PARAM_WIDTH = "w";
    public static final String PARAM_HEIGHT = "h";
    public static final String PARAM_FIT = "fit";
    public static final String PARAM_CROP = "crop";
    public static final String FIT_TYPE_CROP = "crop";
    public static final String CROP_MODE_ENTROPY = "entropy";
    public static final String CROP_MODE_FACE = "face";
    public static final int NO_PLACEHOLDER = -1;


    public static void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView,
                                 @DrawableRes int placeholder, @Nullable Transformation<Bitmap> transformation) {
        try {
            RequestBuilder<Drawable> drawableTypeRequest = createBaseDrawableRequestBuilder(context, url, placeholder, true, transformation);
            drawableTypeRequest.into(imageView);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public static void loadImage(@NonNull Context context, @NonNull String url, @NonNull Target<Drawable> target,
                                 @DrawableRes int placeholder, @Nullable Transformation<Bitmap> transformation) {
        try {
            RequestBuilder<Drawable> drawableTypeRequest = createBaseDrawableRequestBuilder(context, url, placeholder, true, transformation);
            drawableTypeRequest.into(target);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    /**
     * Loads an image specifying a width and height to imgix. By default when specifying a size,
     * the image will be center cropped.
     */
    public static void loadImageFromDimenResSize(@NonNull Context context, @NonNull String url,
                                                 @NonNull ImageView imageView, @DrawableRes int placeholder,
                                                 @DimenRes int width, @DimenRes int height,
                                                 @Nullable Transformation<Bitmap> transformation) {

        loadImageFromDimenResSize(context, url, imageView, placeholder, width, height, transformation, CROP_MODE_ENTROPY);
    }

    public static void loadImageFromDimenResSize(@NonNull Context context, @NonNull String url,
                                                 @NonNull ImageView imageView, @DrawableRes int placeholder,
                                                 @DimenRes int width, @DimenRes int height,
                                                 @Nullable Transformation<Bitmap> transformation, String cropMode) {

        Resources resources = context.getResources();
        int pixelWidth = resources.getDimensionPixelSize(width);
        int pixelHeight = resources.getDimensionPixelSize(height);
        loadImageFromPixelSize(context, url, imageView, placeholder, pixelWidth, pixelHeight, transformation, cropMode);
    }

    public static void loadImageFromPixelSize(@NonNull Context context, @NonNull String url,
                                              @NonNull ImageView imageView, @DrawableRes int placeholder,
                                              int pixelWidth, int pixelHeight,
                                              @Nullable Transformation<Bitmap> transformation) {

        loadImageFromPixelSize(context, url, imageView, placeholder, pixelWidth, pixelHeight, transformation, CROP_MODE_ENTROPY);
    }

    public static void loadImageFromPixelSize(@NonNull Context context, @NonNull String url,
                                              @NonNull ImageView imageView, @DrawableRes int placeholder,
                                              int pixelWidth, int pixelHeight,
                                              @Nullable Transformation<Bitmap> transformation, String cropMode) {
        String builtUrl = null;
        if (!TextUtils.isEmpty(url)) {
            builtUrl = createUrlForImgix(url, pixelWidth, pixelHeight, FIT_TYPE_CROP, cropMode);
        }
        try {
            RequestBuilder<Drawable> drawableRequestBuilder = createBaseDrawableRequestBuilder(context, builtUrl, placeholder, false, transformation);
            drawableRequestBuilder.into(imageView);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public static void loadImageFromDimenResSize(@NonNull Context context, @NonNull String url,
                                                 @NonNull Target<Drawable> target, @DrawableRes int placeholder,
                                                 @DimenRes int width, @DimenRes int height,
                                                 @Nullable Transformation<Bitmap> transformation) {

        loadImageFromDimenResSize(context, url, target, placeholder, width, height, transformation, CROP_MODE_ENTROPY);
    }

    public static void loadImageFromDimenResSize(@NonNull Context context, @NonNull String url,
                                                 @NonNull Target<Drawable> target, @DrawableRes int placeholder,
                                                 @DimenRes int width, @DimenRes int height,
                                                 @Nullable Transformation<Bitmap> transformation, String cropMode) {

        Resources resources = context.getResources();
        int pixelWidth = resources.getDimensionPixelSize(width);
        int pixelHeight = resources.getDimensionPixelSize(height);
        loadImageFromPixelSize(context, url, target, placeholder, pixelWidth, pixelHeight, transformation, cropMode);
    }

    public static void loadImageFromPixelSize(@NonNull Context context, @NonNull String url,
                                              @NonNull Target<Drawable> target, @DrawableRes int placeholder,
                                              int pixelWidth, int pixelHeight,
                                              @Nullable Transformation<Bitmap> transformation) {

        loadImageFromPixelSize(context, url, target, placeholder, pixelWidth, pixelHeight, transformation, CROP_MODE_ENTROPY);
    }

    public static void loadImageFromPixelSize(@NonNull Context context, @NonNull String url,
                                              @NonNull Target<Drawable> target, @DrawableRes int placeholder,
                                              int pixelWidth, int pixelHeight,
                                              @Nullable Transformation<Bitmap> transformation, String cropMode) {
        String builtUrl = null;
        if (!TextUtils.isEmpty(url)) {
            builtUrl = createUrlForImgix(url, pixelWidth, pixelHeight, FIT_TYPE_CROP, cropMode);
        }

        try {
            RequestBuilder<Drawable> drawableRequestBuilder = createBaseDrawableRequestBuilder(context, builtUrl, placeholder, false, transformation);
            drawableRequestBuilder.into(target);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private static RequestBuilder<Drawable> createBaseDrawableRequestBuilder(Context context, String url, @DrawableRes int placeholder,
                                                                             boolean centerCrop, @Nullable Transformation<Bitmap> transformation) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholder)
                .error(placeholder);
        if (centerCrop) {
            requestOptions = requestOptions.centerCrop();
        }
        if (transformation != null) {
            requestOptions = requestOptions.transform(transformation);
        }

        return Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .apply(requestOptions);
    }

    private static String createUrlForImgix(String url, int width, int height, String fit, String crop) {
        Uri builtUri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(PARAM_WIDTH, String.valueOf(width))
                .appendQueryParameter(PARAM_HEIGHT, String.valueOf(height))
                .appendQueryParameter(PARAM_FIT, fit)
                .appendQueryParameter(PARAM_CROP, crop)
                .build();
        return builtUri.toString();
    }

    public static void loadVectorFromUrl(@NonNull Context context, @NonNull String url,
                                         @NonNull ImageView imageView, @DrawableRes int placeholder, @Nullable SvgSoftwareLayerSetter svgSoftwareLayerSetter) {
        RequestOptions requestOptions = new RequestOptions();

        if (placeholder != NO_PLACEHOLDER) {
            requestOptions = requestOptions.placeholder(placeholder);
        }

        RequestBuilder<PictureDrawable> requestBuilder = Glide.with(context)
                .as(PictureDrawable.class)
                .apply(requestOptions);

        if (svgSoftwareLayerSetter != null) {
            requestBuilder = requestBuilder.listener(svgSoftwareLayerSetter);
        } else {
            requestBuilder = requestBuilder.listener(new SvgSoftwareLayerSetter());
        }
        requestBuilder.load(url).into(imageView);
    }
}
