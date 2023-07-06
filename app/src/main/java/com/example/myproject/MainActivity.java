package com.example.myproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.media.MediaPlayer;

import com.example.myproject.model.Category;

import java.util.List;

import android.widget.LinearLayout;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private LinearLayout buttonContainer;
    private Button showButton;
    private Button button_gioithieumon;
    private Button button_themcauhoi;
    Switch button_toggle;
    MediaPlayer player_sound = null;
    MediaPlayer player_music = null;

    ActivityResultLauncher<String> selectCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player_music = MediaPlayer.create(this, R.raw.backgroundmusic1);
        player_sound = MediaPlayer.create(this, R.raw.buttoneffect2);

        button_gioithieumon = findViewById(R.id.button_gioithieumon);
        button_gioithieumon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GioiThieuMonActivity.class);
                startActivity(intent);
            }
        });

        button_themcauhoi = findViewById(R.id.button_themcauhoi);
        button_themcauhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThemCauHoiActivity.class);
                startActivity(intent);
            }
        });

        buttonContainer = findViewById(R.id.buttonContainer);
        showButton = findViewById(R.id.showButton);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonContainer.getVisibility() == View.VISIBLE) {
                    buttonContainer.setVisibility(View.GONE);
                } else {
                    buttonContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        button_toggle = findViewById(R.id.button_toggle);
        button_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.musicBackground = button_toggle.isChecked();
                if (Settings.musicBackground){
                    startMusic(player_music);
                }else {
                    stopMusic(player_music);
                }
            }
        });
    }

    public void startMusic(MediaPlayer player){
        player.setLooping(true);
        player.start();
    }

    public void stopMusic(MediaPlayer player){
        player.stop();
        player.prepareAsync();
    }
}