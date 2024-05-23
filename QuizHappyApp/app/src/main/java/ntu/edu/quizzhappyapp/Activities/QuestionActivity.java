package ntu.edu.quizzhappyapp.Activities;

import android.os.Bundle;
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

import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class QuestionActivity extends AppCompatActivity {

    RadioButton rb1,rb2,rb3,rb4;
    Button btnBack;
    TextView tvTypeQues, tvCountQues, tvTimeCount;
    QuizDBHelper db;
    int quesCurrent, totalQues, timeCount;

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
        tvTimeCount = findViewById(R.id.tv_timeCount);
        tvCountQues = findViewById(R.id.tv_countQues);

        showInfo();
        onClickListener();

    }

    private void showInfo(){
        Bundle bundle = getIntent().getExtras();
        quesCurrent = 1;
        if (bundle != null) {
            int ID = bundle.getInt("typeID");
            String value = db.getTypeName(ID);
            if(value != null) {
                tvTypeQues.setText(value);
            }else {
                Toast.makeText(QuestionActivity.this,"Không có giá trị! " + value,Toast.LENGTH_SHORT).show();

            }
        }
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

    private void onClick(RadioButton typeBtn){
        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestionActivity.this,"OnClick",Toast.LENGTH_SHORT).show();
            }
        });
    }
}