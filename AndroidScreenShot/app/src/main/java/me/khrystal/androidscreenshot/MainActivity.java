package me.khrystal.androidscreenshot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.khrystal.system.screenshot.ScreenShotActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void onClickShot(View view) {
        //1.直接启动
        startActivity(new Intent(this, ScreenShotActivity.class));
        //2.通过service启动
//        startService(new Intent(this, ScreenShotService.class));
    }
}
