package ntu.edu.quizzhappyapp.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.Models.Questions;
import ntu.edu.quizzhappyapp.R;

public class QuestionActivity extends AppCompatActivity {

    RadioButton rb1,rb2,rb3,rb4;
    Button btnBack;
    TextView tvTypeQues, tvCountQues, tvTimeCount,tvQuestion;
    QuizDBHelper db;
    int quesCurrent, totalQues, timeCount;
    ArrayList<Questions> list;
    CountDownTimer countDownTimer;
    static final long TIME_LIMIT = 30000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);

        db = new QuizDBHelper(this);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        btnBack = findViewById(R.id.btn_back);
        tvTypeQues = findViewById(R.id.tv_typeQues);
        tvQuestion = findViewById(R.id.tv_Question);
        tvTimeCount = findViewById(R.id.tv_timeCount);
        tvCountQues = findViewById(R.id.tv_countQues);

        showInfo();
        onClickListener();

    }


    private void showInfo() {
        Bundle bundle = getIntent().getExtras();
        quesCurrent = 0; // Khởi tạo chỉ số câu hỏi hiện tại
        int ID = 0;
        if (bundle != null) {
            ID = bundle.getInt("typeID");
            String value = db.getTypeName(ID);
            if (value != null) {
                tvTypeQues.setText(value);
            } else {
                Toast.makeText(QuestionActivity.this, "Không có giá trị!", Toast.LENGTH_SHORT).show();
            }
        }
        list = new ArrayList<>();
        list = db.loadQuestion();

        totalQues = list.size();
        tvCountQues.setText((quesCurrent + 1) + "/" + totalQues);

        // Hiển thị câu hỏi đầu tiên nếu có
        if (!list.isEmpty()) {
            displayQuestion(list.get(quesCurrent));
        } else {
            Toast.makeText(this, "Không có câu hỏi nào!", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextQuestion() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Tăng chỉ số câu hỏi hiện tại
        quesCurrent++;
        totalQues = list.size();
        // Kiểm tra nếu đã hết câu hỏi
        if (quesCurrent >= list.size()) {
            Toast.makeText(this, "Bạn đã hoàn thành tất cả các câu hỏi!", Toast.LENGTH_SHORT).show();
            // Bạn có thể thực hiện thêm hành động nào đó khi hoàn thành tất cả các câu hỏi
            return;
        }
        tvCountQues.setText((quesCurrent + 1)+"/"+totalQues);
        // Hiển thị câu hỏi tiếp theo
        displayQuestion(list.get(quesCurrent));
    }

    private void displayQuestion(Questions question) {
        // Hiển thị câu hỏi và các lựa chọn lên giao diện
        tvQuestion.setText(question.getQuestion());
        rb1.setText(question.getOption1());
        rb2.setText(question.getOption2());
        rb3.setText(question.getOption3());
        rb4.setText(question.getOption4());
        startTimer();
    }
    private void onClickListener(){
        onClick(rb1);
        onClick(rb2);
        onClick(rb3);
        onClick(rb4);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(TIME_LIMIT, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                tvTimeCount.setText(String.format("00:%02d", secondsRemaining));

                if (secondsRemaining <= 10) {
                    tvTimeCount.setTextColor(Color.RED);
                } else {
                    tvTimeCount.setTextColor(Color.WHITE); // Đặt lại màu đen nếu còn hơn 10 giây
                }
            }

            @Override
            public void onFinish() {
                // Khi thời gian kết thúc, tự động chuyển sang câu hỏi tiếp theo
                nextQuestion();
            }
        }.start();
    }

    private void onClick(RadioButton typeBtn){
        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestionActivity.this,"OnClick",Toast.LENGTH_SHORT).show();
                nextQuestion();
            }
        });
    }
}