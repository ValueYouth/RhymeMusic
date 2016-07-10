package priv.valueyouth.rhymemusic.test;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Idea on 2016/6/24.
 */
public class OnlinePlayer implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnPreparedListener
{
    private MediaPlayer mediaPlayer;

    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[OnlinePlayer]#";

    public OnlinePlayer(String url)
    {
        mediaPlayer = new MediaPlayer();
        initPlayer(url);
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        mp.stop();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        mp.stop();
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent)
    {
//        Log.d(TAG, SUB+ "onBufferingUpdate" + percent + "%");
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        mp.start();
    }

    private void initPlayer(String url)
    {
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);

        mediaPlayer.reset();

        try
        {
            mediaPlayer.setDataSource(url);

            mediaPlayer.prepareAsync();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
