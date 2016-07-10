package priv.valueyouth.rhymemusic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import priv.valueyouth.rhymemusic.help.ActivityCollector;

/**
 * 所有活动的基类
 * Created by Idea on 2016/6/21.
 */
public class BaseActivity extends AppCompatActivity
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[BaseActivity]#";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        Log.d(TAG, SUB + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
