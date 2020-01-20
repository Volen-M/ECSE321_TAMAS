package ca.mcgill.ecse321.tamas.mobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tamas.ecse321.mcgill.ca.view.R;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void saveChanges (View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
