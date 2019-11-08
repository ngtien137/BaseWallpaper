package com.base.wallpaperbase.service;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.base.wallpaperbase.R;


public class VideoLiveWallpaper extends WallpaperService {

    public static String videoPath = "";
    public static int videoResource = -1;
    //###################### Setting ######################
    public static final String TAG = "NVTVideoLiveWallpaper";
    public String LOCAL_VIDEO_PATH = "";
    public String LOCAL_VIDEO_NAME = "";
    public String LOCAL_VIDEO = "testvideo.mp4";

    public Engine onCreateEngine() {
        return new VideoWallpaperEngine();
    }


    public static void setToWallPaper(Context context,String videoPath,int requestResult) {
        VideoLiveWallpaper.videoPath = videoPath;
        WallpaperUtil.setToWallPaper(context,
                VideoLiveWallpaper.class.getName(),requestResult);
    }

    public static void setToWallPaper(Context context,int videoResource, int requestResult) {
        VideoLiveWallpaper.videoResource = videoResource;
        WallpaperUtil.setToWallPaper(context,
                VideoLiveWallpaper.class.getName(),requestResult);
    }

    class VideoWallpaperEngine extends WallpaperService.Engine {

        private MediaPlayer mMediaPlayer;

        private int mSurfaceWidth;
        private int mSurfaceHeight;
        private int mMovieWidth;
        private int mMovieHeight;
        private float scaleRatio;
        private Surface mSurface;
        private int SETSIZE = 1;


        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                mMediaPlayer.start();
            } else {
                mMediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mMediaPlayer = new MediaPlayer();
            try {
                if (!videoPath.isEmpty()) {
                    mMediaPlayer.setDataSource(videoPath);
                }else if (videoResource!=-1) {
                    mMediaPlayer = MediaPlayer.create(VideoLiveWallpaper.this, videoResource);
                }else{
                    throw new Exception();
                }
                mMediaPlayer.setSurface(holder.getSurface());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.setVolume(0, 0);
                mMediaPlayer.prepare();
                mMediaPlayer.start();

            } catch (Exception e) {
                Log.e(TAG,"Error Create Surface: "+e.toString());
                e.printStackTrace();
            }

            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // TODO Auto-generated method stub
                    mMediaPlayer.release();
                    return false;
                }
            });


            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    try {
                        mp.start();
                        uiHandler.sendEmptyMessageDelayed(SETSIZE, 1000);

                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e("start mediaplayer", e.toString());
                    }

                }
            });

        }


        private Handler uiHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if(msg.what == SETSIZE){
                    mMovieHeight = mMediaPlayer.getVideoHeight();
                    mMovieWidth = mMediaPlayer.getVideoWidth();
                }
            };
        };

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);

            mSurfaceWidth = width;
            mSurfaceHeight = height;
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);

        }
    }

}