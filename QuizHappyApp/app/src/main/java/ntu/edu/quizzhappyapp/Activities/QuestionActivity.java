package ntu.edu.quizzhappyapp.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;

import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.Models.Questions;
import ntu.edu.quizzhappyapp.R;

public class QuestionActivity extends AppCompatActivity {

    RadioButton rb1,rb2,rb3,rb4;
    Button btnBack, btnOk;
    TextView tvTypeQues, tvCountQues, tvTimeCount,tvQuestion,tvInfo;
    QuizDBHelper db;
    int quesCurrent, totalQues;
    ArrayList<Questions> list;
    CountDownTimer countDownTimer;
    static final long TIME_LIMIT = 30000;
    Handler handler;
    int pointsPerQuestion = 10, totalPoint=0, countQuesTrue=0,countQuesFalse=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);
        handler = new Handler();
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

    private int getTypeID(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getInt("typeID");
        }else {
            return -1;
        }
    }

    private void showInfo() {
        quesCurrent = 0; // Khởi tạo chỉ số câu hỏi hiện tại
        int ID = getTypeID();
        if(ID == -1){
            Toast.makeText(QuestionActivity.this, "Lỗi không tìm thấy ID", Toast.LENGTH_SHORT).show();
        }else{
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
        Drawable drawable_T = getResources().getDrawable(R.drawable.btn_bg_answer_true);
        Drawable drawable_F = getResources().getDrawable(R.drawable.btn_bg_answer_false);
        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions currentQuestion = list.get(quesCurrent);
                Log.e("CurrentQuestion", currentQuestion.toString());

                if(checkAnswer(currentQuestion, typeBtn)){
                    countQuesTrue++;
                    typeBtn.setBackground(drawable_T);
                    typeBtn.setTextColor(Color.GREEN);
                    totalPoint += pointsPerQuestion;
                }
                else {
                    countQuesFalse++;
                    typeBtn.setBackground(drawable_F);
                    typeBtn.setTextColor(Color.RED);
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextQuestion();
                    }
                }, 500);
            }
        });
    }
    private boolean checkAnswer(Questions question, RadioButton selectedRadioButton) {
        int selectedOptionId =getPos(selectedRadioButton);
        int correctOptionId = question.getOptionCorrect();
        if(selectedOptionId == correctOptionId)
            return true;
        return false;
    }

    private int getPos(RadioButton selectBtn){
        int id = selectBtn.getId();
        if (id == R.id.radioButton1) {
            return 1;
        } else if (id == R.id.radioButton2) {
            return 2;
        } else if (id == R.id.radioButton3) {
            return 3;
        } else if (id == R.id.radioButton4) {
            return 4;
        } else {
            return -1;
        }
    }

    private void nextQuestion() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Drawable drawable = getResources().getDrawable(R.drawable.btn_bg_question);
        //Thiết lập lại màu cũ
        rb1.setBackground(drawable);
        rb2.setBackground(drawable);
        rb3.setBackground(drawable);
        rb4.setBackground(drawable);
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);
        // Tăng chỉ số câu hỏi hiện tại
        quesCurrent++;
        totalQues = list.size();
        // Kiểm tra nếu đã hết câu hỏi
        if (quesCurrent >= list.size()) {
            int id = getTypeID(); // ID của loại câu hỏi (typeID)
            Date currentTime = new Date();

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String time = dateTimeFormat.format(currentTime);

            boolean isResultExis = db.isResultExist(id);
            if (isResultExis) {
                db.updateResult(totalPoint, time, id);
            } else {
                db.insertResult(totalPoint, time, id);
            }

            openDialogOk(Gravity.CENTER);
            tvInfo.setText("Đã hoàn thành");
            return;
        }
        tvCountQues.setText((quesCurrent + 1)+"/"+totalQues);
        // Hiển thị câu hỏi tiếp theo
        displayQuestion(list.get(quesCurrent));
    }

    public void openDialogOk(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){ //Click ra ngoài sẽ tắt
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }
        btnOk =dialog.findViewById(R.id.btn_submit);
        tvInfo = dialog.findViewById(R.id.tv_Info);
        ImageView img = dialog.findViewById(R.id.gifImg);
        Glide.with(this)
                .asGif()
                .centerCrop()
                .load(R.drawable.tick)
                .into(img);
        Intent resultActivity = new Intent(QuestionActivity.this,ResultActivity.class);
        Bundle bundle = getIntent().getExtras();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bundle != null) {
                    int userID = bundle.getInt("userID");
                    bundle.putInt("userID",userID);
                    bundle.putInt("typyID",getTypeID());
                    bundle.putInt("anwTrue",countQuesTrue);
                    bundle.putInt("anwFalse",countQuesFalse);
                    bundle.putInt("totalQues",totalQues);
                    resultActivity.putExtras(bundle);
                }
                startActivity(resultActivity);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}