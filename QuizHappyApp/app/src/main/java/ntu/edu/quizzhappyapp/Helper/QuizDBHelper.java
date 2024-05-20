package ntu.edu.quizzhappyapp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    public static final String COLUMN_QUESTION_OPTION2 = "option2";
    private static final String COLUMN_QUESTION_OPTION3 = "option3";
    private static final String COLUMN_QUESTION_OPTION4 = "option4";
    private static final String COLUMN_QUESTION_OPTION_CORRECT = "option_correct";

    //Tên bảng và cột của kết quả
    private static final String TABLE_RESULTS = "RESULTS";
    private static final String COLUMN_RESULT_ID = "resultID";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_TIMESTAMP = "timestamp";

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
            return true;
        }else {
            return false;
        }
    }

    public boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from USERS where username=? and password=?", new String[]{username,password});

        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }

    }
}
