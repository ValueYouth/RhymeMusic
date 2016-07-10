package priv.valueyouth.rhymemusic.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import priv.valueyouth.rhymemusic.R;

public class WelcomeActivity extends AppCompatActivity
{
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();  // 隐藏掉标题栏
        setContentView(R.layout.activity_welcome);

        handler.postDelayed(new Runnable() {

            @Override
            public void run()
            {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
