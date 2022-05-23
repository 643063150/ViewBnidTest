package com.android.zpp.viewbnidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.android.zpp.viewbnidtest.databinding.ActivityMainBinding;
import com.android.zpp.viewbnidtest.utils.MyMediaPlayer;
import com.android.zpp.viewbnidtest.utils.MyMediaPlayerListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding activityMainBinding;
    ObjectAnimator objectAnimator;
    MediaPlayer mediaPlayer;
    MyMediaPlayer myMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.test.setText("123456");
        activityMainBinding.test.setOnClickListener(this);
        setObjectAnimator();
//        setMediaPlayer();
        myMediaPlayer=MyMediaPlayer.newCreate(this).registerMediaPlayer("https://sntalk.morewiscloud.com/gateway/file/zpp.mp3").setMyMediaPlayerListener(new MyMediaPlayerListener() {
            @Override
            public void onPlayStatus(int status) {
                Log.e("状态：", status + "");
            }

            @Override
            public void onInPress(int duration, int press) {
                Log.e("进度：", duration + "/" + press);
            }


            @Override
            public void onBufferingUpdate(int percent) {
                Log.e("缓冲进度：", percent + "%");
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onFinish() {
                Log.e("当前曲目播放完成：", "onFinish");
            }
        });
        mediaPlayer=myMediaPlayer.getMediaPlayer();
        mediaPlayer.setOnPreparedListener(mp -> {
            mp.start();
            objectAnimator.start();
        });
    }

    /**
     * MediaPlayer 类使用
     */
    private void setMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource("https://sntalk.morewiscloud.com/gateway/file/zpp.mp3");
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "加载音源失败!", Toast.LENGTH_SHORT).show();
        }
        mediaPlayer.setOnPreparedListener(mp -> {
            mp.start();
            objectAnimator.start();
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.e("缓冲进度:", percent + "%");
            }
        });
    }

    /**
     * 设置动画
     */
    private void setObjectAnimator() {
        objectAnimator = ObjectAnimator.ofFloat(activityMainBinding.imageView, "rotation", 0, 359);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        activityMainBinding.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.test) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ListTestActivity.class);
            startActivity(intent);
        }
        if (id == R.id.button) {
            if (objectAnimator.isPaused()) {
                objectAnimator.resume();
                mediaPlayer.start();
                activityMainBinding.button.setBackgroundResource(R.mipmap.pause);
            } else {
                objectAnimator.pause();
                mediaPlayer.pause();
                activityMainBinding.button.setBackgroundResource(R.mipmap.play);
            }

        }
    }
}