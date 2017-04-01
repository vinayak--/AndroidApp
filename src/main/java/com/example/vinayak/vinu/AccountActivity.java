package com.example.vinayak.vinu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountActivity extends AppCompatActivity {

   // private FirebaseAuth mAuth;

   // private FirebaseAuth.AuthState Listener mAuthListener;

    private DatabaseReference oDatabase;
    public FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

       mAuth = FirebaseAuth.getInstance();

        oDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null)
                {

                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = oDatabase.child(user_id);


                }

            }
        };





    }

    protected void onStart()
    {

        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {


        if(item.getItemId() == R.id.action_logout)
        {

            logout();


        }

        if(item.getItemId() == R.id.edit_profile) //for redirecting to profile
        {
            Intent eIntent = new Intent(AccountActivity.this,profileActivity.class);
            startActivity(eIntent);

        }


        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        mAuth.signOut();
        //mAuth = null;
        startActivity(new Intent(AccountActivity.this, MainActivity.class));




    }



}
