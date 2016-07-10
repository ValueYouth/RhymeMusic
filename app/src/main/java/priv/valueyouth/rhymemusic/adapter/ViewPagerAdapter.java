package priv.valueyouth.rhymemusic.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import priv.valueyouth.rhymemusic.fragment.ArtWorkFragment;
import priv.valueyouth.rhymemusic.fragment.LyricFragment;

/**
 * 这个滑动切换页面指的是在PlaybackActivity中，歌曲的专辑图片与
 * 该歌曲的歌词切换。
 * Created by Idea on 2016/5/31.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private static final int NUM_ITEMS = 2;

    private List<Fragment> fragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);

        fragments.add(new ArtWorkFragment());
        fragments.add(new LyricFragment());
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
}
