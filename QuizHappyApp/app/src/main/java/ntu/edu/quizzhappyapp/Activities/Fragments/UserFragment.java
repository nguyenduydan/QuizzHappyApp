package ntu.edu.quizzhappyapp.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ntu.edu.quizzhappyapp.Activities.MainActivity;
import ntu.edu.quizzhappyapp.Helper.QuizDBHelper;
import ntu.edu.quizzhappyapp.R;

public class UserFragment extends Fragment {

    QuizDBHelper db;
    TextView username, password, email;

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

        info();
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
}