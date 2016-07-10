package priv.valueyouth.rhymemusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import priv.valueyouth.rhymemusic.activity.MainActivity;
import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.adapter.MusicListAdapter;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.util.Audio;
import priv.valueyouth.rhymemusic.util.AudioUtil;

/**
 * 本地音乐
 * Created by Idea on 2016/5/3.
 */
public class LocalMusicFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[LocalMusicFragment]#";

    private MainActivity mainActivity;
    private MusicApplication application;

    private ListView musicView;
    private ArrayList<Audio> audioList;
    private MusicListAdapter musicListAdapter;

    private TextView textAllSongs;
    private TextView textExtraScene; // 彩蛋

    private SearchView searchMusic;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
        application = (MusicApplication) mainActivity.getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d(TAG, SUB + "onCreateView");
        View view = inflater.inflate(R.layout.tab_local_music, null);

        initComponents(view);
        loadData(); // 加载本地音乐列表

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, SUB + "onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG, SUB + "onStart");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, SUB + "onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, SUB + "onPause");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, SUB + "onStop");
    }

    @Override
    public void onDestroyView()
    {
        Log.d(TAG, SUB + "onDestroyView");

        super.onDestroyView();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.d(TAG, SUB + "onDetach");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Log.d(TAG, SUB + "歌曲位置" + audioList.get(position));
        application.getMusicBinder().startPlay(position, 0);
//        view.setBackgroundResource(R.color.colorAccent);
    }

    @Override
    public void onClick(View v)
    {
        if ( v.getId() == R.id.text_extra_scene )
        {
            int color = getResources().getColor(R.color.colorAccent);
            textExtraScene.setTextColor(color);

            String extraScene = "常言说的好：好奇害死猫。";
            Toast.makeText(getContext(), extraScene, Toast.LENGTH_SHORT).show();
        }
    }

    private void initComponents(View view)
    {
        /*文本类初始化*/
        textExtraScene = (TextView) view.findViewById(R.id.text_extra_scene);
        textAllSongs = (TextView) view.findViewById(R.id.text_sum_songs);
        textExtraScene.setOnClickListener(this);

        /*初始化列表*/
        musicView = (ListView) view.findViewById(R.id.id_music_list);
        musicView.setOnItemClickListener(this);
    }

    /**
     * 加载音乐列表数据
     */
    private void loadData()
    {
        /*加载音乐列表*/
        audioList = AudioUtil.getAudioList(mainActivity);
        musicListAdapter = new MusicListAdapter(mainActivity, audioList, application);
        musicView.setAdapter(musicListAdapter);

        /*显示本地音乐数量*/
        String allSongs = "(共" + audioList.size() + "首)";
        textAllSongs.setText(allSongs);
    }

}
