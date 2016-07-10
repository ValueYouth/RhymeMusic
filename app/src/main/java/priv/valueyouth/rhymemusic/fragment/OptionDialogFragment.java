package priv.valueyouth.rhymemusic.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.adapter.GridAdapter;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.service.MusicService;
import priv.valueyouth.rhymemusic.util.OptionGrid;
import priv.valueyouth.rhymemusic.util.OptionGridUtil;


/**
 * 弹出式对话框
 * Created by Idea on 2016/6/5.
 */
public class OptionDialogFragment extends DialogFragment
        implements AdapterView.OnItemClickListener
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[OptionDialogFragment]#";

    private static final int PLAY_MODE  = 0;
    private static final int AUTO_STOP  = 1;
    private static final int AUDIO_INFO = 2;
    private static final int MORE_INFO = 3;

    private GridView gridView;

    private List<OptionGrid> options = null;

    private Calendar c;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_option, null);

        gridView = (GridView) view.findViewById(R.id.grid_view);
        gridView.setOnItemClickListener(this);
        setOptionGrid(); // 设置具体的对话框显示样式
        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    /**
     * 设置点击专辑图片后的显示方式
     * GridView方式
     */
    private void setOptionGrid()
    {
        OptionGridUtil gridUtils = new OptionGridUtil();

        options = gridUtils.getOptions();
        GridAdapter adapter = new GridAdapter(getActivity(), options);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        switch ( position )
        {
            case PLAY_MODE:
                MusicApplication application =
                        (MusicApplication) getActivity().getApplication();
                MusicService.MusicBinder musicBinder = application.getMusicBinder();

                musicBinder.changePlayMode();
                break;

            case AUTO_STOP:
                getDialog().dismiss(); // 关闭父对话框

                DialogFragment dialogFragment = new ASDialogFragment();
                FragmentManager manager = getFragmentManager();
                dialogFragment.show(manager, "dialog auto stop");
                break;

            case AUDIO_INFO:
                getDialog().dismiss(); // 关闭父对话框

                DialogFragment dialogFragment1 = new AIDialogFragment();
                FragmentManager manager1 = getFragmentManager();
                dialogFragment1.show(manager1, "dialog audio info");
                break;

            case MORE_INFO:
                getDialog().dismiss(); // 关闭父对话框

                Toast.makeText(getActivity(), "暂未开发", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }


}
