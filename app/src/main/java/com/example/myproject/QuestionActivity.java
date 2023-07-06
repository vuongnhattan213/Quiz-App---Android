package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.widget.EditText;

public class QuestionActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCategory;
    private TextView textViewCountDown;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private CountDownTimer countDownTimer;
    private ArrayList<Question> questionList;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionSize;

    private int Score;
    private boolean answered;
    private Question currentQuestion;
    private int count = 0;
    private int numCorrect = 0;
    private int numWrong = 0;
    private int numOutOfTime = 0;
    private int numSkip = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        anhxa();

        Intent intent = getIntent();
        int categoryID = intent.getIntExtra("idcategories", 0);
        String categoryName = intent.getStringExtra("categoriesname");

        textViewCategory.setText("Chủ đề: "+categoryName);

        Database database = new Database(this);

        //Danh sách chứa câu hỏi
        questionList = database.getQuestions(categoryID);

        questionSize = questionList.size();

        //Random các câu hỏi
        Collections.shuffle(questionList);

        //Show câu hỏi và đáp án
        showNextQuestion();

        final MediaPlayer mediaPlayerSound = MediaPlayer.create(this, R.raw.buttoneffect2);

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerSound.start();

                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                        showNextQuestion();
                    }
                    else {
                        numSkip++;
                        checkAnswer();
                        showNextQuestion();
                    }
                }
            }
        });
    }

    private void showNextQuestion() {
        rbGroup.clearCheck();

        if (questionCounter < questionSize) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());

            if (currentQuestion.getOption3().isEmpty() && currentQuestion.getOption4().isEmpty()) {
                rb3.setVisibility(View.GONE);
                rb4.setVisibility(View.GONE);
            } else {
                rb3.setVisibility(View.VISIBLE);
                rb4.setVisibility(View.VISIBLE);
                rb3.setText(currentQuestion.getOption3());
                rb4.setText(currentQuestion.getOption4());
            }

            questionCounter++;

            textViewQuestionCount.setText("Câu hỏi : "+questionCounter+" / "+questionSize);

            answered = false;

            buttonConfirmNext.setText("Câu tiếp");

            timeLeftInMillis = 20000;

            startCountDown();
        } else {
            buttonConfirmNext.setText("Hoàn thành");
            finishQuestion();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;

                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();

                checkAnswer();
                showNextQuestion();
            }
        }.start();
    }

    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());

        int answer = rbGroup.indexOfChild(rbSelected) + 1;

        if (answer == currentQuestion.getAnswer()) {
            numCorrect++;
            Score = Score + 10;

            textViewScore.setText("Điểm hiện tại : " + Score);
        }

        if (answer != currentQuestion.getAnswer()) {
            numWrong++;
        }

        if (timeLeftInMillis == 0) {
            numOutOfTime++;
        }

        //showSolution();
        countDownTimer.cancel();
    }

/*    private void showSolution() {
        rb1.setTextColor(Color.BLUE);
        rb2.setTextColor(Color.BLUE);
        rb3.setTextColor(Color.BLUE);
        rb4.setTextColor(Color.BLUE);

        if (questionCounter < questionSize) {
            buttonConfirmNext.setText("Câu tiếp");
        }
        else {
            buttonConfirmNext.setText("Hoàn thành");
        }
        countDownTimer.cancel();
    }*/

    private void updateCountDownText() {
        int minutes = (int) ((timeLeftInMillis/1000)/60);

        int seconds = (int) ((timeLeftInMillis/1000)%60);

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        }
        else {
            textViewCountDown.setTextColor(Color.BLACK);
        }
    }

    private void finishQuestion() {
        Intent intent = new Intent(this, TongKetActivity.class);
        intent.putExtra("score", Score);
        intent.putExtra("numCorrect", numCorrect);
        intent.putExtra("numWrong", numWrong);
        intent.putExtra("numOutOfTime", numOutOfTime);
        intent.putExtra("numSkip", numSkip);
        startActivity(intent);

        finish();
    }

    @Override
    public void onBackPressed() {
        count++;
        if (count>=1) {
            finishQuestion();
        }
        count=0;
    }

    private void anhxa() {
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCategory = findViewById(R.id.text_view_category);

        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);

        buttonConfirmNext = findViewById(R.id.button_confirm_next);
    }
}