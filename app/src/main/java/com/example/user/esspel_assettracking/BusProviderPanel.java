package com.example.user.esspel_assettracking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by user on 3/20/2018.
 */
public class BusProviderPanel extends AppCompatActivity{
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    Button showRoute;
    ProgressDialog progressDialog;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_provider_panel);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressBar = (ProgressBar)findViewById(R.id.progeresBar);
        if(mAuth.getCurrentUser()==null)
        {
            Intent intent = new Intent(this,ProviderLogin.class);
            startActivity(intent);
            finish();
        }
        else {
            DatabaseReference databaseReference = firebaseDatabase.getReference("Providers").
                    child(mAuth.getCurrentUser().getUid());
            progressDialog.setMessage("Fetching data");
            progressDialog.show();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("route")) {

                        Intent intent = new Intent(getApplicationContext(), ShowRoute.class);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                    } else {

                        Toast.makeText(BusProviderPanel.this, "Route  Doesn't Exists", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), AddRoute.class);

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

    }
}
