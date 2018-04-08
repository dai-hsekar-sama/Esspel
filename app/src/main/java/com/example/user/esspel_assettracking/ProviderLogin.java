package com.example.user.esspel_assettracking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProviderLogin extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText entEmail,entPass;
    String mail,pass;
    TextView register;
    Button lgnBtn;
    ProgressDialog progressDialog;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        entEmail  = (EditText)findViewById(R.id.enterEmail);
        entPass = (EditText)findViewById(R.id.enterPass);
        register =(TextView)findViewById(R.id.registerNew);
        lgnBtn = (Button)findViewById(R.id.loginBtn);
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderLogin.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
        lgnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(entEmail.getText())|| TextUtils.isEmpty(entPass.getText())){
                    Toast.makeText(ProviderLogin.this,"Fill up all the fields",Toast.LENGTH_LONG).show();
                }
                else {
                    progressDialog.setMessage("Signing In");
                    progressDialog.show();
                    mail = entEmail.getText().toString();
                    pass = entPass.getText().toString();

                    login(mail,pass);
                }

            }
        });



    }
    public void login(String mail,String pass){
        mAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            DatabaseReference databaseReference = database.getReference().child("Providers").child(mAuth.getCurrentUser().getUid());

                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child("type").getValue().toString().equals("bus")){
                                        Intent intent = new Intent(ProviderLogin.this,BusProviderPanel.class);
                                        intent.putExtra("user Id",mAuth.getCurrentUser().getUid());
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();

                                    }
                                    else{
                                      Intent intent = new Intent(ProviderLogin.this,CabDrivers.class);
                                       startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }
                        else{
                            Toast.makeText(ProviderLogin.this,"You are not a registered provider",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
