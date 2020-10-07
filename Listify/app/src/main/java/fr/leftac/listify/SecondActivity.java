package fr.leftac.listify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {
    @BindView(R.id.textView) TextView tv;

    @OnClick(R.id.helloButton)
    public void hello() {
        tv.setText("Salut les amis");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}