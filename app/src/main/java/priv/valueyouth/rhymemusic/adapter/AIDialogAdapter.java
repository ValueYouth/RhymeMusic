package priv.valueyouth.rhymemusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.help.FormatHelper;
import priv.valueyouth.rhymemusic.util.Audio;
import priv.valueyouth.rhymemusic.util.AudioUtil;

/**
 * 音乐信息
 * Created by Idea on 2016/6/10.
 */
public class AIDialogAdapter extends BaseAdapter
{
    private List<AudioInfo> audioInfos = new ArrayList<>();

    private Context context;

    public AIDialogAdapter(Context context)
    {
        this.context = context;

        setData(); // 初始化数据
    }

    @Override
    public int getCount()
    {
        return audioInfos.size();
    }

    @Override
    public Object getItem(int position)
    {
        return audioInfos.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(context)
                .inflate(R.layout.item_audio_info, null);

        TextView AudioAttr = (TextView) convertView.findViewById(R.id.text_attr);
        TextView AudioContent = (TextView) convertView.findViewById(R.id.text_content);

        AudioAttr.setText(audioInfos.get(position).getTextAttr());
        AudioContent.setText(audioInfos.get(position).getTextContent());

        return convertView;
    }

    /**
     * 设置数据
     * @return list集合
     */
    private void setData()
    {
        String[] audioAttr = {"文件名称:", "文件路径:", "文件大小:", "播放时长:", "专辑名称:"};
        MusicApplication application = (MusicApplication) context.getApplicationContext();
        int currentMusic = application.getCurrentMusic();
        Audio audio = AudioUtil.getAudioList(context).get(currentMusic);

        String size = FormatHelper.formatAudioSize(audio.getSize());
        String duration = FormatHelper.formatDuration(audio.getDuration());

        audioInfos.add(new AudioInfo("专辑名称:", audio.getAlbum()));
        audioInfos.add(new AudioInfo("文件名称:", audio.getTitle()));
        audioInfos.add(new AudioInfo("文件路径:", audio.getData()));
        audioInfos.add(new AudioInfo("文件大小:", size));
        audioInfos.add(new AudioInfo("播放时长:", duration));
    }

    /**
     * 内部类
     * 音乐信息
     */
    class AudioInfo
    {
        private String textAttr;    // 音乐信息类别
        private String textContent; // 音乐信息内容

        public AudioInfo(String textAttr, String textContent)
        {
            this.textAttr = textAttr;
            this.textContent = textContent;
        }

        public String getTextAttr()
        {
            return textAttr;
        }

        public void setTextAttr(String textAttr)
        {
            this.textAttr = textAttr;
        }

        public String getTextContent()
        {
            return textContent;
        }

        public void setTextContent(String textContent)
        {
            this.textContent = textContent;
        }
    }
}
