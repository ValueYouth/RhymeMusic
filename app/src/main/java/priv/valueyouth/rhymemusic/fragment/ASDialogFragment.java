package priv.valueyouth.rhymemusic.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.adapter.AIDialogAdapter;
import priv.valueyouth.rhymemusic.adapter.ASDialogAdapter;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.service.MusicService;

/**
 * Auto Stop自动停止播放
 * Created by Idea on 2016/6/11.
 */
public class ASDialogFragment extends DialogFragment
        implements AdapterView.OnItemClickListener
{
    private ListView listView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_audio_stop, null);

        listView = (ListView) view.findViewById(R.id.list_auto_stop);
        listView.setOnItemClickListener(this);
        setAdapter();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    private void setAdapter()
    {
        ASDialogAdapter adapter = new ASDialogAdapter(getActivity());

        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        int[] stopTime = {10, 20, 30, 40, 60, 90};

        /*倒计时具体代码实现转入service中*/
        MusicApplication application = (MusicApplication) getActivity().getApplication();
        MusicService.MusicBinder musicBinder = application.getMusicBinder();

        musicBinder.startTiming(stopTime[position]);

        String tips = "设置成功，音乐将于" + stopTime[position] + "分钟之后关闭";
        Toast.makeText(getActivity(), tips, Toast.LENGTH_SHORT).show();
        getDialog().dismiss();
    }
}
