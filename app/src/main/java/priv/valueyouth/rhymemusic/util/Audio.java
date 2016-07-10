package priv.valueyouth.rhymemusic.util;

import android.os.Bundle;
import android.provider.MediaStore;

/**
 * 封装一个Audio的类
 * 存储读取到的音频信息
 * Created by Idea on 2016/5/4.
 */
public class Audio
{
    private String title;       // 音乐名称
    private String titleKey;    // 此key用于查找，排序和分组
    private String artist;      // 歌手名称
    private String artistKey;   //
    private String composer;    // 作曲家名称
    private String album;       // 专辑名称
    private String albumKey;    //
    private String displayName; //
    private String mimeType;    // 文件类型
    private String data;        // The data stream for the file

    private int id;             // The unique ID for a row
    private int artistId;       // 歌手ID
    private int albumId;        // 专辑ID
    private int year;           // The year the audio file was recorded, if any
    private int track;          //
    private int duration = 0;   // 初始化音乐时间为0
    private int size = 0;       // The size of the file in bytes

    private boolean isRingtone = false;        // 是否为铃声
    private boolean isPodcast = false;         // 是否是播客
    private boolean isAlarm = false;           // 是否是闹铃
    private boolean isMusic = false;           // 是否是音乐
    private boolean isNotification = false;    // 是否有通知

    /**
     * 构造方法。初始化各种变量
     * @param bundle
     */
    public Audio(Bundle bundle)
    {

        title = bundle.getString(MediaStore.Audio.Media.TITLE);
        titleKey = bundle.getString(MediaStore.Audio.Media.TITLE_KEY);
        artist = bundle.getString(MediaStore.Audio.Media.ARTIST);
        artistKey = bundle.getString(MediaStore.Audio.Media.ARTIST_KEY);
        composer = bundle.getString(MediaStore.Audio.Media.COMPOSER);
        album = bundle.getString(MediaStore.Audio.Media.ALBUM);
        albumKey = bundle.getString(MediaStore.Audio.Media.ALBUM_KEY);
        displayName = bundle.getString(MediaStore.Audio.Media.DISPLAY_NAME);
        mimeType = bundle.getString(MediaStore.Audio.Media.MIME_TYPE);
        data = bundle.getString(MediaStore.Audio.Media.DATA);

        id = bundle.getInt(MediaStore.Audio.Media._ID);
        artistId = bundle.getInt(MediaStore.Audio.Media.ARTIST_ID);
        albumId = bundle.getInt(MediaStore.Audio.Media.ALBUM_ID);
        year = bundle.getInt(MediaStore.Audio.Media.YEAR);
        track = bundle.getInt(MediaStore.Audio.Media.TRACK);
        duration =bundle.getInt(MediaStore.Audio.Media.DURATION);
        size = bundle.getInt(MediaStore.Audio.Media.SIZE);

        isRingtone = bundle.getInt(MediaStore.Audio.Media.IS_RINGTONE) == 1;
        isPodcast = bundle.getInt(MediaStore.Audio.Media.IS_PODCAST) == 1;
        isAlarm = bundle.getInt(MediaStore.Audio.Media.IS_ALARM) == 1;
        isMusic = bundle.getInt(MediaStore.Audio.Media.IS_MUSIC) == 1;
        isNotification = bundle.getInt(MediaStore.Audio.Media.IS_NOTIFICATION) == 1;

    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitleKey()
    {
        return titleKey;
    }

    public void setTitleKey(String titleKey)
    {
        this.titleKey = titleKey;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getArtistKey()
    {
        return artistKey;
    }

    public void setArtistKey(String artistKey)
    {
        this.artistKey = artistKey;
    }

    public String getComposer()
    {
        return composer;
    }

    public void setComposer(String composer)
    {
        this.composer = composer;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getAlbumKey()
    {
        return albumKey;
    }

    public void setAlbumKey(String albumKey)
    {
        this.albumKey = albumKey;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getArtistId()
    {
        return artistId;
    }

    public void setArtistId(int artistId)
    {
        this.artistId = artistId;
    }

    public int getAlbumId()
    {
        return albumId;
    }

    public void setAlbumId(int albumId)
    {
        this.albumId = albumId;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getTrack()
    {
        return track;
    }

    public void setTrack(int track)
    {
        this.track = track;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public boolean isRingtone()
    {
        return isRingtone;
    }

    public void setRingtone(boolean ringtone)
    {
        isRingtone = ringtone;
    }

    public boolean isPodcast()
    {
        return isPodcast;
    }

    public void setPodcast(boolean podcast)
    {
        isPodcast = podcast;
    }

    public boolean isAlarm()
    {
        return isAlarm;
    }

    public void setAlarm(boolean alarm)
    {
        isAlarm = alarm;
    }

    public boolean isMusic()
    {
        return isMusic;
    }

    public void setMusic(boolean music)
    {
        isMusic = music;
    }

    public boolean isNotification()
    {
        return isNotification;
    }

    public void setNotification(boolean notification)
    {
        isNotification = notification;
    }
}
