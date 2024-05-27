package ntu.edu.quizzhappyapp.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.Models.Result;
import ntu.edu.quizzhappyapp.R;

public class ResultActivity extends AppCompatActivity {

    Button btnBack;
    TextView quesCorrect, quesWrong, time, point, percent;
    ImageView img;
    QuizDBHelper db;
    Result resultData;
    ProgressBar progressBar;
    int i=0;
    int delay=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        db = new QuizDBHelper(this);
        btnBack = findViewById(R.id.btn_back);
        img = findViewById(R.id.gifImg);
        quesCorrect = findViewById(R.id.tvCorrectCount);
        quesWrong = findViewById(R.id.tvWrongCount);
        point = findViewById(R.id.tvPoint);
        time = findViewById(R.id.tvTime);
        percent = findViewById(R.id.tvPercent);
        progressBar = findViewById(R.id.circularProgressBar);

        Glide.with(this)
                .asGif()
                .load(R.drawable.congrat)
                .into(img);

        // Tạo Intent để quay lại MainActivity
        Intent home = new Intent(ResultActivity.this, MainActivity.class);
        // Lấy dữ liệu userID từ Intent hiện tại
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int userID = bundle.getInt("userID");
            // Đặt dữ liệu userID vào Intent để gửi về MainActivity
            home.putExtra("userID", userID);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi chạy Intent
                startActivity(home);
            }
        });
        show();
    }

    public void show(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int typeId = bundle.getInt("typeID");
            int anwTrue = bundle.getInt("anwTrue");
            int anwFalse = bundle.getInt("anwFalse");
            int totalques = bundle.getInt("totalQues");
            resultData = db.getResult(typeId);
            if (resultData != null){
                quesCorrect.setText(String.valueOf(anwTrue));
                quesWrong.setText(String.valueOf(anwFalse));
                point.setText(String.valueOf(resultData.getScore()));
                time.setText(resultData.getTimeStamp());
                double percentage = (double) anwTrue / totalques * 100;
                percent.setText(String.format("%.0f%%", percentage));
                final Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i <= (int)percentage){
                            progressBar.setProgress(i);
                            i++;
                            delay +=2;
                            handler.postDelayed(this,delay);
                        }else {
                            handler.removeCallbacks(this);
                        }
                    }
                },delay);

            }else {
                Toast.makeText(this, "Lỗi",Toast.LENGTH_SHORT).show();
            }
        }
    }
}