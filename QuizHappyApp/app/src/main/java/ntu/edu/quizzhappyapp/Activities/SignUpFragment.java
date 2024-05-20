package ntu.edu.quizzhappyapp.Activities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

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

public class SignUpFragment extends Fragment {

    EditText username, password,repassword, email;
    ImageButton btn_showPwd;
    Button signup,btn_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        //FindViewByID
        btn_login = view.findViewById(R.id.btn_login);
        signup = view.findViewById(R.id.btn_signup_submit);
        username = view.findViewById(R.id.edt_username);
        email = view.findViewById(R.id.edt_email);
        password = view.findViewById(R.id.edt_pwd);
        repassword = view.findViewById(R.id.edt_pwd_confirm);
        btn_showPwd = view.findViewById(R.id.showPwd);

        //Xử lý sự kiện
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_fragment, fragment)
                        .commit();
            }
        });

        signUp();
        showPassword();

        return view;
    }

    public void signUp(){
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizDBHelper dbHelper = new QuizDBHelper(getContext());
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String mail = email.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(mail)){ //kiểm tra có để trống hay không
                    Toast.makeText(getContext(),"Không được để trống!",Toast.LENGTH_SHORT).show();
                }else {
                    if(pass.equals(repass)) { // kiểm tra nhập lại mật khẩu có bằng với mật khẩu hay không
                        Boolean checkUser = dbHelper.checkUsername(user);
                        if (checkUser==false)
                        {
                            Boolean insert = dbHelper.insertDataSignUp(user, pass, mail); //thêm dữ liệu vào data
                            if (insert == true){
                                Toast.makeText(getContext(),"Đăng kí thành công!",Toast.LENGTH_SHORT).show();
                                Fragment fragment = new LoginFragment();
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frame_fragment, fragment)
                                        .commit();
                            }else {
                                Toast.makeText(getContext(),"Đăng kí thất bại!",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(),"Tên người dùng đã tồn tại!",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(),"Mật khẩu không trùng khớp!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void showPassword(){
        btn_showPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu trữ các thuộc tính hiện tại của EditTextPassword
                int inputType = password.getInputType();
                int textSize = (int) password.getTextSize();
                int textColor = password.getCurrentTextColor();
                // Lưu trữ vị trí con trỏ
                int cursorPosition = password.getSelectionStart();

                // Lưu trữ các thuộc tính hiện tại của EditTextRePassword
                int inputTypeRePwd = repassword.getInputType();
                int textSizePwd = (int) repassword.getTextSize();
                int textColorPwd = repassword.getCurrentTextColor();
                // Lưu trữ vị trí con trỏ
                int cursorPositionPwd = repassword.getSelectionStart();

                // Thay đổi kiểu hiển thị của EditText
                if ((inputType & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD || (inputTypeRePwd & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    // Nếu đang ở chế độ mật khẩu, chuyển sang chế độ text thường
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    btn_showPwd.setImageResource(R.drawable.eye_closed);
                } else {
                    // Nếu đang ở chế độ text thường, chuyển sang chế độ mật khẩu
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btn_showPwd.setImageResource(R.drawable.eye_open);
                }

                // Thiết lập lại các thuộc tính sau khi thay đổi
                password.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                password.setTextColor(textColor);
                //Thiêt lập lại vị trí con trỏ
                password.setSelection(cursorPosition);

                // Thiết lập lại các thuộc tính sau khi thay đổi
                repassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePwd);
                repassword.setTextColor(textColorPwd);
                //Thiêt lập lại vị trí con trỏ
                repassword.setSelection(cursorPositionPwd);
            }
        });
    }
}