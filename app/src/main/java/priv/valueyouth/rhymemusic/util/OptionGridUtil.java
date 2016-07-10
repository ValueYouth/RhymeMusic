package priv.valueyouth.rhymemusic.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import priv.valueyouth.rhymemusic.R;

/**
 * 将图片与图片下方的文字相对应
 * Created by Idea on 2016/6/7.
 */
public class OptionGridUtil
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[OptionGridUtil]#";

    private List<OptionGrid> options = new ArrayList<>();

    public OptionGridUtil()
    {
        loadData(); // 加载数据
    }

    public List<OptionGrid> getOptions()
    {
        Log.d(TAG, SUB + "getOptions:" + options);
        return options;
    }

    /**
     * 加载相关数据
     */
    private void loadData()
    {
        options.add(new OptionGrid(R.drawable.play_mode, "播放模式"));
        options.add(new OptionGrid(R.drawable.auto_stop, "定时停止"));
        options.add(new OptionGrid(R.drawable.audio_info, "歌曲信息"));
        options.add(new OptionGrid(R.drawable.more, "更多信息"));
    }
}
