package priv.valueyouth.rhymemusic.adapter;

/**
 * Tab页面的适配器
 * Created by Idea on 2016/5/3.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import priv.valueyouth.rhymemusic.fragment.LocalMusicFragment;
import priv.valueyouth.rhymemusic.fragment.OnlineMusicFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    private static final int NUM_ITEMS = 2;

    private List<Fragment> fragments = new ArrayList<Fragment>();

    public SectionsPagerAdapter(FragmentManager fm)
    {
        super(fm);
        fragments.add(new LocalMusicFragment());
        fragments.add(new OnlineMusicFragment());

    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "本地音乐";
            case 1:
                return "在线音乐";
        }
        return null;
    }
}
