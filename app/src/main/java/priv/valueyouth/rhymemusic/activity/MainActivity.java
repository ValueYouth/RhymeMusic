package priv.valueyouth.rhymemusic.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.adapter.SectionsPagerAdapter;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.fragment.ASDialogFragment;
import priv.valueyouth.rhymemusic.help.ActivityCollector;
import priv.valueyouth.rhymemusic.service.MusicService;
import priv.valueyouth.rhymemusic.util.Audio;
import priv.valueyouth.rhymemusic.util.AudioUtil;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[MainActivity]#";

    private MusicApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, SUB + "onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MusicApplication) getApplication();

        initComponents(); // 初始化各种组件
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }


    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                startActivity(intent);
                break;

            case R.id.action_stop:
                MediaPlayer mediaPlayer = application.getMediaPlayer();

                if ( mediaPlayer.isPlaying() )
                {
                    mediaPlayer.stop();

                    String tips = "播放停止了。";
                    Toast.makeText(MainActivity.this, tips, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String tips = "别点了，现在没有歌曲在播放。";
                    Toast.makeText(MainActivity.this, tips, Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.action_help:
                Intent intent1 = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent1);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case R.id.nav_search:
                Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_play_mode:
                MusicService.MusicBinder musicBinder= application.getMusicBinder();
                musicBinder.changePlayMode();

                String string = application.getMusicBinder().getCurrentMode();
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();

                break;

            case R.id.nav_read_audio:
                String str = "因技术原因，该功能暂未实现。";
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_auto_stop:
                DialogFragment dialogFragment1 = new ASDialogFragment();
                FragmentManager manager1 = getSupportFragmentManager();
                dialogFragment1.show(manager1, "dialog audio info");
                break;

            case R.id.nav_night_mode:
                if ( application.isNightMode() )
                {
                    Log.d(TAG, SUB + "夜间模式");
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();

                    application.setNightMode(false);
                }
                else
                {
                    Log.d(TAG, SUB + "日间模式");
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();

                    application.setNightMode(true);
                }

                break;

            case R.id.nav_about:
                Intent inAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(inAbout);
                break;

            case R.id.nav_quit:
                application.unBindService();

                ActivityCollector.finishAll();
                System.exit(0);

                break;

            default:
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        switch ( v.getId() )
        {
            case R.id.fab:
                Intent intent = new Intent(MainActivity.this, PlaybackActivity.class);

                if ( application.getMediaPlayer().isPlaying() && !application.isOnline())
                {
                    startActivity(intent);
                }
                else if (application.isOnline())
                {
                    Snackbar.make(v, "在线功能还在完善中...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                {
                    Snackbar.make(v, "请选择一首歌曲播放", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 点击播放全部
     */
    public void playAllSongs(View view)
    {
        Log.d(TAG, SUB + "playAllSongs");
        application.getMusicBinder().startPlay(0, 0);

        Audio audio = AudioUtil.getAudioList(this).get(application.getCurrentMusic());

        String tips = "正在播放: " + audio.getTitle() + " — " + audio.getArtist();

        Snackbar.make(view, tips, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * 初始化组件
     */
    private void initComponents()
    {
        initCommon();
    }

    /**
     * 初始化通用的组件
     */
    private void initCommon()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /*为application中的Fragment赋值*/
//        LocalMusicFragment fragment = (LocalMusicFragment)
//                mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem() + 1);
//        application.setLocalMusicFragment(fragment);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


}
