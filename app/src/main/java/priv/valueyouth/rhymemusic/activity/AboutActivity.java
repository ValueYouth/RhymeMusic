package priv.valueyouth.rhymemusic.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import priv.valueyouth.rhymemusic.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageButton buttonBack;

    private ImageButton buttonOption;

    private TextView textTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();  // 隐藏掉标题栏
        setContentView(R.layout.activity_about);

        textTitle = (TextView) findViewById(R.id.title_text);
        textTitle.setText("关于应用");

        buttonBack = (ImageButton) findViewById(R.id.title_back);
        buttonOption = (ImageButton) findViewById(R.id.title_option);
        buttonBack.setOnClickListener(this);
        buttonOption.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.title_back:
                finish();
                break;

            case R.id.title_option:
                Snackbar.make(v, "韵，发现音乐的乐趣！", Snackbar.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
