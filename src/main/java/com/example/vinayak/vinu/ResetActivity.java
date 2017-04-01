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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    private TextView rTextField;
    private EditText rEmailField;
    private Button rResetBtn;
    private ProgressDialog rProgress;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        rTextField = (TextView) findViewById(R.id.rtext_field);
        rEmailField = (EditText) findViewById(R.id.remail_field);
        rResetBtn = (Button) findViewById(R.id.reset_btn);
        rProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        rResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = rEmailField.getText().toString();



                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(ResetActivity.this, "Please enter the Email Id", Toast.LENGTH_SHORT).show();

                }

                rProgress.setMessage("Sending link...");
                rProgress.show();

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {


                            Toast.makeText(ResetActivity.this, "We have sent password reset link", Toast.LENGTH_SHORT).show();
                            rProgress.dismiss();
                            startActivity(new Intent(ResetActivity.this,MainActivity.class));

                        }
                        else
                        {
                            Toast.makeText(ResetActivity.this, "Failed to send password reset link", Toast.LENGTH_SHORT).show();
                            rProgress.dismiss();
                        }

                    }
                });

            }
        });
    }
}
