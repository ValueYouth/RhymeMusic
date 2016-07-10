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

/**
 * Auto Stop自动停止播放
 * Created by Idea on 2016/6/11.
 */
public class ASDialogAdapter extends BaseAdapter
{
    private List<String> list = new ArrayList<>();

    private Context context;

    public ASDialogAdapter(Context context)
    {
        this.context = context;

        loadData();
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_audio_stop, null);

        TextView autoStop = (TextView) convertView.findViewById(R.id.text_stop_time);
        autoStop.setText(list.get(position));

        return convertView;
    }

    /**
     * 加载ListView中显示的数据
     */
    private void loadData()
    {
        String[] time =
                {
                    "10分钟之后", "20分钟之后", "30分钟之后",
                    "40分钟之后", "60分钟之后", "90分钟之后"
                };

        for ( int i = 0; i < time.length; i++ )
        {
            list.add(time[i]);
        }
    }
}
