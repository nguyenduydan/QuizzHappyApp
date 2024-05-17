package ntu.edu.quizzhappyapp.Helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class QuizDBHelper  {
//    private static final String DATABASE_NAME = "QuizDB"; //Tên database
//    private static final int DATABASE_VERSION = 1;//phiên bản
//
//    //Tên bảng và cột của người dùng
//    public static final String TABLE_USERS = "USERS";
//    public static final String COLUMN_USER_ID = "userID";
//    public static final String COLUMN_USERNAME = "username";
//    public static final String COLUMN_EMAIL = "email";
//    public static final String COLUMN_PASSWORD = "password";
//    public static final String COLUMN_AVATAR = "avatar";
//
//    //Tên bảng và cột của loại câu hỏi
//    public static final String TABLE_TYPEQUES = "TYPEQUES";
//    public static final String COLUMN_TYPEQUES_ID = "typeID";
//    public static final String COLUMN_TYPEQUES_NAME = "nameType";
//    public static final String COLUMN_TYPEQUES_IMAGE = "image";
//
//    //Tên bảng và cột của câu hỏi
//    public static final String TABLE_QUESTION = "QUESTION";
//    public static final String COLUMN_QUESTION_ID = "quesID";
//    public static final String COLUMN_TYPEQUESTION_ID = "typeID";
//    public static final String COLUMN_QUESTION_QUES = "question";
//    public static final String COLUMN_QUESTION_OPTION1 = "option1";
//    public static final String COLUMN_QUESTION_OPTION2 = "option2";
//    public static final String COLUMN_QUESTION_OPTION3 = "option3";
//    public static final String COLUMN_QUESTION_OPTION4 = "option4";
//    public static final String COLUMN_QUESTION_OPTION_CORRECT = "option_correct";
//
//    //Tên bảng và cột của kết quả
//    public static final String TABLE_RESULTS = "results";
//    public static final String COLUMN_RESULT_ID = "resultID";
//    public static final String COLUMN_SCORE = "score";
//    public static final String COLUMN_TIMESTAMP = "timestamp";
//
//    // Câu lệnh tạo bảng cho bảng người dùng
//    private static final String CREATE_TABLE_USERS =
//            "CREATE TABLE " + TABLE_USERS + " (" +
//                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_USERNAME + " TEXT, " +
//                    COLUMN_EMAIL + " TEXT, " +
//                    COLUMN_PASSWORD + " TEXT, " +
//                    COLUMN_AVATAR + "TEXT);";
//
//    //Câu lệnh tạo bảng cho loại câu hỏi
//    private static final String CREATE_TABLE_TYPEQUES =
//            "CREATE TABLE " + TABLE_TYPEQUES + " (" +
//                    COLUMN_TYPEQUES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_TYPEQUES_NAME + " TEXT, " +
//                    COLUMN_TYPEQUES_IMAGE + " TEXT);";
//
//    //Câu lệnh tạo bảng cho câu hỏi
//    private static final String CREATE_TABLE_QUESTION =
//            "CREATE TABLE " + TABLE_QUESTION + " (" +
//                    COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_QUESTION_QUES + " TEXT, " +
//                    COLUMN_QUESTION_OPTION1 + " TEXT, " +
//                    COLUMN_QUESTION_OPTION2 + " TEXT, " +
//                    COLUMN_QUESTION_OPTION3 + " TEXT, " +
//                    COLUMN_QUESTION_OPTION4 + " TEXT, " +
//                    COLUMN_QUESTION_OPTION_CORRECT + " INTEGER, "+
//                    COLUMN_TYPEQUESTION_ID + " INTEGER, " +
//                    "FOREIGN KEY(" + COLUMN_TYPEQUESTION_ID + ") REFERENCES " + //Tạo khóa ngoại đến bảng loại câu hỏi
//                    TABLE_TYPEQUES + "(" + COLUMN_TYPEQUES_ID + "))";
//
//    //Câu lệnh tạo bảng cho kết quả
//    private static final String CREATE_TABLE_RESULT =
//            "CREATE TABLE " + TABLE_RESULTS + " (" +
//                    COLUMN_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_SCORE + " INTEGER, " +
//                    COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
//
//
//    private SQLiteDatabase db;
//
//    public QuizDBHelper(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        this.db =db;
//
//        db.execSQL(CREATE_TABLE_USERS);
//        db.execSQL(CREATE_TABLE_TYPEQUES);
//        db.execSQL(CREATE_TABLE_QUESTION);
//        db.execSQL(CREATE_TABLE_RESULT);
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPEQUES);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
//        onCreate(db);
//
//    }
}
