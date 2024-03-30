package com.kongjak.koreatechboard.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html.ImageGetter
import android.widget.TextView
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import java.lang.ref.WeakReference

class HtmlImageGetter(
    textView: TextView,
    densityAware: Boolean = false,
    private val imagesHandler: HtmlImagesHandler? = null
) : ImageGetter {
    private val container: WeakReference<TextView> = WeakReference(textView)
    private var density = 1.0f

    init {
        if (densityAware) {
            container.get()?.let {
                density = it.resources.displayMetrics.density
            }
        }
    }

    override fun getDrawable(source: String?): Drawable {
        imagesHandler?.addImage(source)

        val drawable = BitmapDrawablePlaceholder()

        // Load Image to the Drawable
        container.get()?.apply {
            post {
                val imageLoader = ImageLoader.Builder(context)
                    .memoryCache {
                        MemoryCache.Builder(context)
                            .maxSizePercent(0.25)
                            .build()
                    }
                    .diskCache {
                        DiskCache.Builder()
                            .directory(context.cacheDir.resolve("image_cache"))
                            .maxSizePercent(0.02)
                            .build()
                    }
                    .build()
                val request = ImageRequest.Builder(context)
                    .data(source)
                    .crossfade(true)
                    .target {
                        drawable.drawable = it
                    }
                    .build()

                imageLoader.enqueue(request)
            }
        }

        return drawable
    }

    private inner class BitmapDrawablePlaceholder :
        BitmapDrawable(
            container.get()?.resources,
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        ) {
        var drawable: Drawable? = null
            set(value) {
                field = value
                value?.let { drawable ->
                    val drawableWidth = (drawable.intrinsicWidth * density).toInt()
                    val drawableHeight = (drawable.intrinsicHeight * density).toInt()
                    val maxWidth = container.get()!!.measuredWidth
                    val newHeight = maxWidth * drawableHeight / drawableWidth
                    drawable.setBounds(0, 0, maxWidth, newHeight)
                    setBounds(0, 0, maxWidth, newHeight)

                    container.get()?.text = container.get()?.text
                }
            }

        override fun draw(canvas: Canvas) {
            drawable?.draw(canvas)
        }
    }

    interface HtmlImagesHandler {
        fun addImage(uri: String?)
    }
}
