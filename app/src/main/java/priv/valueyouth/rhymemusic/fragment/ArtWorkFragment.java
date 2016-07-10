package priv.valueyouth.rhymemusic.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import priv.valueyouth.rhymemusic.R;
import priv.valueyouth.rhymemusic.activity.PlaybackActivity;
import priv.valueyouth.rhymemusic.application.MusicApplication;
import priv.valueyouth.rhymemusic.util.Audio;
import priv.valueyouth.rhymemusic.util.AudioUtil;
import priv.valueyouth.rhymemusic.util.ImageUtil;

/**
 * 歌曲专辑图片界面
 * Created by Idea on 2016/5/31.
 */
public class ArtWorkFragment extends Fragment
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[ArtWorkFragment]#";

    private Context context;

    private List<Audio> audioList; // 音乐列表

    private ImageView imageCover; // 封面图片

    private PlaybackActivity playbackActivity;
    private MusicApplication application;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        playbackActivity = (PlaybackActivity) getActivity();
        application = (MusicApplication) playbackActivity.getApplication();
        audioList = AudioUtil.getAudioList(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_artwork, null);

        imageCover = (ImageView) view.findViewById(R.id.image_cover);
        /*imageCover的点击事件*/
        imageCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                clickCover();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.d(TAG, SUB + "onActivityCreated");

        super.onActivityCreated(savedInstanceState);
        setArtWork();
    }

    /**
     * 设置专辑封面图片
     */
    public void setArtWork()
    {
        int currentMusic = application.getCurrentMusic();
        Bitmap bitmap = ImageUtil.getAlbumCover(context,
                audioList.get(currentMusic).getId());

        if ( bitmap != null )
        {
            Bitmap output = ImageUtil.getRoundCornerBitmap(bitmap, 30.0f);
            imageCover.setImageBitmap(output);
        }
        else
        {
            imageCover.setImageResource(R.drawable.defalt_artwork);
        }

    }

    /**
     * 封面点击事件
     */
    public void clickCover()
    {
        Log.d(TAG, SUB + "clickCover");

        DialogFragment dialogFragment = new OptionDialogFragment();
        FragmentManager manager = getFragmentManager();
        dialogFragment.show(manager, "dialog");
    }

}
