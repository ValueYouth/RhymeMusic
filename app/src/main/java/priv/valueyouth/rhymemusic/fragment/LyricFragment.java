package priv.valueyouth.rhymemusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.activity.PlaybackActivity;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.lrc_view.LyricView;
import priv.valueyouth.rhymemusic.util.Audio;
import priv.valueyouth.rhymemusic.util.AudioUtil;
import priv.valueyouth.rhymemusic.util.LyricUtils;

/**
 * 歌词的界面
 * Created by Idea on 2016/5/31.
 */
public class LyricFragment extends Fragment
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[LyricFragment]#";

    private PlaybackActivity playbackActivity;
    private MusicApplication application;

    private LyricView lyricView;

    private List<Audio> audioList; // 音乐列表

    private Handler handler = new Handler();


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.d(TAG, SUB + "onAttach");

        playbackActivity = (PlaybackActivity) getActivity();
        application = (MusicApplication) getActivity().getApplication();
        audioList = AudioUtil.getAudioList(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d(TAG, SUB + "onCreateView");
        View view = inflater.inflate(R.layout.view_lyric, null);

        lyricView = (LyricView) view.findViewById(R.id.lrc_view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.d(TAG, SUB + "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        initLyric();
    }

    /**
     * 初始化歌词
     */
    public void initLyric()
    {
        Log.d(TAG, SUB + "initLyric");

        LyricUtils lyricUtils = new LyricUtils();
        int currentMusic = application.getCurrentMusic();
        String path = audioList.get(currentMusic).getData();
        lyricUtils.setLyricPath(path);

        lyricView.setLyricContent(lyricUtils.getLyrics());

        handler.post(runnable);
    }

    /**
     * 开始执行线程
     */
    public void startRunnable()
    {
        handler.post(runnable);
    }

    /**
     * 停止执行线程
     */
    public void destroyRunnable()
    {
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run()
        {
            long time = application.getMediaPlayer().getCurrentPosition();
            lyricView.setCurrentTime(time, true);
            handler.postDelayed(runnable, 500);
        }
    };


}
