package priv.valueyouth.rhymemusic.util;

import android.widget.ImageView;

/**
 * 九宫格显示播放详情界面
 * 点击专辑图片后出现的选项
 * Created by Idea on 2016/6/7.
 */
public class OptionGrid
{
    private int imageID;  // 显示的图片
    private String text; // 显示的文字

    /**
     * 构造方法，初始化imageID和text
     * @param imageID 要加载的图片
     * @param text 与图片匹配的文字
     */
    public OptionGrid(int imageID, String text)
    {
        this.imageID = imageID;
        this.text = text;
    }

    public int getImageID()
    {
        return imageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
