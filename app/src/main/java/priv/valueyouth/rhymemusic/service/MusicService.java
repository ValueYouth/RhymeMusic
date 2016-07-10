package priv.valueyouth.rhymemusic.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.receiver.AlarmReceiver;
import priv.valueyouth.rhymemusic.util.Audio;
import priv.valueyouth.rhymemusic.util.AudioUtil;

/**
 * 播放逻辑
 * Created by Idea on 2016/5/15.
 */
public class MusicService extends Service
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
                   MediaPlayer.OnCompletionListener
{
    private MusicApplication application;

    /*播放模式*/
    private int currentMode = 0; // 默认播放模式：顺序播放
    private static final int MODE_SEQUENCE = 0; // 顺序播放
    private static final int MODE_RANDOM = 1; // 随机播放
    private static final int MODE_SINGLE_CYCLE = 2; // 单曲循环
    private static final int MODE_LIST_CYCLE = 3; // 列表循环
    private static final String[] PLAY_MODE = {
        "顺序播放","随机播放","单曲循环", "列表循环"
    };

    private static final int PREVIOUS_TRACK = 1;
    private static final int NEXT_TRACK = 2;

    /*定义整型常量，用于更新UI*/
    private static final int UPDATE_CURRENT_MUSIC = 1;
    private static final int UPDATE_DURATION = 2;
    private static final int UPDATE_PROGRESS = 3;
    private static final int UPDATE_PLAY_STATUS = 4;
    private static final int UPDATE_PLAY_MODE = 5;

    /*发出的广播类型*/
    public static final String ACTION_UPDATE_CURRENT_MUSIC =
            "com.valueyouth.UPDATE_CURRENT_MUSIC";
    public static final String ACTION_UPDATE_DURATION =
            "com.valueyouth.UPDATE_DURATION";
    public static final String ACTION_UPDATE_PROGRESS =
            "com.valueyouth.UPDATE_PROGRESS";
    public static final String ACTION_UPDATE_PLAY_STATUS =
            "com.valueyouth.UPDATE_PLAY_STATUS";
    public static final String ACTION_UPDATE_PLAY_MODE =
            "com.valueyouth.UPDATE_PLAY_MODE";

    /*日志需要*/
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[MusicService]#";

    /*其他变量*/
    private List<Audio> audioList; // 音乐列表
    private LocalBroadcastManager manager; // 使用本地广播
    private MediaPlayer mediaPlayer = null;
    private MusicBinder binder = new MusicBinder();
    private MusicHandler musicHandler = new MusicHandler();


    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, SUB + "onCreate");

        application = (MusicApplication) getApplication();
        initMediaPlayer(); // 初始化播放器

        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, SUB + "onDestroy");

        if ( mediaPlayer != null )
        {
            mediaPlayer.release();
            mediaPlayer = null;
        }
//        else
//        {
//            Log.d(TAG, SUB + "mediaPlayer.release()");
//            mediaPlayer.release();
//        }
    }


    /**
     * 返回MusicBinder对象的实例
     * @param intent intent对象
     * @return IBinder对象
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, SUB + "onBind");
        return binder;
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        Log.d(TAG, SUB + "onPrepared");

        mp.start();

        if ( application.isOnline() )
        {
            Toast.makeText(getBaseContext(), "音乐开始播放了", Toast.LENGTH_SHORT).show();
        }

        mp.seekTo(application.getCurrentPosition());

        sendMessage(UPDATE_PLAY_STATUS);
        sendMessage(UPDATE_DURATION);
        sendMessage(UPDATE_CURRENT_MUSIC);
        sendMessage(UPDATE_PROGRESS);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        Log.d(TAG, SUB + "onError");
        mp.reset();  // 重置播放器
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        Log.d(TAG, SUB + "onCompletion");

        if ( application.isOnline() )
        {
            Toast.makeText(getBaseContext(), "因时间原因，该功能暂未实现。",
                    Toast.LENGTH_LONG).show();
            mp.stop();
        }
        else
        {
            binder.nextTrack();
        }

    }

    /**
     * 初始化播放器
     */
    public void initMediaPlayer()
    {
        mediaPlayer = application.getMediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        /*获取音乐相关的信息*/
        audioList = AudioUtil.getAudioList(MusicService.this);
        /*获取本地广播实例*/
        manager = application.getManager();
    }

    /**
     * 更新界面，显示当前播放音乐。
     */
    private void updateCurrentMusic()
    {
        Log.d(TAG, SUB + "[Broadcast] updateCurrentMusic");
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_CURRENT_MUSIC);

        manager.sendBroadcast(intent); // 发送本地广播
    }

    /**
     * 更新音乐播放的进度：流逝时间的显示。
     */
    private void updateProgress()
    {
//        Log.d(TAG, SUB + "[Broadcast] updateCurrentProgress");

        if ( mediaPlayer != null && mediaPlayer.isPlaying() )
        {
            Intent intent = new Intent();
            intent.setAction(ACTION_UPDATE_PROGRESS);

            manager.sendBroadcast(intent);
            musicHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 1000);
        }
    }

    /**
     * 更新歌曲的时长
     */
    private void updateDuration()
    {
        Log.d(TAG, SUB + "[Broadcast] updateCurrentDuration");

        if ( mediaPlayer != null )
        {
            Log.d(TAG, SUB + "if...");
            Intent intent = new Intent();
            intent.setAction(ACTION_UPDATE_DURATION);

            manager.sendBroadcast(intent);
        }
    }

    private void updatePlayStatus()
    {
        Log.d(TAG, SUB + "[Broadcast] updatePlayStatus");

        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_PLAY_STATUS);

        manager.sendBroadcast(intent);
    }

    private void updatePlayMode()
    {
        Log.d(TAG, SUB + "[Broadcast] updatePlayMode");

        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_PLAY_MODE);

        manager.sendBroadcast(intent);
    }

    /**
     * 发送自定义消息
     * @param what 消息的类型
     */
    public void sendMessage(int what)
    {
//        Log.d(TAG, SUB + "sendMessage" + what);
        Message message = new Message();
        message.what = what;
        musicHandler.sendMessage(message);
    }

    /**
     * 创建内部类MusicBinder，来管理播放的相关事务。
     * 包括：开始，暂停和停止播放；上一曲和下一曲；播放模式。
     */
    public class MusicBinder extends Binder
    {
        /**
         * 开始播放音乐
         * @param currentMusic 当前音乐在List中的位置
         * @param currentPosition 当前音乐的播放位置（相对于该音乐时长）
         */
        public void startPlay(int currentMusic, int currentPosition)
        {
            application.setOnline(false);
            application.setCurrentPosition(currentPosition);
            application.setCurrentMusic(currentMusic);
            mediaPlayer.reset();  // 重置音乐播放器，使其处于空闲状态。

            try
            {
                /*设置音频路径资源*/
                mediaPlayer.setDataSource(audioList.get(currentMusic).getData());
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync(); // 准备播放
                Log.d(TAG, SUB + "startPlay：" + audioList.get(currentMusic).getTitle());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void startPlay(String url)
        {
            application.setOnline(true);
            mediaPlayer.reset();  // 重置音乐播放器，使其处于空闲状态。

            try
            {
                /*设置音频路径资源*/
                mediaPlayer.setDataSource(url);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync(); // 准备播放
//                Log.d(TAG, SUB + "startPlay：" + audioList.get(currentMusic).getTitle());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        /*暂停播放*/
        public void pausePlay()
        {
            mediaPlayer.pause();
            sendMessage(UPDATE_PLAY_STATUS);
        }

        /*停止播放*/
        public void stopPlay()
        {
            mediaPlayer.stop();
            sendMessage(UPDATE_PLAY_STATUS);
        }

        /*播放上一曲*/
        public void preTrack()
        {
            changeTrack(PREVIOUS_TRACK);

            int color = getResources().getColor(R.color.colorAccent);
            application.getTextAudioIndex().setTextColor(color);
        }

        /*播放下一曲*/
        public void nextTrack()
        {
            changeTrack(NEXT_TRACK);
        }

        /*播放模式：顺序播放，随机播放，循环播放（单曲和列表）*/
        public void changePlayMode()
        {
            currentMode = (currentMode + 5) % 4;
            Log.d(TAG, SUB + "changePlayMode");

//            Toast.makeText(getBaseContext(), PLAY_MODE[currentMode],
//                    Toast.LENGTH_SHORT).show();
            sendMessage(UPDATE_PLAY_MODE);
        }

        /*得到当前的播放模式*/
        public String getCurrentMode()
        {
            return PLAY_MODE[currentMode];
        }

        /**
         * 改变当前音乐的播放进度。
         * @param progress 调整的目标播放时间点
         */
        public void changeProgress(int progress)
        {
            if ( mediaPlayer.isPlaying() )
            {
                mediaPlayer.seekTo(progress);
            }
            else
            {
                startPlay(application.getCurrentMusic(), progress);
            }
        }

        /**
         * 改变播放的歌曲：用于下一曲和上一曲切换音乐时使用。
         */
        private void changeTrack(int action)
        {
            Log.d(TAG, SUB + currentMode );
            int currentMusic = application.getCurrentMusic();

            switch ( currentMode )
            {
                case MODE_SINGLE_CYCLE: // 单曲循环
                    startPlay(application.getCurrentMusic(), 0);
                    break;

                case MODE_LIST_CYCLE: // 列表循环
                    if ( PREVIOUS_TRACK == action ) // 上一曲的动作
                    {
                        /*对是否是第一首歌曲进行判断*/
                        if ( 0 == currentMusic )
                        {
                            startPlay(audioList.size() - 1, 0);
                        }
                        else
                        {
                            startPlay(currentMusic - 1, 0);
                        }
                    }
                    else
                    {
                        if ( audioList.size() == currentMusic + 1 )
                        {
                            startPlay(0, 0);
                        }
                        else
                        {
                            startPlay(currentMusic + 1, 0);
                        }
                    }
                    break;

                case MODE_RANDOM: // 随机播放
                    Random random = new Random();
                    int randomPosition = random.nextInt(audioList.size()) - 1;
                    startPlay(randomPosition, 0);
                    break;

                case MODE_SEQUENCE: // 顺序播放

                    if ( PREVIOUS_TRACK == action ) // 上一曲
                    {
                        if ( 0 == currentMusic )
                        {
                            Toast.makeText(MusicService.this, "前面没有音乐了",
                                    Toast.LENGTH_SHORT).show();
                            stopPlay();
                        }
                        else
                        {
                            startPlay(currentMusic - 1, 0);
                        }
                    }
                    else
                    {
                        if ( audioList.size() == currentMusic + 1 )
                        {
                            Toast.makeText(MusicService.this, "没有音乐了。",
                                    Toast.LENGTH_SHORT).show();
                            stopPlay();
                        }
                        else
                        {
                            startPlay(currentMusic + 1, 0);
                        }
                    }
                    break;

                default:
                    break;
            }

        }

        public void startTiming(int minute)
        {
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            int targetTime = 60 * 1000 * minute; // 倒计时的毫秒数
            long triggerAtTime = SystemClock.elapsedRealtime() + targetTime;

            Intent intent = new Intent(MusicService.this, AlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(MusicService.this, 0, intent, 0);

            manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        }
    }


    /**
     * 创建内部类，用于更新UI
     */
    class MusicHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
//            Log.d(TAG, SUB + "handleMessage");
            switch ( msg.what )
            {
                case UPDATE_CURRENT_MUSIC:
                    updateCurrentMusic();
                    break;

                case UPDATE_DURATION:
                    updateDuration();
                    break;

                case UPDATE_PROGRESS:
                    updateProgress();
                    break;

                case UPDATE_PLAY_STATUS:
                    updatePlayStatus();
                    break;

                case UPDATE_PLAY_MODE:
                    updatePlayMode();
                    break;

                default:
                    break;
            }
        }
    }
}
