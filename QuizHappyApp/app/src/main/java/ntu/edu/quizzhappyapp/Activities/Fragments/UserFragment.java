package ntu.edu.quizzhappyapp.Activities.Fragments;

import static android.app.Activity.RESULT_OK;
import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import ntu.edu.quizzhappyapp.Activities.MainActivity;
import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class UserFragment extends Fragment {

    QuizDBHelper db;
    TextView username, password, email;
    EditText edtInfo;
    ImageView userImg;
    Button btnEditUsername, btnEditEmail, btnEditPass,cancelDialog, submit, editImg;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSION = 8888;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
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
        editImg = view.findViewById(R.id.btn_edit_img);
        userImg = view.findViewById(R.id.user_img);

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
//            String userAvatar = db.getImg(userID);

            username.setText(userName);
            password.setText(userPass);
            email.setText(userEmail);
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

//        editImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
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