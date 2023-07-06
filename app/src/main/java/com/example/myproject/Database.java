package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myproject.model.Category;
import com.example.myproject.model.Question;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Question.db";
    private static final int VERTION = 1;
    private SQLiteDatabase db;
    private Context context;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERTION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;

        final String CATEGORIES_TABLE = "CREATE TABLE " +
                Table.CategoriesTable.TABLE_NAME + "( " +
                Table.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String QUESTIONS_TABLE = "CREATE TABLE " +
                Table.QuestionsTable.TABLE_NAME + " ( " +
                Table.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                Table.QuestionsTable.COLUMN_ANSWER + " INTEGER, " +
                Table.QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + Table.QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                Table.CategoriesTable.TABLE_NAME + "(" + Table.CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(CATEGORIES_TABLE);
        db.execSQL(QUESTIONS_TABLE);

        CreateCategories();
        CreateQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+Table.CategoriesTable.COLUMN_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Table.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void insertCategories(Category category) {
        ContentValues values = new ContentValues();
        values.put(Table.CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(Table.CategoriesTable.TABLE_NAME, null, values);
    }

    private void CreateCategories() {
        // môn Software Engineer có ID = 1
        Category c1 = new Category("Software Engineer");
        insertCategories(c1);
        // môn Cơ sở dữ liệu có ID = 2
        Category c2 = new Category("Cơ sở dữ liệu");
        insertCategories(c2);
        // môn Phát triển web có ID = 3
        Category c3 = new Category("Phát triển web");
        insertCategories(c3);
    }

    private void insertQuestion(Question question) {
        ContentValues values = new ContentValues();

        values.put(Table.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        values.put(Table.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        values.put(Table.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        values.put(Table.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        values.put(Table.QuestionsTable.COLUMN_OPTION4, question.getOption4());
        values.put(Table.QuestionsTable.COLUMN_ANSWER, question.getAnswer());
        values.put(Table.QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());

        db.insert(Table.QuestionsTable.TABLE_NAME, null, values);
    }

    public String getJson(String fileName){
        String json = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception ex){
            Log.e("TAG", "Load json error: " + ex.getMessage());
        }
        return json;
    }

    public ArrayList convertJsonToQuestions(String json){
        ArrayList questionList = new ArrayList<Question>();
        try {
            JSONArray jsonArr = new JSONArray(json);
            int size = jsonArr.length();
            for (int i = 0; i < size; i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                Question question = new Question();
                question.setQuestion(jsonObject.getString("question"));
                question.setOption1(jsonObject.getString("option1"));
                question.setOption2(jsonObject.getString("option2"));
                question.setOption3(jsonObject.getString("option3"));
                question.setOption4(jsonObject.getString("option4"));
                question.setAnswer(jsonObject.getInt("answer"));
                question.setCategoryID(jsonObject.getInt("categoryID"));
                questionList.add(question);
            }
        } catch (Exception ex){
            Log.e("TAG", "Load json error: " + ex.getMessage());
        }
        return questionList;
    }

    private void CreateQuestions() {
        String json = getJson("questions.json");
        ArrayList<Question> questions = convertJsonToQuestions(json);

        for (int i = 0 ; i < questions.size(); i++){
            insertQuestion(questions.get(i));
        }
    }

    public List<Category> getDataCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+Table.CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(Table.CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(Table.CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            }
            while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }

    public ArrayList<Question> getQuestions(int categoryID) {
        ArrayList<Question> questionArrayList= new ArrayList<>();

        db = getReadableDatabase();

        String selection = Table.QuestionsTable.COLUMN_CATEGORY_ID + " = ? ";

        String[] selectionArgs = new String[]{String.valueOf(categoryID)};

        Cursor c = db.query(Table.QuestionsTable.TABLE_NAME,
                null, selection, selectionArgs, null, null, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();

                question.setId(c.getInt(c.getColumnIndex(Table.QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(Table.QuestionsTable.COLUMN_OPTION4)));
                question.setAnswer(c.getInt(c.getColumnIndex(Table.QuestionsTable.COLUMN_ANSWER)));
                question.setCategoryID(c.getInt(c.getColumnIndex(Table.QuestionsTable.COLUMN_CATEGORY_ID)));

                questionArrayList.add(question);
            }
            while (c.moveToNext());
        }
        c.close();
        return questionArrayList;
    }
}