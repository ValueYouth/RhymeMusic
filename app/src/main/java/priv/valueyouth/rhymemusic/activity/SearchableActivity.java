package priv.valueyouth.rhymemusic.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.util.Audio;
import priv.valueyouth.rhymemusic.util.AudioUtil;

public class SearchableActivity extends BaseActivity
        implements View.OnClickListener, SearchView.OnQueryTextListener,
        AdapterView.OnItemClickListener
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[SearchableActivity]#";

    private ListView listView;
    private ImageButton searchBack;
    private ImageButton searchOption;
    private SearchView searchView;
    private TextView textTips;

    private List<String> stringList;

    private MusicApplication application;

    private int currentMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();  // 隐藏掉标题栏
        setContentView(R.layout.activity_searchable);

        initComponents();

        /*获得Intent对象，核实action后，获取查询结果*/
        Intent intent = getIntent();
        if ( Intent.ACTION_SEARCH.equals(intent.getAction()) )
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }


    }

    @Override
    public void onClick(View v)
    {
        switch ( v.getId() )
        {
            case R.id.image_search_back:
                finish();
                break;

            case R.id.image_search_option:

                break;

            default:
                break;
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        if ( newText.length() != 0 )
        {
            currentMusic = getCurrentMusic(newText);

            listView.setFilterText(newText);
            listView.dispatchDisplayHint(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
        else
        {
            listView.clearTextFilter();
            listView.setVisibility(View.INVISIBLE);
        }

        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Log.d(TAG, SUB + "position" + position);
        application.getMusicBinder().startPlay(currentMusic, 0);

        Intent intent = new Intent(SearchableActivity.this, PlaybackActivity.class);
        startActivity(intent);
    }

    private void initComponents()
    {
        application = (MusicApplication) getApplication();

        /*搜索结果为空时，显示的提示信息*/
        textTips = (TextView) findViewById(R.id.text_tips);

        /*按钮类组件初始化*/
        searchBack = (ImageButton) findViewById(R.id.image_search_back);
        searchOption = (ImageButton) findViewById(R.id.image_search_option);
        searchBack.setOnClickListener(this);
        searchOption.setOnClickListener(this);

        /*列表组件初始化*/
        listView = (ListView) findViewById(R.id.list_search_result);
        listView.setOnItemClickListener(this);
        listView.setVisibility(View.INVISIBLE);
        loadData();

        /*搜索组件初始化*/
        searchView = (SearchView) findViewById(R.id.view_search);
        searchView.setOnQueryTextListener(this);
        searchView.onActionViewExpanded(); // 默认出现点击输入状态
        searchView.setFocusable(false);
        searchView.clearFocus();
//        searchView.setSubmitButtonEnabled(true);

    }

    /**
     * 加载数据
     */
    private void loadData()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, translationData());

        if ( 0 == stringList.size() )
        {
            String tips = "暂无数据";
            textTips.setText(tips);
        }

        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
    }

    /**
     * 转化原始数据，将歌曲名和歌手提取出来
     * @return
     */
    private List<String> translationData()
    {
        List<Audio> list = AudioUtil.getAudioList(this);
        stringList = new ArrayList<>();

        for ( Audio item : list )
        {
            String title  = item.getTitle();
            String artist = item.getArtist();
            String combine = artist + "——" + title;

            stringList.add(combine);
        }

        return stringList;
    }

    /**
     * 获取通过搜索得到的音乐索引
     * @param musicInfo 输入的关键字
     * @return 关键字所得音乐索引
     */
    private int getCurrentMusic(String musicInfo)
    {
        Log.d(TAG, SUB + "getCurrentMusic" + musicInfo);
        int currentMusic = 0;

        for ( int i = 0; i < stringList.size(); i++ )
        {
            if ( stringList.get(i).contains(musicInfo) )
            {
                currentMusic = i;
            }
        }

        Log.d(TAG, SUB + "getCurrentMusic" + currentMusic);
        return currentMusic;
    }
}
