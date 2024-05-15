package ntu.edu.quizzhappyapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ntu.edu.quizzhappyapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //Khai báo các hàm
        Fragment fragment = new LoginFragment(); // Không cần truyền FragmentManager
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_fragment, fragment) // Sử dụng replace() để thay thế nếu đã có Fragment
                .commit();

    }

}