package priv.valueyouth.rhymemusic.application;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import priv.valueyouth.rhymemusic.fragment.LocalMusicFragment;
import priv.valueyouth.rhymemusic.service.MusicService;

/**
 * ...
 * Created by Idea on 2016/5/5.
 */
public class MusicApplication extends Application implements ServiceConnection
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[MusicApplication]#";

    private MediaPlayer mediaPlayer = null; // 创建MediaPlayer对象
    private MusicService.MusicBinder musicBinder;

    private int currentMusic;  // 当前播放音乐在列表中的位置（从0开始）
    private int currentPosition; // 当前音乐的播放进度

    private TextView textAudioIndex;

    private boolean nightMode = true;

    private boolean isOnline;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, SUB + "onCreated");

        mediaPlayer = new MediaPlayer();
        bindService();

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {
        Log.d(TAG, SUB + "onServiceConnected");
        if ( service instanceof MusicService.MusicBinder)
        {
            musicBinder = (MusicService.MusicBinder) service;
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name)
    {
        // ...
    }

    public MediaPlayer getMediaPlayer()
    {
//        Log.d(TAG, SUB + "getMediaPlayer:" + mediaPlayer);
        return mediaPlayer;
    }

    public LocalBroadcastManager getManager()
    {
        Log.d(TAG, SUB + "getManager");
        return LocalBroadcastManager.getInstance(this); // 获取实例
    }

    public int getCurrentPosition()
    {
        Log.d(TAG, SUB + "getCurrentPosition:" + currentPosition);
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition)
    {
        this.currentPosition = currentPosition;
        Log.d(TAG, SUB + "setCurrentPosition:" + currentPosition);
    }

    public int getCurrentMusic()
    {
        Log.d(TAG, SUB + "getCurrentMusic:" + currentMusic);
        return currentMusic;
    }

    public void setCurrentMusic(int currentMusic)
    {
        this.currentMusic = currentMusic;
        Log.d(TAG, SUB + "setCurrentMusic" + currentMusic);
    }

    public void bindService()
    {
        Log.d(TAG, SUB + "bindService");
        Intent intent = new Intent(this, MusicService.class);
        this.bindService(intent, this, BIND_AUTO_CREATE);
    }

    public void unBindService()
    {
        Log.d(TAG, SUB + "unBindService");
        this.unbindService(this);
    }

    public MusicService.MusicBinder getMusicBinder()
    {
        Log.d(TAG, SUB + "getMusicBinder");
        return musicBinder;
    }

    public TextView getTextAudioIndex()
    {
        Log.d(TAG, SUB + "getTextAudioIndex");
        return textAudioIndex;
    }

    public void setTextAudioIndex(TextView textAudioIndex)
    {
//        Log.d(TAG, SUB + "setTextAudioIndex" + textAudioIndex);
        this.textAudioIndex = textAudioIndex;
    }

    public boolean isNightMode()
    {
        return nightMode;
    }

    public void setNightMode(boolean nightMode)
    {
        this.nightMode = nightMode;
    }

    public boolean isOnline()
    {
        return isOnline;
    }

    public void setOnline(boolean online)
    {
        isOnline = online;
    }



}


