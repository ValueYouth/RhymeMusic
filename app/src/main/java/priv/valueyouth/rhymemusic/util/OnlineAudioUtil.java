package priv.valueyouth.rhymemusic.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.show.api.ShowApiRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import priv.valueyouth.rhymemusic.bean.AudioBean;
import priv.valueyouth.rhymemusic.bean.JsonBean;

/**
 * 发送在线音乐请求，并解析出来，放在一个列表中。
 * Created by Idea on 2016/6/23.
 */
public class OnlineAudioUtil
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[OnlineAudioUtil]#";

    public static String jsonData = null;

    /**
     * 接收在线服务器发过来的数据。
     * @param id 榜单id
     */
    public List<AudioBean> handleJsonData(final String id)
    {
        Log.d(TAG, SUB + "startThread");

        List<AudioBean> audioBeen = new ArrayList<>();
        String appId = "19678";
        String secret = "c54679057f774fe395588c83a2b21aa7";
        String url = "http://route.showapi.com/213-4";
        ShowApiRequest request = new ShowApiRequest(url, appId,secret);


        request.addTextPara("topid", id); // 设置参数
        jsonData = request.post(); // 得到json数据

        Log.d(TAG, SUB + "123234324" +jsonData);

        Gson gson = new Gson();
        JsonBean jsonBean = gson.fromJson(jsonData, JsonBean.class);
        audioBeen = jsonBean.getShowapi_res_body().getPagebean().getSonglist();

        Log.d(TAG, SUB + audioBeen.size());
        return audioBeen;
    }


}
