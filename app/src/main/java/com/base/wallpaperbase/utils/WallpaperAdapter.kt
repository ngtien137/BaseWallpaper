package com.base.wallpaperbase.utils

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.base.wallpaperbase.R
import com.base.wallpaperbase.service.GIFLiveWallpaper
import com.base.wallpaperbase.service.VideoLiveWallpaper
import java.io.*


object WallpaperAdapter {

    private val TAG = "WPPLOG"

    enum class Key {
        SET_AS_LOCK, SET_AS_HOME, SET_BOTH
    }

    fun setWallpaperByGif(context: Context, path: String,requestResult:Int=-1) {
        GIFLiveWallpaper.setToWallPaper(context, path,requestResult)
    }

    fun setWallpaperByGif(context: Context, resGif: Int,requestResult:Int=-1) {
        GIFLiveWallpaper.setToWallPaper(context, resGif,requestResult)
    }

    fun setWallpaperByVideo(context: Context, path: String,requestResult:Int=-1) {
        VideoLiveWallpaper.setToWallPaper(context, path,requestResult)
    }

    fun setWallpaperByVideo(context: Context, resVideo: Int,requestResult:Int=-1) {
        VideoLiveWallpaper.setToWallPaper(context, resVideo,requestResult)
    }

    fun setWallpaper(
        key: Key,
        path: String,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {},
        setByResourceId: Int = -1
    ) {
        val image: File? = File(path)
        val bmOptions = BitmapFactory.Options()
        if(image!=null) {
            val bitmap = if (!path.isEmpty()) BitmapFactory.decodeFile(image.absolutePath, bmOptions)
            else BitmapFactory.decodeResource(App.app.resources, setByResourceId)
            when (key) {
                Key.SET_AS_LOCK -> {
                    SetLockScreen(bitmap, onStart, onEnd).execute("")
                }
                Key.SET_AS_HOME -> {
                    SetHomeScreen(bitmap, onStart, onEnd).execute("")
                }
                Key.SET_BOTH -> {
                    SetLockAndHomeScreen(bitmap, onStart, onEnd).execute("")
                }
            }
        }else{
            Log.e(TAG,"ERROR!")
        }
    }

    private fun getFileFromResource(id: Int): File? {
        try {
            val inputStream = App.app.resources.openRawResource(id)
            val tempFile = File.createTempFile("pre", "suf")
            copyFile(inputStream,FileOutputStream(tempFile))
            return tempFile
        }catch (e:IOException){
            return null
        }finally {
            Log.e("HVV1312","ok")
        }
    }

    private fun copyFile(ips: InputStream, out: OutputStream) {
        val buffer = byteArrayOf()
        var read: Int
        read = ips.read(buffer)
        while (read != -1) {
            out.write(buffer, 0, read)
            read = ips.read(buffer)
        }
    }


    private class SetLockScreen(
        var bitmap: Bitmap,
        var onStart: () -> Unit = {},
        var onEnd: () -> Unit = {}
    ) : AsyncTask<String, Bitmap, String>() {
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
                Log.e(TAG, "Error: $e")
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

    private class SetLockAndHomeScreen(
        var bitmap: Bitmap,
        var onStart: () -> Unit = {},
        var onEnd: () -> Unit = {}
    ) : AsyncTask<String, Bitmap, String>() {
        internal var progress = 0
        private val pd: ProgressDialog? = null

        override fun doInBackground(vararg params: String): String {
            // get size
            val tempBmp = bitmap
            val wallpaperManager = WallpaperManager.getInstance(App.app)

            wallpaperManager.setWallpaperOffsetSteps(1f, 1f)

            try {
                wallpaperManager.setBitmap(tempBmp)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "Error: $e")
            }

            return "Executed"
        }

        override fun onPostExecute(result: String) {
            Toast.makeText(App.app, "Set both", Toast.LENGTH_SHORT).show()
            onEnd()
        }

        override fun onPreExecute() {
            Toast.makeText(App.app, "Is setting both", Toast.LENGTH_SHORT).show()
            onStart()
        }

        override fun onProgressUpdate(vararg values: Bitmap) {}
    }

    private class SetHomeScreen(
        var bitmap: Bitmap,
        var onStart: () -> Unit = {},
        var onEnd: () -> Unit = {}
    ) : AsyncTask<String, Bitmap, String>() {

        @TargetApi(Build.VERSION_CODES.N)
        override fun doInBackground(vararg params: String): String {
            val wallpaperManager = WallpaperManager.getInstance(App.app)

            wallpaperManager.setWallpaperOffsetSteps(1f, 1f)
            try {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "Error: $e")
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