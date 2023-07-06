package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TongKetActivity extends AppCompatActivity {
    private Button button_finish;
    private TextView textViewScore;
    private Button button_restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongket);

        button_finish = findViewById(R.id.button_finish);
        textViewScore = findViewById(R.id.textView_score);
        button_restart = findViewById(R.id.button_restart);
        TextView textViewCorrect = findViewById(R.id.textView_correct);
        TextView textViewWrong = findViewById(R.id.textView_wrong);
        TextView textViewOutOfTime = findViewById(R.id.textView_out_of_time);
        TextView textViewSkip = findViewById(R.id.textView_skip);

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TongKetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        button_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TongKetActivity.this, GioiThieuMonActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        int numCorrect = intent.getIntExtra("numCorrect", 0);
        int numWrong = intent.getIntExtra("numWrong", 0);
        int numOutOfTime = intent.getIntExtra("numOutOfTime", 0);
        int numSkip = intent.getIntExtra("numSkip", 0);

        textViewScore.setText("Tổng điểm: " + score);
        textViewCorrect.setText("Số câu đúng: " + numCorrect);
        textViewWrong.setText("Số câu sai: " + numWrong);
        textViewOutOfTime.setText("Số câu hết giờ: " + numOutOfTime);
        textViewSkip.setText("Số câu bỏ qua: " + numSkip);
    }
}