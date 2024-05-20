package ntu.edu.quizzhappyapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class LoginFragment extends Fragment {

    Button btn_signup;
    Button btn_login;
    EditText username, password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btn_signup = view.findViewById(R.id.btn_signup);
        btn_login = view.findViewById(R.id.btn_login_submit);
        username = view.findViewById(R.id.edit_username);
        password = view.findViewById(R.id.edt_pwd);

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
        return view;
    }
}