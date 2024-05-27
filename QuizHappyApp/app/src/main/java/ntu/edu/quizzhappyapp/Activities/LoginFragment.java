package ntu.edu.quizzhappyapp.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class LoginFragment extends Fragment {

    Button btn_signup,btn_login, btnOk,btnTry;
    ImageButton btn_showPwd;
    TextView errorTxt, infoTxt;
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

                        int userID = dbHelper.getID(user,pass);
                        if(userID != -1){
                            openDialogOk(Gravity.CENTER,userID);
                            infoTxt.setText("Đăng nhập thành công");
                        }else{
                            openDialogError(Gravity.CENTER);
                            errorTxt.setText("Đăng nhập thất bại");
                        }
                    }else {
                        openDialogError(Gravity.CENTER);
                        errorTxt.setText("Đăng nhập thất bại");
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
    public void openDialogOk(int gravity, int userID){
        final Dialog dialog = new Dialog(getContext());
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
        infoTxt = dialog.findViewById(R.id.tv_Info);
        ImageView img = dialog.findViewById(R.id.gifImg);
        Glide.with(this)
                .asGif()
                .centerCrop()
                .load(R.drawable.tick)
                .into(img);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();// Tạo Bundle để chứa dữ liệu cần truyền
                bundle.putInt("userID", userID);
                Intent mainActivity = new Intent(getContext(),MainActivity.class);
                mainActivity.putExtras(bundle);// Đặt Bundle vào Intent
                startActivity(mainActivity);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void openDialogError(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_danger);

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
        btnTry =dialog.findViewById(R.id.btn_try);
        errorTxt = dialog.findViewById(R.id.tv_error);
        ImageView img = dialog.findViewById(R.id.gifImgEr);
        Glide.with(this)
                .asGif()
                .centerCrop()
                .load(R.drawable.xstick)
                .into(img);

        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}