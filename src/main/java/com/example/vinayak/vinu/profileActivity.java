package com.example.vinayak.vinu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileActivity extends AppCompatActivity {

    private DatabaseReference ref,current_user_db;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText pNameField,pClassField;
    private Button pSaveChangeBtn,pHomebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        pNameField = (EditText)findViewById(R.id.pname_field);
        pClassField = (EditText)findViewById(R.id.pclass_field);
        pSaveChangeBtn = (Button) findViewById(R.id.psave_change_btn);
        pHomebtn = (Button) findViewById(R.id.phome_btn);

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();


        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null)
                {

                    String user_id = mAuth.getCurrentUser().getUid();
                    current_user_db = ref.child(user_id);


                }

            }
        };

        /*protected void onStart()
        {

            super.onStart();
            mAuth.addAuthStateListener(mAuthListener);
        }*/

        pHomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(profileActivity.this, AccountActivity.class));

            }
        });

        pSaveChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id = mAuth.getCurrentUser().getUid();
                current_user_db = ref.child(user_id);

                String name, clas;
                name = pNameField.getText().toString();
                clas = pClassField.getText().toString();



               /* if(TextUtils.isEmpty(name))
                    current_user_db.child("class").setValue(clas);
                else if(TextUtils.isEmpty(clas))
                    current_user_db.child("name").setValue(name);
                else
                {

                    current_user_db.child("class").setValue(clas);
                    current_user_db.child("name").setValue(name);
                }*/

                if(TextUtils.isEmpty(name) && TextUtils.isEmpty(clas))
                {

                    Toast.makeText(profileActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(name))
                {
                    current_user_db.child("class").setValue(clas);
                    Intent mainIntent = new Intent(profileActivity.this, AccountActivity.class);
                    startActivity(mainIntent);
                }
                else if(TextUtils.isEmpty(clas))
                {
                    current_user_db.child("name").setValue(name);
                    Intent mainIntent = new Intent(profileActivity.this, AccountActivity.class);
                    startActivity(mainIntent);
                }
                else
                {
                    current_user_db.child("class").setValue(clas);
                    current_user_db.child("name").setValue(name);
                    Intent mainIntent = new Intent(profileActivity.this, AccountActivity.class);
                    startActivity(mainIntent);
                }



                //Intent mainIntent = new Intent(profileActivity.this, AccountActivity.class);
                //startActivity(mainIntent);

            }
        });

    }


}
