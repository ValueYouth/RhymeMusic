package priv.valueyouth.rhymemusic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.util.OptionGrid;

/**
 * GridView显示事件
 * Created by Idea on 2016/6/5.
 */
public class GridAdapter extends BaseAdapter
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[GridAdapter]#";

    private Context context;
    private List<OptionGrid> options = null;

    private ViewHolder viewHolder;

    public GridAdapter(Context context, List<OptionGrid> options)
    {
        this.context = context;
        this.options = options;

        Log.d(TAG, SUB + options.size());
    }

    @Override
    public int getCount()
    {
        return options.size();
    }

    @Override
    public Object getItem(int position)
    {
        return options.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if ( convertView == null )
        {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_grid_option, null);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_option);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_option);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(options.get(position).getImageID());
        viewHolder.textView.setText(options.get(position).getText());


        return convertView;
    }

    class ViewHolder
    {
        ImageView imageView;
        TextView textView;
    }

}
