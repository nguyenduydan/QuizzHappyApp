package ntu.edu.quizzhappyapp.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class ResultActivity extends AppCompatActivity {

    Button btnBack;
    TextView quesCorrect, quesWrong, time, point, percent;
    ImageView img;
    QuizDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        db = new QuizDBHelper(this);
        btnBack = findViewById(R.id.appCompatButton);
        img = findViewById(R.id.gifImg);
        quesCorrect = findViewById(R.id.tvCorrectCount);

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
    }

    private void show(){

    }
}