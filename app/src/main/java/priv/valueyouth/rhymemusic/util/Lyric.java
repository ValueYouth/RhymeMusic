package priv.valueyouth.rhymemusic.util;

/**
 * 歌词实体类
 * Created by Idea on 2016/6/1.
 */
public class Lyric
{
    private long   time;    // 歌词当前的时间
    private String content; // 一行歌词的内容

    public Lyric(long time, String content)
    {
        this.time = time;
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }
}
