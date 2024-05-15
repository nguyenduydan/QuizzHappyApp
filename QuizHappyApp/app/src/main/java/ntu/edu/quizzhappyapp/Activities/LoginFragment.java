package ntu.edu.quizzhappyapp.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ntu.edu.quizzhappyapp.R;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button btn_signup = view.findViewById(R.id.btn_signup);

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
        return view;
    }
}