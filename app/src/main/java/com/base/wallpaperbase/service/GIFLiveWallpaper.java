package com.base.wallpaperbase.service;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GIFLiveWallpaper extends WallpaperService {

    public static String gifPath = "";
    public static int gifResource = -1;

    //###################### Setting ######################
    public String LOCAL_GIF_PATH = "";
    public String LOCAL_GIF_NAME = "";
    private static String LOCAL_GIF = "testgif.gif";
    private static final String TAG = "LOGGIFLiveWallpaper";


    public static void setToWallPaper(Context context,String gifPath,int requestResult) {
        GIFLiveWallpaper.gifPath = gifPath;
        WallpaperUtil.setToWallPaper(context,
                GIFLiveWallpaper.class.getName(),1);

    }

    public static void setToWallPaper(Context context,int gifResource,int requestResult) {
        GIFLiveWallpaper.gifResource = gifResource;
        WallpaperUtil.setToWallPaper(context,
                GIFLiveWallpaper.class.getName(),requestResult);

    }

    public Engine onCreateEngine() {

        return new GIFWallpaperEngine();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class GIFWallpaperEngine extends WallpaperService.Engine {

        private final int frameDuration = 0;

        private SurfaceHolder holder;
        private Movie movie;
        private boolean visible;
        private Handler handler;
        private int mSurfaceWidth;
        private int mSurfaceHeight;
        private int mMovieWidth;
        private int mMovieHeight;
        private float scaleRatio;
        private float scaleRatioW;
        private float scaleRatioH;
        private float y;
        private float x;
        private volatile boolean mIsSurfaceCreated;

        public GIFWallpaperEngine() {
            handler = new Handler();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.holder = surfaceHolder;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawGIF);
        }



        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);

            try {
                Log.d("GIF", "imageFile exist ");
                InputStream mInputStream = null;

                File imageFile = new File(gifPath);
                final int readLimit = 16 * 1024;
                if (!gifPath.isEmpty()){
                    mInputStream  =  new BufferedInputStream(new FileInputStream(imageFile), readLimit);
                }else{
                    mInputStream = getResources().openRawResource(gifResource);
                }
                mInputStream.mark(readLimit);
                movie = Movie.decodeStream(mInputStream);
            }catch(IOException e){
                Log.e(TAG,"Error: "+e.toString());

                try {
                    Movie video = null;
                    video = Movie.decodeStream(
                            getResources().getAssets().open(LOCAL_GIF));
                    movie = video;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);

            super.onSurfaceChanged(holder, format, width, height);
            mSurfaceWidth = width;
            mSurfaceHeight = height;
            mMovieWidth = movie.width();
            mMovieHeight = movie.height();
            if((float)mSurfaceWidth/mMovieWidth > (float)mSurfaceHeight/mMovieHeight){
                scaleRatio = (float) mSurfaceWidth/mMovieWidth;
            }
            else {
                scaleRatio = (float) mSurfaceHeight/mMovieHeight;
            }
            scaleRatioW = (float) mSurfaceWidth/mMovieWidth;
            scaleRatioH = (float) mSurfaceHeight/mMovieHeight;
            this.x =        (mSurfaceWidth - (mMovieWidth*scaleRatio))/2;
            this.y =      (mSurfaceHeight - (mMovieHeight*scaleRatio))/2;
            mIsSurfaceCreated = true;
            x= x/scaleRatio;
            y=y/scaleRatio;
        }


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mIsSurfaceCreated = false;

        }

        private Runnable drawGIF = new Runnable() {
            public void run() {
                draw();
            }
        };

        private void draw() {
            if (visible) {
                Canvas canvas = holder.lockCanvas();
                canvas.save();
                // Adjust size and position so that
                // the image looks good on your screen
                canvas.scale(scaleRatio,scaleRatio);
                movie.draw(canvas,x,y);
                canvas.restore();
                holder.unlockCanvasAndPost(canvas);
                movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
                handler.removeCallbacks(drawGIF);
                handler.postDelayed(drawGIF, frameDuration);
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawGIF);
            } else {
                handler.removeCallbacks(drawGIF);
            }
        }

    }


}