package com.example.myproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myproject.model.Category;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class GioiThieuMonActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button buttonStartQuestion;
    private static final int REQUEST_CODE_QUESTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioithieumon);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        buttonStartQuestion = findViewById(R.id.button_start_question);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new Fragment1(), "Software Engineer");
        adapter.addFragment(new Fragment2(), "Cơ sở dữ liệu");
        adapter.addFragment(new Fragment3(), "Phát triển web");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        buttonStartQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuestion();
            }
        });
    }

    private void startQuestion() {
        int currentTabPosition = viewPager.getCurrentItem();
        List<Category> categoryList = new Database(this).getDataCategories();
        Category category = categoryList.get(currentTabPosition);
        int categoryID = categoryList.get(currentTabPosition).getId();
        String categoryName = category.getName();

        Intent intent = new Intent(GioiThieuMonActivity.this, QuestionActivity.class);

        intent.putExtra("idcategories", categoryID);
        intent.putExtra("categoriesname", categoryName);

        startActivityForResult(intent, REQUEST_CODE_QUESTION);
    }
}