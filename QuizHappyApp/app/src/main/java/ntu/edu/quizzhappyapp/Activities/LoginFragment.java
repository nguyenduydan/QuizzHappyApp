package ntu.edu.quizzhappyapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class LoginFragment extends Fragment {

    Button btn_signup,btn_login;
    ImageButton btn_showPwd;
    EditText username, password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //FindViewByID
        btn_signup = view.findViewById(R.id.btn_signup);
        btn_login = view.findViewById(R.id.btn_login_submit);
        username = view.findViewById(R.id.edit_username);
        password = view.findViewById(R.id.edt_pwd);
        btn_showPwd = view.findViewById(R.id.showPwd);

        //Xử lý sự kiện
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện giao dịch Fragment đến SignUpFragment
                Fragment fragment = new SignUpFragment();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_fragment, fragment)
                        .commit();
            }
        });

        login();
        showPassword();

        return view;
    }

    public void login() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizDBHelper dbHelper = new QuizDBHelper(getContext());
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) ){ //kiểm tra có để trống hay không
                    Toast.makeText(getContext(),"Không được để trống!",Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkUserPass = dbHelper.checkUsernamePassword(user,pass);
                    if(checkUserPass==true){
                        Toast.makeText(getContext(),"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                        startActivity(mainActivity);
                    }else {
                        Toast.makeText(getContext(),"Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void showPassword(){
        btn_showPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu trữ các thuộc tính hiện tại của EditText
                int inputType = password.getInputType();
                int textSize = (int) password.getTextSize();
                int textColor = password.getCurrentTextColor();
                // Lưu trữ vị trí con trỏ
                int cursorPosition = password.getSelectionStart();

                // Thay đổi kiểu hiển thị của EditText
                if ((inputType & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    // Nếu đang ở chế độ mật khẩu, chuyển sang chế độ text thường
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    btn_showPwd.setImageResource(R.drawable.eye_closed);
                } else {
                    // Nếu đang ở chế độ text thường, chuyển sang chế độ mật khẩu
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btn_showPwd.setImageResource(R.drawable.eye_open);
                }

                // Thiết lập lại các thuộc tính sau khi thay đổi
                password.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                password.setTextColor(textColor);
                //Thiêt lập lại vị trí con trỏ
                password.setSelection(cursorPosition);
            }
        });
    }
}