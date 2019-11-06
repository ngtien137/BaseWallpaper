package com.base.wallpaperbase.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.base.wallpaperbase.R
import kotlinx.android.synthetic.main.dialog_wallpaper.view.*

@SuppressLint("InflateParams")
class WallpaperDialog(var context: Context) {

    var dialog: AlertDialog
    var onSetLock:()->Unit = {}
    var onSetHome:()->Unit = {}
    var onSetBoth:()->Unit = {}

    init {
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_wallpaper, null)
        view.btnSetLock.setOnClickListener {
            onSetLock()
            hide()
        }
        view.btnSetHome.setOnClickListener {
            onSetHome()
            hide()
        }
        view.btnSetBoth.setOnClickListener {
            onSetBoth()
            hide()
        }
        builder.setView(view)

        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show() = dialog.show()
    fun hide() = dialog.cancel()
}