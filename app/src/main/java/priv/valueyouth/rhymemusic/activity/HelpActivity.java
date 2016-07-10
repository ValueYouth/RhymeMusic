package priv.valueyouth.rhymemusic.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import priv.valueyouth.rhymemusic.R;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView textView;

    private ImageButton back;

    private ImageButton option;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();  // 隐藏掉标题栏
        setContentView(R.layout.activity_help);

        textView = (TextView) findViewById(R.id.title_text);
        textView.setText("帮助");

        back = (ImageButton) findViewById(R.id.title_back);
        option = (ImageButton) findViewById(R.id.title_option);
        back.setOnClickListener(this);
        option.setOnClickListener(this);
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
                Snackbar.make(v, "希望能够帮到你。", Snackbar.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
