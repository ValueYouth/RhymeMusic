package priv.valueyouth.rhymemusic.help;

import android.util.Log;

/**
 * 一些关于音乐格式的转换
 * Created by Idea on 2016/5/26.
 */
public class FormatHelper
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[FormatHelper]#";

    /**
     * 输入ms，输出字符串格式的时间
     * @param duration 时长ms
     * @return 字符串格式时间
     */
    public static String formatDuration(int duration)
    {
        int seconds = duration / 1000;
        int minute = seconds / 60;
        int second = seconds % 60;

        Log.d("Tag", minute + ":" + second );
        return (minute >= 10 ? minute : "0" + minute) + ":" +
               (second >= 10 ? second : "0" +second);
    }

    public static String formatDurationSeconds(String duration)
    {
        if ( duration == null || 0 == duration.length() )
        {
            return "00:00";
        }
        else
        {
            int seconds = Integer.parseInt(duration);
            int minute = seconds / 60;
            int second = seconds % 60;

            Log.d("Tag", "formatDurationSeconds--" + minute + ":" + second );
            return (minute >= 10 ? minute : "0" + minute) + ":" +
                    (second >= 10 ? second : "0" +second);
        }

    }

    /**
     * 输入时间字符串，以long类型输出时间。
     * @param time 字符串格式的时间
     * @return long类型的时间
     */
    public static long formatMillions(String time)
    {
        if ( time == null || 0 == time.length() )
        {
            return 0;
        }
        else
        {
            time = time.replace("[", "").replace("]", ""); // 去掉左右中括号
            String[] min_sec = time.split(":"); // 将分与秒分开

            if ( min_sec == null || min_sec.length != 2 )
            {
                return 0;
            }
            else
            {
                long minute = Long.parseLong(min_sec[0]);
                float second = Float.parseFloat(min_sec[1]);

                return minute * 60 * 1000 + (long) second * 1000;
            }
        }
    }

    public static String formatAudioSize(long audioSize)
    {
        double size = audioSize / 1024.0 / 1024;

        String formatSize = size + "";

        if ( formatSize.length() < 4 )
        {
            formatSize = size + "MB";
        }
        else
        {
            formatSize = ((size + "").substring(0, 3)) + "MB";
        }

        Log.d(TAG, SUB + size);
        return formatSize;
    }

    /**
     * 音乐列表首位显示音乐的索引
     * @param position 当前音频索引
     * @return 目标显示文本
     */
    public static String formatIndex(int position)
    {
        return position < 10 ? "0" + position : position + "";
    }
}
