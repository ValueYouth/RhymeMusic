package priv.valueyouth.rhymemusic.fragment;

import android.app.AlertDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.support.v4.app.DialogFragment;

import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.adapter.AIDialogAdapter;

/**
 * AI：Audio Information 即音频信息
 * Created by Idea on 2016/6/10.
 */
public class AIDialogFragment extends DialogFragment
{
    private ListView listView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_audio_info, null);
        listView = (ListView) view.findViewById(R.id.list_audio_info);
        setAdapter();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    private void setAdapter()
    {
        AIDialogAdapter adapter = new AIDialogAdapter(getActivity());
        listView.setAdapter(adapter);
    }


}
