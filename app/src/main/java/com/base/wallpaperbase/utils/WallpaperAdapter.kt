package com.base.wallpaperbase.utils

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.base.wallpaperbase.service.GIFLiveWallpaper
import com.base.wallpaperbase.service.VideoLiveWallpaper
import java.io.File
import java.util.*

object WallpaperAdapter {

    private val TAG = "WPPLOG"

    enum class Key{
        SET_AS_LOCK, SET_AS_HOME, SET_BOTH
    }

    fun setWallpaperByGif(context:Context, path:String){
        GIFLiveWallpaper.setToWallPaper(context,path)
    }

    fun setWallpaperByVideo(context:Context, path:String){
        VideoLiveWallpaper.setToWallPaper(context,path)
    }

    fun setWallpaper(key:Key,path:String,onStart:()->Unit={},onEnd:()->Unit={}){
        val image = File(path)
        val bmOptions = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeFile(image.absolutePath, bmOptions)
        when(key){
            Key.SET_AS_LOCK->{
                SetLockScreen(bitmap,onStart,onEnd).execute("")
            }
            Key.SET_AS_HOME->{
                SetHomeScreen(bitmap,onStart,onEnd).execute("")
            }
            Key.SET_BOTH->{
                SetLockAndHomeScreen(bitmap,onStart,onEnd).execute("")
            }
        }
    }


    private class SetLockScreen(var bitmap: Bitmap,var onStart:()->Unit={},var onEnd:()->Unit={}) : AsyncTask<String, Bitmap, String>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        override fun doInBackground(vararg params: String): String {


            // get size
            val metrics = App.app.resources.displayMetrics
            var width = metrics.widthPixels
            var height = metrics.heightPixels
            val area = width * height / 1000
            val bitmap1 = bitmap
            width *= 2
            val scale = width / bitmap1.width.toFloat()
            height = (scale * bitmap1.height).toInt()

            val bitmap = Bitmap.createScaledBitmap(bitmap1, width, height, true)

            val wallpaperManager = WallpaperManager.getInstance(App.app)

            wallpaperManager.setWallpaperOffsetSteps(1f, 1f)
            wallpaperManager.suggestDesiredDimensions(width, height)

            try {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG,"Error: $e")
            }

            return "Executed"
        }

        override fun onPostExecute(result: String) {
            Toast.makeText(App.app, "Set lock!", Toast.LENGTH_SHORT).show();
            onEnd()
        }

        override fun onPreExecute() {
            Toast.makeText(App.app, "Is setting as lock", Toast.LENGTH_SHORT).show()
            onStart()
        }

        override fun onProgressUpdate(vararg values: Bitmap) {


        }
    }

    private class SetLockAndHomeScreen(var bitmap: Bitmap,var onStart:()->Unit={},var onEnd:()->Unit={}) : AsyncTask<String, Bitmap, String>() {
        internal var progress = 0
        private val pd: ProgressDialog? = null

        override fun doInBackground(vararg params: String): String {
            // get size
            var tempBmp = bitmap
            val wallpaperManager = WallpaperManager.getInstance(App.app)

            wallpaperManager.setWallpaperOffsetSteps(1f, 1f)

            try {
                wallpaperManager.setBitmap(tempBmp)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG,"Error: $e")
            }

            return "Executed"
        }

        override fun onPostExecute(result: String) {
            Toast.makeText(App.app, "Set both", Toast.LENGTH_SHORT).show();
            onEnd()
        }

        override fun onPreExecute() {
            Toast.makeText(App.app, "Is setting both", Toast.LENGTH_SHORT).show();
            onStart()
        }

        override fun onProgressUpdate(vararg values: Bitmap) {}
    }

    private class SetHomeScreen(var bitmap:Bitmap,var onStart:()->Unit={},var onEnd:()->Unit={}) : AsyncTask<String, Bitmap, String>() {

        @TargetApi(Build.VERSION_CODES.N)
        override fun doInBackground(vararg params: String): String {
            val wallpaperManager = WallpaperManager.getInstance(App.app)

            wallpaperManager.setWallpaperOffsetSteps(1f, 1f)
            try {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG,"Error: $e")
            }

            return "Executed"
        }

        override fun onPostExecute(result: String) {
            Toast.makeText(App.app, "Set home!", Toast.LENGTH_SHORT).show();
            onEnd()
        }

        override fun onPreExecute() {
            Toast.makeText(App.app, "Is setting as home", Toast.LENGTH_SHORT).show();
            onStart()
        }
        override fun onProgressUpdate(vararg values: Bitmap) {}
    }
}