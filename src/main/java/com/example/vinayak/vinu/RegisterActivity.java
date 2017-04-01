package com.example.vinayak.vinu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText rNameField,rEmailField,rPasswordField,rClassField;
    private Button rRegisterButton;

    // public String user_id;

    private ProgressDialog rProgress;

    private FirebaseAuth mAuth;

    private DatabaseReference rDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        rDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        rProgress = new ProgressDialog(this);

        rNameField = (EditText) findViewById(R.id.Rname_field);
        rEmailField = (EditText) findViewById(R.id.Remail_field);
        rPasswordField = (EditText) findViewById(R.id.Rpassword_field);
        rClassField = (EditText) findViewById(R.id.Rclass_field);
        rRegisterButton = (Button) findViewById(R.id.Register_btn);

        rRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startRegister();
            }
        });
    }

    private void startRegister() {

        final String name,clas;
        String email,password;

        name = rNameField.getText().toString();
        email = rEmailField.getText().toString();
        password = rPasswordField.getText().toString();
        clas  = rClassField.getText().toString();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(clas))
        {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //rProgress.setMessage("Signing Up...");
            //rProgress.show();

            if(password.length()>=6)
            {
                rProgress.setMessage("Signing Up...");
                rProgress.show();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                              String user_id = mAuth.getCurrentUser().getUid(); //declare the variable

                            DatabaseReference current_user_db = rDatabase.child(user_id);

                            current_user_db.child("name").setValue(name);
                            current_user_db.child("class").setValue(clas);

                            rProgress.dismiss();

                            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(mainIntent);

                        }

                    }
                });
            }
            else
            {

                Toast.makeText(this, "Password Should be atleast 6 characters long.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
