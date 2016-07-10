package priv.valueyouth.rhymemusic.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.List;
import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.adapter.ViewPagerAdapter;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.fragment.ArtWorkFragment;
import priv.valueyouth.rhymemusic.fragment.LyricFragment;
import priv.valueyouth.rhymemusic.help.FormatHelper;
import priv.valueyouth.rhymemusic.service.MusicService;
import priv.valueyouth.rhymemusic.util.Audio;
import priv.valueyouth.rhymemusic.util.AudioUtil;


public class PlaybackActivity extends BaseActivity
        implements View.OnClickListener,SeekBar.OnSeekBarChangeListener
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[PlaybackActivity]#";

    private MusicApplication application;

    private ImageButton btnBack;
    private ImageButton btnOption;
    private ImageButton btnPlay;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private TextView textPlayMode;
    private TextView textElapsed; // 音乐播放流逝的时间
    private TextView textDuration; // 音乐总时长
    private TextView textTitle; // 标题栏的文字显示
    private SeekBar seekBar; // 音乐进度条，可拖动

    private List<Audio> audioList; // 音乐列表

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private MusicService.MusicBinder musicBinder;

    private ArtWorkFragment artWorkFragment;
    private LyricFragment lyricFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, SUB + "onCreate");

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();  // 隐藏掉标题栏
        setContentView(R.layout.activity_playback);

        initComponents(); // 初始化组件
        registerLocalReceiver();  // 注册本地广播接收器
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG, SUB + "onStop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        lyricFragment.destroyRunnable();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        updateProgress();
    }

    /**
     * 按钮类的监听时间
     * @param v v
     */
    @Override
    public void onClick(View v)
    {
        switch ( v.getId() )
        {
            case R.id.detail_play:
                playMusic();
                break;
            case R.id.detail_previous:
                btnPlay.setBackgroundResource(R.drawable.detail_play);
                musicBinder.preTrack();
                break;

            case R.id.detail_next:
                btnPlay.setBackgroundResource(R.drawable.detail_play);
                musicBinder.nextTrack();
                break;

            case R.id.title_back:
                finish();
                break;

            case R.id.title_option:
                Log.d(TAG, SUB + "title_option");
                artWorkFragment.clickCover();
                break;

            default:
                break;
        }
    }

    /**
     * 进度条改变的监听事件
     * @param seekBar 进度条对象
     * @param progress 当前进度
     * @param fromUser 是否是用户操作
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        if ( fromUser )
        {
            musicBinder.changeProgress(progress);
        }
    }

    /**
     * 开始拖动拖动条
     * @param seekBar seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    /**
     * 停止拖动拖动条
     * @param seekBar seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        // ...
    }


    /**
     * 初始化相关的组件
     */
    private void initComponents()
    {
        Log.d(TAG, SUB + "initComponents");
        application = (MusicApplication) getApplication();
        musicBinder = application.getMusicBinder();

        /*初始化音乐列表*/
        audioList = AudioUtil.getAudioList(PlaybackActivity.this);
        int currentMusic = application.getCurrentMusic();

        /*按钮类的初始化*/
        btnPlay = (ImageButton) findViewById(R.id.detail_play);
        btnPrevious = (ImageButton) findViewById(R.id.detail_previous);
        btnNext = (ImageButton) findViewById(R.id.detail_next);
        btnBack = (ImageButton) findViewById(R.id.title_back);
        btnOption = (ImageButton) findViewById(R.id.title_option);
        btnPlay.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnOption.setOnClickListener(this);
        setResource();

        /*进度条的初始化*/
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBar.setMax(audioList.get(currentMusic).getDuration());
        seekBar.setProgress(application.getCurrentPosition());
        seekBar.setOnSeekBarChangeListener(this);

        /*播放流逝的时间*/
        textElapsed = (TextView) findViewById(R.id.text_elapsed);
        updateProgress();

        /*音乐的总时间*/
        textDuration = (TextView) findViewById(R.id.text_duration);
        updateDuration();

        /*播放页面的音乐标题*/
        textTitle = (TextView) findViewById(R.id.title_text);
        updateMusicInfo();

        /*显示播放模式*/
        textPlayMode = (TextView) findViewById(R.id.text_current_play_mode);
        updatePlayMode();

        /*左右滑动的切换页面适配器*/
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.change_view);
        pager.setAdapter(adapter);

        artWorkFragment = (ArtWorkFragment) adapter.getItem(pager.getCurrentItem());
        lyricFragment = (LyricFragment) adapter.getItem(pager.getCurrentItem() + 1);

    }

    /**
     * 播放音乐
     */
    private void playMusic()
    {
        if ( application.getMediaPlayer().isPlaying() )
        {
            musicBinder.pausePlay();
        }
        else
        {
            musicBinder.startPlay(application.getCurrentMusic(),
                    application.getMediaPlayer().getCurrentPosition());
        }
    }

    /**
     * 注册本地广播接收器
     */
    private void registerLocalReceiver()
    {
        Log.d(TAG, SUB + "registerLocalReceiver");
        intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.ACTION_UPDATE_CURRENT_MUSIC);
        intentFilter.addAction(MusicService.ACTION_UPDATE_DURATION);
        intentFilter.addAction(MusicService.ACTION_UPDATE_PROGRESS);
        intentFilter.addAction(MusicService.ACTION_UPDATE_PLAY_STATUS);
        intentFilter.addAction(MusicService.ACTION_UPDATE_PLAY_MODE);

        localReceiver = new LocalReceiver();
        /*注册本地广播监听器*/
        application.getManager().registerReceiver(localReceiver, intentFilter);
    }

    /**
     * 更新当前音乐的显示信息
     */
    private void updateMusicInfo()
    {
        /*设置音乐名与音乐人*/

        String title = audioList.get(application.getCurrentMusic()).getTitle();
        String artist = audioList.get(application.getCurrentMusic()).getArtist();

        String showTitle = title + "—" + artist;
        textTitle.setText(showTitle);
    }

    /**
     * 更新播放音乐流逝的时间
     */
    private void updateProgress()
    {
        int currentPosition = application.getMediaPlayer().getCurrentPosition();
        String strElapsed = FormatHelper.formatDuration(currentPosition);
        textElapsed.setText(strElapsed);

        seekBar.setProgress(application.getMediaPlayer().getCurrentPosition());
    }

    /**
     * 更新音乐文件的总时长
     */
    private void updateDuration()
    {
        int currentMusic = application.getCurrentMusic();
        int duration = audioList.get(currentMusic).getDuration();
        Log.d(TAG, SUB + duration);

        textDuration.setText(FormatHelper.formatDuration(duration));
        seekBar.setMax(duration);
    }

    /**
     * 更新播放模式显示
     */
    private void updatePlayMode()
    {
        Log.d(TAG, SUB + "updatePlayMode");
        textPlayMode.setText(musicBinder.getCurrentMode());
    }

    private void setResource()
    {
        if ( application.getMediaPlayer().isPlaying() )
        {
            btnPlay.setBackgroundResource(R.drawable.detail_play);
        }
        else
        {
            btnPlay.setBackgroundResource(R.drawable.detail_pause);
            lyricFragment.destroyRunnable();
        }
    }

    /**
     * 内部类
     * 本地广播接收器。
     * 接受来自MusicService的广播，并进行处理
     */
    class LocalReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            switch (action)
            {
                case MusicService.ACTION_UPDATE_CURRENT_MUSIC:
                    Log.d(TAG, SUB + "更新音乐标题");
                    updateMusicInfo();
                    artWorkFragment.setArtWork(); //  更新专辑图片
                    lyricFragment.initLyric();
                    break;

                case MusicService.ACTION_UPDATE_DURATION:
                    Log.d(TAG, SUB + "更新音乐时长");
                    updateDuration();
                    break;

                case MusicService.ACTION_UPDATE_PROGRESS:
                    updateProgress();
                    break;

                case MusicService.ACTION_UPDATE_PLAY_STATUS:
                    setResource();
                    break;

                case MusicService.ACTION_UPDATE_PLAY_MODE:
                    updatePlayMode();
                    break;

                default:
                    break;
            }
        }
    }
}
