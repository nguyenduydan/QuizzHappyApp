package ntu.edu.quizzhappyapp.Activities;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import ntu.edu.quizzhappyapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        // Tạo một instance của LoginFragment
        Fragment fragment = new LoginFragment();

        // Bắt đầu một giao dịch Fragment
        getSupportFragmentManager().beginTransaction()

                // Thay thế Fragment hiện tại (nếu có) tại vị trí R.id.frame_fragment
                // bằng Fragment mới được chỉ định
                .replace(R.id.frame_fragment, fragment)

                // Hoàn thành giao dịch, làm cho thay đổi trở thành hiệu lực
                .commit();

    }



}