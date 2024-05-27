package ntu.edu.quizzhappyapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

import ntu.edu.quizzhappyapp.Models.Questions;
import ntu.edu.quizzhappyapp.Models.Result;
import ntu.edu.quizzhappyapp.Models.TypeQues;

public class QuizDBHelper extends SQLiteOpenHelper  {
    private static final String DATABASE_NAME = "QuizDB.db"; //Tên database
    private static final int DATABASE_VERSION = 1;//phiên bản

    //Tên bảng và cột của người dùng
    private static final String TABLE_USERS = "USERS";
    private static final String COLUMN_USER_ID = "userID";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_AVATAR = "avatar";

    //Tên bảng và cột của loại câu hỏi
    private static final String TABLE_TYPEQUES = "TYPEQUES";
    private static final String COLUMN_TYPEQUES_ID = "typeID";
    private static final String COLUMN_TYPEQUES_NAME = "nameType";
    private static final String COLUMN_TYPEQUES_IMAGE = "image";

    //Tên bảng và cột của câu hỏi
    private static final String TABLE_QUESTION = "QUESTION";
    private static final String COLUMN_QUESTION_ID = "quesID";
    private static final String COLUMN_TYPEQUESTION_ID = "typeID";
    private static final String COLUMN_QUESTION_QUES = "question";
    private static final String COLUMN_QUESTION_OPTION1 = "option1";
    private static final String COLUMN_QUESTION_OPTION2 = "option2";
    private static final String COLUMN_QUESTION_OPTION3 = "option3";
    private static final String COLUMN_QUESTION_OPTION4 = "option4";
    private static final String COLUMN_QUESTION_OPTION_CORRECT = "option_correct";

    //Tên bảng và cột của kết quả
    private static final String TABLE_RESULTS = "RESULTS";
    private static final String COLUMN_RESULT_ID = "resultID";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_TYPEQUESID = "typeQuesID";

    // Câu lệnh tạo bảng cho bảng người dùng
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_AVATAR + "TEXT);";

    //Câu lệnh tạo bảng cho loại câu hỏi
    private static final String CREATE_TABLE_TYPEQUES =
            "CREATE TABLE " + TABLE_TYPEQUES + " (" +
                    COLUMN_TYPEQUES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TYPEQUES_NAME + " TEXT, " +
                    COLUMN_TYPEQUES_IMAGE + " TEXT);";

    //Câu lệnh tạo bảng cho câu hỏi
    private static final String CREATE_TABLE_QUESTION =
            "CREATE TABLE " + TABLE_QUESTION + " (" +
                    COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUESTION_QUES + " TEXT, " +
                    COLUMN_QUESTION_OPTION1 + " TEXT, " +
                    COLUMN_QUESTION_OPTION2 + " TEXT, " +
                    COLUMN_QUESTION_OPTION3 + " TEXT, " +
                    COLUMN_QUESTION_OPTION4 + " TEXT, " +
                    COLUMN_QUESTION_OPTION_CORRECT + " INTEGER, "+
                    COLUMN_TYPEQUESTION_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_TYPEQUESTION_ID + ") REFERENCES " + //Tạo khóa ngoại đến bảng loại câu hỏi
                    TABLE_TYPEQUES + "(" + COLUMN_TYPEQUES_ID + "))";

    //Câu lệnh tạo bảng cho kết quả
    private static final String CREATE_TABLE_RESULT =
            "CREATE TABLE " + TABLE_RESULTS + " (" +
                    COLUMN_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_SCORE + " INTEGER, " +
                    COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";


    public QuizDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TYPEQUES);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPEQUES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        onCreate(db);
    }

    public boolean insertDataSignUp(String username, String password, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME,username);
        values.put(COLUMN_PASSWORD,password);
        values.put(COLUMN_EMAIL,email);

        long result = db.insert(TABLE_USERS,null,values);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from USERS where username=?", new String[]{username});

        if (cursor.getCount()>0){
            cursor.close();
            return true;
        }else {
            cursor.close();
            return false;
        }
    }

    public boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from USERS where username=? and password=?", new String[]{username,password});

        if (cursor.getCount()>0){
            // Đóng cursor để giải phóng tài nguyên
            cursor.close();
            return true;
        }else {
            // Đóng cursor để giải phóng tài nguyên
            cursor.close();
            return false;
        }
    }

    public int getID(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USERS WHERE username=? AND password=?", new String[]{username, password});
        int userID = 0;

        if (cursor != null && cursor.moveToFirst()) {

            int columnIndex = cursor.getColumnIndex(COLUMN_USER_ID);
            userID = cursor.getInt(columnIndex);
            cursor.close();
            db.close();
            return userID;
        }

        db.close();
        return -1;
    }

    public String getUsername(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = null;

        try {
            // Sử dụng câu truy vấn SQL để lấy thông tin của người dùng dựa trên ID
            Cursor cursor = db.rawQuery("SELECT " + COLUMN_USERNAME + " FROM USERS WHERE userID = ?", new String[]{String.valueOf(ID)});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị username từ Cursor
                int user = cursor.getColumnIndex(COLUMN_USERNAME);
                username = cursor.getString(user);
                cursor.close();
                db.close();
                return username;
            }
        } catch (SQLiteException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra khi thực hiện truy vấn SQL
            Log.e("SQLiteException", "Error executing SQL query: " + e.getMessage());
        } finally {
            db.close();
        }

        db.close();
        return null;
    }

    public String getEmail(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String email = null;

        try {
            // Sử dụng câu truy vấn SQL để lấy thông tin của người dùng dựa trên ID
            Cursor cursor = db.rawQuery("SELECT " + COLUMN_EMAIL + " FROM USERS WHERE userID = ?", new String[]{String.valueOf(ID)});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị email từ Cursor
                int user = cursor.getColumnIndex(COLUMN_EMAIL);
                email = cursor.getString(user);
                cursor.close();
                db.close();
                return email;
            }
        } catch (SQLiteException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra khi thực hiện truy vấn SQL
            Log.e("SQLiteException", "Error executing SQL query: " + e.getMessage());
        } finally {
            db.close();
        }

        db.close();
        return null;
    }
    public String getPassword(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String password = null;

        try {
            // Sử dụng câu truy vấn SQL để lấy thông tin của người dùng dựa trên ID
            Cursor cursor = db.rawQuery("SELECT " + COLUMN_PASSWORD + " FROM USERS WHERE userID = ?", new String[]{String.valueOf(ID)});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị password từ Cursor
                int user = cursor.getColumnIndex(COLUMN_PASSWORD);
                password = cursor.getString(user);
                cursor.close();
                db.close();
                return password;
            }
        } catch (SQLiteException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra khi thực hiện truy vấn SQL
            Log.e("SQLiteException", "Error executing SQL query: " + e.getMessage());
        } finally {
            db.close();
        }

        db.close();
        return null;
    }

    public Boolean editInfo(int ID, String type, String newValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        long result=0;
        switch (type){
            case "username":{
                values = new ContentValues();
                values.put(COLUMN_USERNAME, newValue);
                result = db.update(TABLE_USERS, values, "userID = ?", new String[]{String.valueOf(ID)});
                break;
            }
            case "password":{
                values = new ContentValues();
                values.put(COLUMN_PASSWORD, newValue);
                result = db.update(TABLE_USERS, values, "userID = ?", new String[]{String.valueOf(ID)});
                break;
            }
            case "email":{
                values = new ContentValues();
                values.put(COLUMN_EMAIL, newValue);
                result = db.update(TABLE_USERS, values, "userID = ?", new String[]{String.valueOf(ID)});
                break;
            }
        }

        if(result == -1){
            db.close();
            return false;
        }else {
            db.close();
            return true;
        }
    }

    public boolean updateImagerUser(int ID, String img){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_AVATAR,img);
        long result = db.update(TABLE_USERS,values,"userID = ?", new String[]{String.valueOf(ID)});

        if(result == -1){
            db.close();
            return false;
        }else {
            db.close();
            return true;
        }
    }

    public String getImg(int ID){
        SQLiteDatabase db = this.getReadableDatabase();
        String avatar = null;

        try {
            // Sử dụng câu truy vấn SQL để lấy thông tin của người dùng dựa trên ID
            Cursor cursor = db.rawQuery("SELECT " + COLUMN_AVATAR + " FROM USERS WHERE userID = ?", new String[]{String.valueOf(ID)});

            if (cursor != null && cursor.moveToFirst()) {
                int user = cursor.getColumnIndex(COLUMN_AVATAR);
                avatar = cursor.getString(user);
                cursor.close();
                db.close();
                return avatar;
            }
        } catch (SQLiteException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra khi thực hiện truy vấn SQL
            Log.e("SQLiteException", "Error executing SQL query: " + e.getMessage());
        } finally {
            db.close();
        }

        db.close();
        return null;
    }

    public ArrayList<TypeQues> loadTypeQuesFromDatabase() {
        ArrayList<TypeQues> ds = new ArrayList<>();
        SQLiteDatabase dbReadable = this.getReadableDatabase();
        String sqlSelect = "SELECT * FROM TYPEQUES;";
        Cursor cs = dbReadable.rawQuery(sqlSelect, null);

        if (cs.moveToFirst()) {
            do {
                int ID = cs.getInt(0);
                String nameType = cs.getString(1);
                String img = cs.getString(2);
                TypeQues b = new TypeQues(ID, nameType, img);
                ds.add(b);
            } while (cs.moveToNext());
        }
        cs.close();
        return ds;
    }

    public int getTypeID(int pos){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +  TABLE_TYPEQUES + " WHERE typeID = ?", new String[]{String.valueOf(pos)});
        int typeID = 0;

        if (cursor != null && cursor.moveToFirst()) {

            int columnIndex = cursor.getColumnIndex(COLUMN_TYPEQUES_ID);
            typeID = cursor.getInt(columnIndex);
            cursor.close();
            db.close();
            return typeID;
        }
        db.close();
        return -1;
    }

    public String getTypeName(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = null;

        try {
            // Sử dụng câu truy vấn SQL để lấy thông tin của người dùng dựa trên ID
            Cursor cursor = db.rawQuery("SELECT * FROM TYPEQUES WHERE typeID = ?", new String[]{String.valueOf(ID)});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị username từ Cursor
                int pos = cursor.getColumnIndex(COLUMN_TYPEQUES_NAME);
                name = cursor.getString(pos);
                cursor.close();
                return name;
            }
        } catch (SQLiteException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra khi thực hiện truy vấn SQL
            Log.e("SQLiteException", "Error executing SQL query: " + e.getMessage());
        } finally {
            db.close();
        }

        db.close();
        return null;
    }

    public ArrayList<Questions> loadQuestion(int typeID){
        ArrayList<Questions> ds = new ArrayList<>();
        SQLiteDatabase dbReadable = this.getReadableDatabase();
        String sqlSelect = "SELECT * FROM QUESTION WHERE typeID=? ;";
        Cursor cs = dbReadable.rawQuery(sqlSelect,  new String[]{String.valueOf(typeID)});

        if (cs.moveToFirst()) {
            do {
                int ID = cs.getInt(0);
                String question = cs.getString(1);
                String option1 = cs.getString(2);
                String option2 = cs.getString(3);
                String option3 = cs.getString(4);
                String option4 = cs.getString(5);
                int quesTypeID = cs.getInt(7);
                int optionCorrect = cs.getInt(6);
                Questions b = new Questions(ID, question, quesTypeID,option1,option2,option3,option4,optionCorrect);
                ds.add(b);
            } while (cs.moveToNext());
        }
        cs.close();
        return ds;
    }

    public boolean insertResult(int score, String time , int typeID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_SCORE,score);
        values.put(COLUMN_TIMESTAMP,time);
        values.put(COLUMN_TYPEQUESID,typeID);

        long result = db.insert(TABLE_RESULTS,null,values);
        db.close();
        return result != -1;
    }

    public boolean isResultExist(int typeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_RESULTS + " WHERE " + COLUMN_TYPEQUESID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(typeID)});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public void updateResult(int score, String time, int typeID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(COLUMN_SCORE, score);
            values.put(COLUMN_TIMESTAMP, time);
            values.put(COLUMN_TYPEQUESID, typeID);

            String whereClause = COLUMN_TYPEQUESID + " = ?";
            String[] whereArgs = {String.valueOf(typeID)};

            db.update(TABLE_RESULTS, values, whereClause, whereArgs);
            db.close();
        }catch (SQLiteException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra khi thực hiện truy vấn SQL
            Log.e("SQLiteException", "Error executing SQL query: " + e.getMessage());
            db.close();
        }
    }

    public Result getResult(int typeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RESULTS WHERE typeQuesID = ?", new String[]{String.valueOf(typeID)});
        Result result = null;
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int score = cursor.getInt(1);
            String time = cursor.getString(2);
            result = new Result(id, score, time, typeID);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return result;
    }
}
