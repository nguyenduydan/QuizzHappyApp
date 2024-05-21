package ntu.edu.quizzhappyapp.Activities.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ntu.edu.quizzhappyapp.Activities.MainActivity;
import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class UserFragment extends Fragment {

    QuizDBHelper db;
    TextView username, password, email;
    EditText edtInfo;
    Button btnEditUsername, btnEditEmail, btnEditPass,cancelDialog, submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        db = new QuizDBHelper(getContext());
        username = view.findViewById(R.id.tv_username);
        password = view.findViewById(R.id.tv_password);
        email = view.findViewById(R.id.tv_email);
        btnEditUsername = view.findViewById(R.id.btn_edit_username);
        btnEditEmail = view.findViewById(R.id.btn_edit_email);
        btnEditPass = view.findViewById(R.id.btn_edit_pass);

        info();
        clickListener();
        return view;
    }

    public void info(){
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            int userID = bundle.getInt("userID");
            String userName = db.getUsername(userID);
            String userPass = db.getPassword(userID);
            String userEmail = db.getEmail(userID);
            if(userName != null) {
                username.setText(userName);
                password.setText(userPass);
                email.setText(userEmail);
            } else {
                Toast.makeText(getContext(),"Không tìm thấy thông tin người dùng!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clickListener(){
        btnEditUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogEdit(Gravity.CENTER, "username");
            }
        });
        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogEdit(Gravity.CENTER,"email");
            }
        });
        btnEditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogEdit(Gravity.CENTER,"password");
            }
        });
    }

    public void openDialogEdit(int gravity, String type){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit);

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
        cancelDialog =dialog.findViewById(R.id.btn_cancel);
        submit =dialog.findViewById(R.id.btn_submit);
        edtInfo = dialog.findViewById(R.id.edt_info);

        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getActivity().getIntent().getExtras();
                if (bundle != null) {
                    int userID = bundle.getInt("userID");
                    String newValue = edtInfo.getText().toString();
                    if(!newValue.isEmpty()){
                        Boolean updateData = db.editInfo(userID,type,newValue);
                        if(updateData == true) {
                            Toast.makeText(getContext(), "Sửa thông tin thành công!", Toast.LENGTH_SHORT).show();
                            ((MainActivity) getActivity()).reloadFragment();
                            dialog.dismiss();
                        }else {
                                Toast.makeText(getContext(),"Sửa thông tin không thành công!",Toast.LENGTH_SHORT).show();
                            }
                    }else{
                        Toast.makeText(getContext(),"Vui lòng nhập thông tin!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();
    }

}