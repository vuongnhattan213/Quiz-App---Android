package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class ThemCauHoiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themcauhoi);

        Button buttonHoanTat = findViewById(R.id.button_hoantatthemcauhoi);
        buttonHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editCauHoi = findViewById(R.id.editCauHoi);
                String cauHoi = editCauHoi.getText().toString();

                EditText editLuaChon1 = findViewById(R.id.editLuaChon1);
                String luaChon1 = editLuaChon1.getText().toString();

                EditText editLuaChon2 = findViewById(R.id.editLuaChon2);
                String luaChon2 = editLuaChon2.getText().toString();

                EditText editLuaChon3 = findViewById(R.id.editLuaChon3);
                String luaChon3 = editLuaChon3.getText().toString();

                EditText editLuaChon4 = findViewById(R.id.editLuaChon4);
                String luaChon4 = editLuaChon4.getText().toString();

                EditText editDapAn = findViewById(R.id.editDapAn);
                String dapAn = editDapAn.getText().toString();

                EditText editIDMonHoc = findViewById(R.id.editIDMonHoc);
                String idMonHoc = editIDMonHoc.getText().toString();

                if (!cauHoi.isEmpty() && !luaChon1.isEmpty() && !luaChon2.isEmpty() && !luaChon3.isEmpty()
                        && !luaChon4.isEmpty() && !dapAn.isEmpty() && !idMonHoc.isEmpty()) {

                    JSONArray questions = readJsonFromFile("question.json");

                    JSONObject newQuestion = makeJSONObject(cauHoi, luaChon1, luaChon2, luaChon3, luaChon4, Integer.parseInt(dapAn), Integer.parseInt(idMonHoc));

                    questions.put(newQuestion);

                    saveJSONArrayToFile("question.json", questions);

                    Toast.makeText(getApplicationContext(), "THÊM CÂU HỎI THÀNH CÔNG", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(ThemCauHoiActivity.this, "VUI LÒNG NHẬP ĐẦY ĐỦ THÔNG TIN", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public JSONArray readJsonFromFile(String fileName) {
        JSONArray jsonArray = new JSONArray();
        try {
            File path = getApplicationContext().getFilesDir();
            File file = new File(path, fileName);
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String jsonStr = stringBuilder.toString();
                jsonArray = new JSONArray(jsonStr);
                bufferedReader.close();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void saveJSONArrayToFile(String fileName, JSONArray jsonArray) {
        try {
            File path = getApplicationContext().getFilesDir();
            File file = new File(path, fileName);
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonArray.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject makeJSONObject(String cauHoi, String luaChon1, String luaChon2, String luaChon3, String luaChon4, int dapAn, int idMonHoc) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("question", cauHoi);
            obj.put("option1", luaChon1);
            obj.put("option2", luaChon2);
            obj.put("option3", luaChon3);
            obj.put("option4", luaChon4);
            obj.put("answer", dapAn);
            obj.put("categoryID", idMonHoc);
        } catch (JSONException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return obj;
    }
}