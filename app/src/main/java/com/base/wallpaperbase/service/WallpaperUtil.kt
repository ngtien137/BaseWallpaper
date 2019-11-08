package com.base.wallpaperbase.service

import android.app.WallpaperInfo
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity

import java.io.IOException

/**
 * Created by MartinRGB on 2017/11/29.
 */

object WallpaperUtil {

    val MIHOME_WALLPAPER_ACTION = "com.miui.home.WALLPAPER_PREVIEW"
    internal val EXTRA_LIVE_WALLPAPER_INTENT = "android.live_wallpaper.intent"
    internal val EXTRA_LIVE_WALLPAPER_SETTINGS = "android.live_wallpaper.settings"
    internal val EXTRA_LIVE_WALLPAPER_PACKAGE = "android.live_wallpaper.package"


    class LiveWallpaperInfo {
        var thumbnail: Drawable? = null
        var info: WallpaperInfo? = null
        var intent: Intent? = null
    }

    @JvmStatic
    fun setToWallPaper(context: Context, className: String,requestResult:Int=-1) {

        try {
            WallpaperManager.getInstance(context).clear()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (DeviceDetectUtil.isMiUi()) {
            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(context, className)
            )
            intent.putExtra("SET_LOCKSCREEN_WALLPAPER", true)
            if (context is AppCompatActivity&&requestResult!=-1){
                context.startActivityForResult(intent,requestResult)
            }else
                context.startActivity(intent)
        } else {

            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(context, className)
            )
            intent.putExtra("SET_LOCKSCREEN_WALLPAPER", true)
            if (context is AppCompatActivity&&requestResult!=-1){
                context.startActivityForResult(intent,requestResult)
            }else
                context.startActivity(intent)
        }


    }


    fun showLiveWallpaperPreview(info: WallpaperInfo?, intent: Intent, context: Context) {
        if (info == null) return

        val preview = Intent(MIHOME_WALLPAPER_ACTION)
        //preview.setComponent(new ComponentName(context, VideoLiveWallpaper.class));
        preview.putExtra(EXTRA_LIVE_WALLPAPER_INTENT, intent)
        preview.putExtra(EXTRA_LIVE_WALLPAPER_SETTINGS, info.settingsActivity)
        preview.putExtra(EXTRA_LIVE_WALLPAPER_PACKAGE, info.packageName)
        context.startActivity(preview)
    }
}
