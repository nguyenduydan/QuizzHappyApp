package ntu.edu.quizzhappyapp.Activities.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import ntu.edu.quizzhappyapp.Activities.ResultActivity;
import ntu.edu.quizzhappyapp.Adapters.ListAdapter;
import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.Models.TypeQues;
import ntu.edu.quizzhappyapp.R;

public class ListFragment extends Fragment {
    //Khai báo biến
    ListAdapter adapter;
    ArrayList<TypeQues> list;
    RecyclerView recyclerView;
    TextView tvUsername;
    ImageView imgUser;
    QuizDBHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new QuizDBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.ryclerView);
        tvUsername = view.findViewById(R.id.tv_username);
        imgUser = view.findViewById(R.id.user_img);
        //Hiển thị username ở ListFragment
        // Lấy dữ liệu từ Intent của hoạt động chứa Fragment
        Bundle bundle = getActivity().getIntent().getExtras();
        int userID =0;
        if (bundle != null) {
            userID = bundle.getInt("userID");
            String userName = db.getUsername(userID);
            String userAvatar = db.getImg(userID);
            if(userName != null) {
                tvUsername.setText(userName);
                if (userAvatar != null && !userAvatar.isEmpty()) {
                    File imgFile = new File(getContext().getFilesDir(), userAvatar);
                    if (imgFile.exists()) {
                        Glide.with(this).load(imgFile).into(imgUser);
                    }
                }
            } else {
                Toast.makeText(getContext(),"Không tìm thấy thông tin người dùng!",Toast.LENGTH_SHORT).show();
            }
        }
        list = db.loadTypeQuesFromDatabase();

        //5. Tạo layout manager để đặt bố cục cho Recyc ler
        RecyclerView.LayoutManager layoutGrid = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutGrid);
        //6. Tạo adapter gắn vào nguồn dữ liệu
        adapter = new ListAdapter(view.getContext(), list, userID);
        //7, Gắn adapter vào Recycler
        recyclerView.setAdapter(adapter);

        return view;
    }

}