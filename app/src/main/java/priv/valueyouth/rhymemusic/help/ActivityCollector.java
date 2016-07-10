package priv.valueyouth.rhymemusic.help;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置专门的集合类，对所有的活动进行管理。
 * Created by Idea on 2016/6/21.
 */
public class ActivityCollector
{
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }

    public static void finishAll()
    {
        for ( Activity activity : activities )
        {
            activity.finish();
        }
    }
}
