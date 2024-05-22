package ntu.edu.quizzhappyapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ntu.edu.quizzhappyapp.R;

public class QuestionActivity extends AppCompatActivity {

    RadioButton rb1,rb2,rb3,rb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        onClickListener();
    }

    public void onClickListener(){
        onClick(rb1);
        onClick(rb2);
        onClick(rb3);
        onClick(rb4);
    }

    public void onClick(RadioButton typeBtn){
        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestionActivity.this,"OnClick",Toast.LENGTH_SHORT).show();
            }
        });
    }
}