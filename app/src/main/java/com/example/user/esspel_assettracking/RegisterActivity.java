package com.example.user.esspel_assettracking;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Spinner mSpinner;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    Button register;
    EditText entName,entPass,entPID,entMail,entPhone;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mSpinner = (Spinner)findViewById(R.id.spinner);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        entMail = (EditText) findViewById(R.id.enterEmail);
        entName =( EditText)findViewById(R.id.entName);
        entPass = (EditText)findViewById(R.id.enterPass);
        entPID = (EditText)findViewById(R.id.enterProviderId);
        entPhone =(EditText)findViewById(R.id.enterPhone);
        register = (Button)findViewById(R.id.registerBtn);
        progressDialog = new ProgressDialog(this);
        toolbar.setTitle("Register");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.type));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(entMail.getText())||TextUtils.isEmpty(entPass.getText())||TextUtils.isEmpty(entPID.getText())||
                TextUtils.isEmpty(entName.getText()))
                {
                    Toast.makeText(RegisterActivity.this,"Fill up all the fields",Toast.LENGTH_LONG).show();
                }
                else{
                    progressDialog.setMessage("Signing Up!");
                    progressDialog.show();
                    signUp(entMail.getText().toString(),entPass.getText().toString());
                }
            }
        });


    }
    public void signUp(String mail,String pass){
        mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Registration Failed",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                else{
                   Provider provider = new Provider(entName.getText().toString(),entPID.getText().toString()
                           ,mSpinner.getSelectedItem().toString(),entPhone.getText().toString())  ;
                    Log.e("User ID",mAuth.getCurrentUser().getUid());
                   final DatabaseReference databaseReference = database.getReference();
                   databaseReference.child("Providers").child(mAuth.getCurrentUser().getUid()).setValue(provider);
                   progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_LONG).show();

                }
            }
        });

    }


}
