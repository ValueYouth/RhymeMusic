package priv.valueyouth.rhymemusic.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import priv.valueyouth.rhymemusic.help.FormatHelper;

/**
 * 读取歌词源文件，并进行处理。
 * 分理出时间及该时间对应的歌词内容。
 * Created by Idea on 2016/6/1.
 */
public class LyricUtils
{
    /*一行歌词的正则表达式匹配*/
    private static final String LINE_REGEX =
            "^(\\[\\d{2,}:\\d\\d\\.\\d\\d\\]\\s*)+(.+)$";

    /*与该行歌词对应的时间的正则表达式匹配*/
    private static final String TIME_REGEX =
            "\\[\\d{2,}:\\d\\d\\.\\d\\d\\]";

    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[LyricUtils]#";

    private List<Lyric> lyrics = new ArrayList<Lyric>(); // 处理后的歌词与时间的集合


    /**
     * 读取歌词文件，并将其处理后存入List中
     * @param path 歌词文件路径
     */
    public void setLyricPath(String path)
    {
        Log.d(TAG, SUB + "setLyricPath,path" + path);

        if ( path == null || 0 == path.length() )
        {
            return;
        }
        else
        {
            lyrics.clear();

            List<String> lyricLines = readFile(path);
            for (Iterator<String> iterator = lyricLines.iterator(); iterator.hasNext();)
            {
                String line = iterator.next();
//                Log.d(TAG, SUB + "iterator.next()" + line);
                matchLyric(line);
            }
        }
    }

    /**
     * 从文件中读取每一行，将其放到一个list中。
     * @param path 歌词文件路径
     * @return list
     */
    private List<String> readFile(String path)
    {
        List<String> lyricLines = new ArrayList<String>();
        File file = new File(path.replace(".mp3", ".lrc"));

        if ( file.exists() && !file.isDirectory() )
        {
            Log.d(TAG, SUB + "读取到的歌词文件是" + file);
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;

            try
            {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                String lrcLine;

                while ( (lrcLine = bufferedReader.readLine()) != null )
                {
                    lyricLines.add(lrcLine); // 将一行的字符串存入list
                }
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                // 关闭bufferedReader与fileReader
                try
                {
                    if ( bufferedReader != null )
                    {
                        bufferedReader.close();
                    }
                    if ( fileReader != null )
                    {
                        fileReader.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            Log.d(TAG, SUB + "file.isDirectory():" + file.isDirectory()
                + "file.exists()" + file.exists());
        }

        return lyricLines;
    }

    /**
     * 匹配每行中的歌词与时间。
     * @param line 一行中歌词与时间的混合显示
     */
    private void matchLyric(String line)
    {
        Pattern pattern;
        Matcher matcher;

        /*匹配歌词*/
        pattern = Pattern.compile(LINE_REGEX);
        matcher = pattern.matcher(line); // 匹配整行

        if ( !matcher.matches() )
        {
            return ;
        }

        String lyric = matcher.group(2); // 歌词内容
        if ( lyric == null || 0 == lyric.length() )
        {
            return ;
        }
//        Log.d(TAG, SUB + lyric);

        /*匹配时间*/
        pattern = Pattern.compile(TIME_REGEX);
        matcher = pattern.matcher(line); // 从整行中匹配时间

        while ( matcher.find() )
        {
            /*获取所有对应的时间点，并将时间转换成毫秒*/
            long time = FormatHelper.formatMillions(matcher.group());

            lyrics.add(new Lyric(time, lyric));
        }
    }

    /**
     * 返回一个List。存放的是处理后的歌词及对应的时间
     * @return List
     */
    public List<Lyric> getLyrics()
    {
        Log.d(TAG, SUB + "getLyrics");
        return lyrics;
    }
}