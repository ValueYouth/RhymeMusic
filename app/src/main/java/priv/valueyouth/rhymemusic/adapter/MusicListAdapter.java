package priv.valueyouth.rhymemusic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.util.Audio;
import priv.valueyouth.rhymemusic.util.AudioUtil;
import priv.valueyouth.rhymemusic.help.FormatHelper;
import priv.valueyouth.rhymemusic.util.ImageUtil;

/**
 * 本地音乐列表适配器
 * Created by Idea on 2016/5/26.
 */
public class MusicListAdapter extends BaseAdapter
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[MusicListAdapter]#";

    private Context context;

    private ArrayList<Audio> audioList;

    private MusicApplication application;

    public MusicListAdapter(Context context, ArrayList<Audio> audioList,
                            MusicApplication application)
    {
        this.context     = context;
        this.audioList   = audioList;
        this.application = application;
    }

    @Override
    public int getCount()
    {
        return audioList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return audioList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if ( convertView == null )
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_music, null);
            viewHolder = new ViewHolder();

            viewHolder.musicIndex = (TextView) convertView.findViewById(R.id.audio_index);
            viewHolder.musicTitle = (TextView) convertView.findViewById(R.id.music_title);
            viewHolder.musicArtist = (TextView) convertView.findViewById(R.id.music_artist);
            viewHolder.musicDuration = (TextView) convertView.findViewById(R.id.music_duration);
            viewHolder.musicCover = (ImageView) convertView.findViewById(R.id.music_cover);
            convertView.setTag(viewHolder);

            application.setTextAudioIndex(viewHolder.musicIndex);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Audio audio = audioList.get(position);
        viewHolder.musicIndex.setText(FormatHelper.formatIndex(position + 1));
        viewHolder.musicTitle.setText(audio.getTitle());
        viewHolder.musicArtist.setText(audio.getArtist());
        viewHolder.musicDuration.setText(FormatHelper.formatDuration(audio.getDuration()));

        Bitmap bitmap = ImageUtil.getAlbumCover(context, audio.getId());

        if ( bitmap == null )
        {
            viewHolder.musicCover.setImageResource(R.drawable.defalt_artwork);
        }
        else
        {
            viewHolder.musicCover.setImageBitmap(bitmap);
        }

        return convertView;
    }


    /**
     * 创建内部类ViewHolder，用于对控件的实例进行缓存。
     */
    class ViewHolder
    {
        TextView  musicIndex;
        TextView  musicTitle;
        TextView  musicArtist;
        TextView  musicDuration;
        ImageView musicCover;
    }
}
