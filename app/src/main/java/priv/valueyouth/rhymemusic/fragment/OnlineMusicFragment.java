package priv.valueyouth.rhymemusic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.show.api.ShowApiRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.adapter.MusicListAdapter;
import priv.valueyouth.rhymemusic.adapter.TopListAdapter;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.bean.AudioBean;
import priv.valueyouth.rhymemusic.bean.JsonBean;
import priv.valueyouth.rhymemusic.service.MusicService;
import priv.valueyouth.rhymemusic.test.OnlinePlayer;
import priv.valueyouth.rhymemusic.util.Constant;
import priv.valueyouth.rhymemusic.util.OnlineAudioUtil;


/**
 * 在线音乐
 * Created by Idea on 2016/5/3.
 */
public class OnlineMusicFragment extends Fragment implements
        AdapterView.OnItemClickListener, View.OnClickListener
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[OnlineMusicFragment]#";

    public static final int GET_DATA = 1;

    private List<AudioBean> audioList = new ArrayList<>();

    private TopListAdapter topListAdapter;

    private ListView listView;
    private ImageView folkRhyme; // 民谣
    private ImageView hotAudio; // 热榜搜
    private ImageView imgAwait; // 敬请期待

    private boolean transform;

    private MusicApplication application;

    private MusicService.MusicBinder musicBinder;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case GET_DATA:
                    audioList = (List<AudioBean>)msg.obj;

//                    listView.invalidate();
                    setAdapter(audioList);
                    Log.d(TAG, SUB + "+++++++++++++++" +audioList.size());
                    break;

                default:
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_online_music, container,false);
        initComponents(view);

        return view;
    }


    @Override
    public void onDetach()
    {
        Log.d(TAG, SUB + "onDetach");
        super.onDetach();
    }

    private void initComponents(View view)
    {
        application = (MusicApplication) getActivity().getApplication();

        listView = (ListView) view.findViewById(R.id.list_top_music);
        listView.setOnItemClickListener(this);

        folkRhyme = (ImageView) view.findViewById(R.id.image_folk_rhyme);
        hotAudio = (ImageView) view.findViewById(R.id.image_top_list);
        imgAwait = (ImageView) view.findViewById(R.id.image_goon);
        folkRhyme.setOnClickListener(this);
        hotAudio.setOnClickListener(this);
        imgAwait.setOnClickListener(this);

        startThread(); // 开始线程
    }


    /**
     * 通过新开一个线程来加载网络数据
     */
    private void startThread()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                OnlineAudioUtil audioUtil = new OnlineAudioUtil();

                Message message = new Message();
                message.what = GET_DATA;

                if ( transform )
                {
                    message.obj = audioUtil.handleJsonData(Constant.HOT_AUDIO);
                }
                else
                {
                    message.obj = audioUtil.handleJsonData(Constant.FOLK_RHYME);
                }

                handler.sendMessage(message);
            }
        }).start();

        Log.d(TAG, SUB + "线程执行结束。");
    }

    private void setAdapter(List<AudioBean> list)
    {
        Log.d(TAG, SUB + list.size());

        topListAdapter = new TopListAdapter(getActivity(), list);
        listView.setAdapter(topListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String url = audioList.get(position).getUrl();
        Log.d(TAG, SUB + "onItemClick" + url);

        musicBinder = application.getMusicBinder();
        musicBinder.startPlay(url);
        Snackbar.make(view, "歌曲正在缓冲，请耐心等待！", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.image_folk_rhyme:
                transform = false;
                startThread();
                break;

            case R.id.image_top_list:
                transform = true;
                Snackbar.make(v, "正在加载数据，请稍后！", Snackbar.LENGTH_LONG).show();
                startThread();
                break;

            case R.id.image_goon:
                Snackbar.make(v, "精彩即将呈现，敬请期待", Snackbar.LENGTH_LONG).show();
                break;

            default:
                break;

        }
    }
}
