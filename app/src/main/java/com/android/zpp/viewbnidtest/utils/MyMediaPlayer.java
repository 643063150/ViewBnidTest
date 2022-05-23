package com.android.zpp.viewbnidtest.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.text.TextUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: ViewBnidTest
 * @Package: com.android.zpp.viewbnidtest.utils
 * @ClassName: MyMediaPlayer
 * @Description:
 * @Author: zpp
 * @CreateDate: 2022/5/23 14:37
 * @UpdateUser:
 * @UpdateDate: 2022/5/23 14:37
 * @UpdateRemark:
 */
public class MyMediaPlayer {
    private static MyMediaPlayer myMediaPlayer;
    public static Context context;
    private MediaPlayer mediaPlayer;
    private MyMediaPlayerListener myMediaPlayerListener;
    ScheduledExecutorService scheduledExecutorService ;

    public static MyMediaPlayer newCreate(Context context) {
        myMediaPlayer = new MyMediaPlayer();
        MyMediaPlayer.context = context;
        return myMediaPlayer;
    }

    /**
     * 初始化播放器
     *
     * @param DataSource 优先初始化播放链接
     * @return
     */
    public MyMediaPlayer registerMediaPlayer(String DataSource) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());
        mediaPlayer.reset();
        try {
            if (!TextUtils.isEmpty(DataSource)) {
                mediaPlayer.setDataSource(DataSource);
                mediaPlayer.prepareAsync();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (myMediaPlayerListener != null) {
                myMediaPlayerListener.onError(e);
            }
        }
        setScheduledExecutorService();
        return this;
    }

    /**
     * 播放器的各种监听
     */
    private void setMediaPlayerListener() {
        mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> {
            myMediaPlayerListener.onBufferingUpdate(percent);
        });
        mediaPlayer.setOnCompletionListener(mp -> myMediaPlayerListener.onFinish());
    }

    /**
     * 获取当前播放器
     *
     * @return
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private void setScheduledExecutorService() {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.
                Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (mediaPlayer.isPlaying()) {
                myMediaPlayerListener.onPlayStatus(0);
            } else {
                myMediaPlayerListener.onPlayStatus(1);
            }
            myMediaPlayerListener.onInPress(mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition());
        }, 0,1, TimeUnit.SECONDS);
    }

    public MyMediaPlayer setMyMediaPlayerListener(MyMediaPlayerListener myMediaPlayerListener) {
        setMediaPlayerListener();
        this.myMediaPlayerListener = myMediaPlayerListener;
        return this;
    }
}
