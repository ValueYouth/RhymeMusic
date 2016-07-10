package priv.valueyouth.rhymemusic.bean;

/**
 * 在线音频解析
 * Created by Idea on 2016/6/22.
 */
public class AudioBean
{
    private String singerid; // 歌手ID

    private String singername; // 歌手名

    private String songname; // 歌曲名称

    private String songid; // 歌曲ID

    private String albumid; // 专辑ID

    private String album; // 专辑名称

    private String albumpic_big; // 专辑大图片，高度300

    private String albumpic_small; // 专辑小图片，高度90

    private String downUrl; // 下载链接

    private String seconds; // 歌曲时长

    private String url;

    public String getSingerid()
    {
        return singerid;
    }

    public void setSingerid(String singerid)
    {
        this.singerid = singerid;
    }

    public String getSingername()
    {
        return singername;
    }

    public void setSingername(String singername)
    {
        this.singername = singername;
    }

    public String getSongname()
    {
        return songname;
    }

    public void setSongname(String songname)
    {
        this.songname = songname;
    }

    public String getSongid()
    {
        return songid;
    }

    public void setSongid(String songid)
    {
        this.songid = songid;
    }

    public String getAlbumid()
    {
        return albumid;
    }

    public void setAlbumid(String albumid)
    {
        this.albumid = albumid;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getAlbumpic_big()
    {
        return albumpic_big;
    }

    public void setAlbumpic_big(String albumpic_big)
    {
        this.albumpic_big = albumpic_big;
    }

    public String getAlbumpic_small()
    {
        return albumpic_small;
    }

    public void setAlbumpic_small(String albumpic_small)
    {
        this.albumpic_small = albumpic_small;
    }

    public String getDownUrl()
    {
        return downUrl;
    }

    public void setDownUrl(String downUrl)
    {
        this.downUrl = downUrl;
    }

    public String getSeconds()
    {
        return seconds;
    }

    public void setSeconds(String seconds)
    {
        this.seconds = seconds;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
