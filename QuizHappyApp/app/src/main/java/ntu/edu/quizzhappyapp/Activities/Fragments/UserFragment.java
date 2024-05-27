package ntu.edu.quizzhappyapp.Activities.Fragments;

import static android.app.Activity.RESULT_OK;
import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.bumptech.glide.Glide;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import ntu.edu.quizzhappyapp.Activities.MainActivity;
import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class UserFragment extends Fragment {

    QuizDBHelper db;
    TextView username, password, email;
    EditText edtInfo;
    ImageView userImg;
    Button btnEditUsername, btnEditEmail, btnEditPass,cancelDialog, submit, editImg;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 8888;
    int userID=0;

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
            userID= bundle.getInt("userID");
            String userName = db.getUsername(userID);
            String userPass = db.getPassword(userID);
            String userEmail = db.getEmail(userID);
            String userAvatar = db.getImg(userID);

            username.setText(userName);
            password.setText(userPass);
            email.setText(userEmail);
            if (userAvatar != null && !userAvatar.isEmpty()) {
                File imgFile = new File(getContext().getFilesDir(), userAvatar);
                if (imgFile.exists()) {
                    Glide.with(this).load(imgFile).into(userImg);
                }
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

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                // Lưu ảnh vào trong mipmap
                String imageName = saveImageToInternalStorage(uri);
                // Update hình ảnh vào trong Sqlite
                boolean updateImg = db.updateImagerUser(userID, imageName);
                if (updateImg) {
                    // Load hình ảnh để hiển thị
                    Glide.with(this).load(new File(getContext().getFilesDir(), imageName)).into(userImg);
                } else {
                    Toast.makeText(getContext(), "Không upload đưuọc ảnh", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Lỗi khi lưu ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String saveImageToInternalStorage(Uri uri) throws IOException {
        InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();

        // Tạo tên độc đáo cho ảnh dựa trên thời gian hiện tại
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageName = "user_avatar_" + userID + "_" + timeStamp + ".png";
        File file = new File(getContext().getFilesDir(), imageName);
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();

        return imageName;
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