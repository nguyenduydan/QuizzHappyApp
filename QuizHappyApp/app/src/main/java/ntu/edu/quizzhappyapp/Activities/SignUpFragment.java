package ntu.edu.quizzhappyapp.Activities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class SignUpFragment extends Fragment {

    EditText username, password,repassword, email;
    Button signup,btn_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        btn_login = view.findViewById(R.id.btn_login);
        signup = view.findViewById(R.id.btn_signup_submit);
        username = view.findViewById(R.id.edt_username);
        email = view.findViewById(R.id.edt_email);
        password = view.findViewById(R.id.edt_pwd);
        repassword = view.findViewById(R.id.edt_pwd_confirm);

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


        return view;
    }
}