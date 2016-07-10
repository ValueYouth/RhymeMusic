package priv.valueyouth.rhymemusic.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 反序列化json字符串
 * Created by Idea on 2016/6/23.
 */
public class JsonBean
{
    private String showapi_res_code;  // 易源返回标志，0为成功，其他为失败。

    private String showapi_res_error; // 错误信息的展示

    private ShowResBody showapi_res_body; // 资源文件体

    private String ret_code; // ...

    public String getShowapi_res_code()
    {
        return showapi_res_code;
    }

    public void setShowapi_res_code(String showapi_res_code)
    {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error()
    {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error)
    {
        this.showapi_res_error = showapi_res_error;
    }

    public String getRet_code()
    {
        return ret_code;
    }

    public void setRet_code(String ret_code)
    {
        this.ret_code = ret_code;
    }

    public ShowResBody getShowapi_res_body()
    {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowResBody showapi_res_body)
    {
        this.showapi_res_body = showapi_res_body;
    }


    /**
     * 静态内部类
     */
    public static class ShowResBody
    {
        public String ret_code; // ...

        public PageBean pagebean; // ...


        public String getRet_code()
        {
            return ret_code;
        }

        public void setRet_code(String ret_code)
        {
            this.ret_code = ret_code;
        }

        public PageBean getPagebean()
        {
            return pagebean;
        }

        public void setPagebean(PageBean pagebean)
        {
            this.pagebean = pagebean;
        }


        /**
         * 内部类
         */
        public static class PageBean
        {
            public String totalpage; // 页数

            public String ret_code; // ...

            public List<AudioBean> songlist = new ArrayList<>(); // 音乐信息集合


            public String getTotalpage()
            {
                return totalpage;
            }

            public void setTotalpage(String totalpage)
            {
                this.totalpage = totalpage;
            }

            public String getRet_code()
            {
                return ret_code;
            }

            public void setRet_code(String ret_code)
            {
                this.ret_code = ret_code;
            }

            public List<AudioBean> getSonglist()
            {
                return songlist;
            }

            public void setSonglist(List<AudioBean> songlist)
            {
                this.songlist = songlist;
            }
        }
    }


}
