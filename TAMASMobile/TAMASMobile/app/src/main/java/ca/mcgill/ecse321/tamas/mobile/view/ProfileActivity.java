package ca.mcgill.ecse321.tamas.mobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tamas.ecse321.mcgill.ca.view.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void editProfile (View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void onBackPressed()   {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}
