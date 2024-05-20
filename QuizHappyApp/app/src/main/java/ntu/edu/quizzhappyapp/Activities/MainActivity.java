package ntu.edu.quizzhappyapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import ntu.edu.quizzhappyapp.Activities.Fragments.UserFragment;
import ntu.edu.quizzhappyapp.Adapters.ListAdapter;
import ntu.edu.quizzhappyapp.Models.TypeQues;
import ntu.edu.quizzhappyapp.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomView = (BottomNavigationView) findViewById(R.id.menu_bottom);
        FrameLayout fragmentLayout = (FrameLayout) findViewById(R.id.fragmentLayout);
        bottomView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemID = menuItem.getItemId();
                if(itemID == R.id.home){
                    loadFragment(new ntu.edu.quizzhappyapp.Activities.Fragments.ListFragment(),false);
                } else if (itemID == R.id.user) {
                    loadFragment(new ntu.edu.quizzhappyapp.Activities.Fragments.UserFragment(),false);
                } else if (itemID == R.id.logout) {
                    Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(logout);
                }
                return true;
            }
        });
        loadFragment(new ntu.edu.quizzhappyapp.Activities.Fragments.ListFragment(),true);
    }
    public void loadFragment(Fragment fragment, boolean boolen){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (boolen){
            fragmentTransaction.add(R.id.fragmentLayout, fragment);
        }else{
            fragmentTransaction.replace(R.id.fragmentLayout, fragment);
        }
        fragmentTransaction.replace(R.id.fragmentLayout, fragment);
        fragmentTransaction.commit();
    }
}