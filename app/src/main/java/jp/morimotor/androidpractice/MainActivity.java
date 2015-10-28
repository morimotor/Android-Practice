package jp.morimotor.androidpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.button:
                intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;

            case R.id.button2:
                intent = new Intent(this, AccelerationActivity.class);
                startActivity(intent);
                break;

            case R.id.button3:
                intent = new Intent(this, ManpokeiActivity.class);
                startActivity(intent);
                break;

            case R.id.button4:
                intent = new Intent(this, GoogleFitActivity.class);
                startActivity(intent);
                break;
        }

    }
}
