package ntu.edu.quizzhappyapp.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

@SuppressLint("SetTextI18n")
public class SignUpFragment extends Fragment {

    EditText username, password,repassword, email;
    TextView error,info;
    ImageButton btn_showPwd;
    Button signup,btn_login,btnOk,btnTry;

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
        btn_login.setOnClickListener(v -> {
            Fragment fragment = new LoginFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_fragment, fragment)
                    .commit();
        });

        signUp();
        showPassword();

        return view;
    }


    public void signUp(){
        signup.setOnClickListener(v -> {
            QuizDBHelper dbHelper = new QuizDBHelper(getContext());
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String repass = repassword.getText().toString();
            String mail = email.getText().toString();

            if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(mail)){ //kiểm tra có để trống hay không
                openDialogError(Gravity.CENTER);
                error.setText("Không được để trống!");
            }else {
                if(pass.equals(repass)) { // kiểm tra nhập lại mật khẩu có bằng với mật khẩu hay không
                    boolean checkUser = dbHelper.checkUsername(user);
                    if (!checkUser)
                    {
                        boolean insert = dbHelper.insertDataSignUp(user, pass, mail); //thêm dữ liệu vào data
                        if (insert){
                            openDialogOk(Gravity.CENTER);
                            info.setText("Đăng kí thành công");
                        }else {
                            openDialogError(Gravity.CENTER);
                            error.setText("Đăng kí thất bại!");
                        }
                    }else{
                        openDialogError(Gravity.CENTER);
                        error.setText("Tên người dùng đã tồn tại!");
                    }
                }else {
                    openDialogError(Gravity.CENTER);
                    error.setText("Mật khẩu không trùng khớp!");
                }
            }
        });
    }
    public void showPassword(){
        btn_showPwd.setOnClickListener(v -> {
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
        });
    }
    public void openDialogOk(int gravity){
        final Dialog dialog = new Dialog(requireContext());
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

        btnOk =dialog.findViewById(R.id.btn_submit);
        info = dialog.findViewById(R.id.tv_Info);
        ImageView img = dialog.findViewById(R.id.gifImg);
        Glide.with(this)
                .asGif()
                .centerCrop()
                .load(R.drawable.tick)
                .into(img);

        btnOk.setOnClickListener(v -> {
            Fragment fragment = new LoginFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_fragment, fragment)
                    .commit();
            dialog.dismiss();
        });
        dialog.show();
    }

    public void openDialogError(int gravity){
        final Dialog dialog = new Dialog(requireContext());
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

        btnTry =dialog.findViewById(R.id.btn_try);
        error = dialog.findViewById(R.id.tv_error);
        ImageView img = dialog.findViewById(R.id.gifImgEr);
        Glide.with(this)
                .asGif()
                .centerCrop()
                .load(R.drawable.xstick)
                .into(img);

        btnTry.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

}