package com.example.user.esspel_assettracking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddRoute extends AppCompatActivity {

    private LinearLayout parentLinearLayout;
    private Button addWayPoint;
    EditText entSrc;
    EditText entDest;
    EditText entWaypoint;
    String way;
    ArrayList<String> arrayList;
    String buscode;
    Bundle bundle;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setroutes);
        parentLinearLayout = (LinearLayout) findViewById(R.id.setRouteLayout);
      arrayList = new ArrayList<String>();

      database = FirebaseDatabase.getInstance();
      firebaseAuth = FirebaseAuth.getInstance();








    }
    public  void addWayPoint(View v){


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);

    }
    public void submitRoute(View v){
        Log.e("Number",Integer.toString(parentLinearLayout.getChildCount()));

        DatabaseReference databaseReference1 = database.getReference().child("Providers").child(firebaseAuth.getCurrentUser().getUid());
        arrayList.clear();
        int n=0;
        databaseReference1.child("waypoints").setValue(null);
        for (int i = 0; i < parentLinearLayout.getChildCount(); i++) {
            View child = parentLinearLayout.getChildAt(i);
            if (child instanceof LinearLayout ) {
                for(int j=0;j<((LinearLayout) child).getChildCount();j++)
                {
                    View childIn = ((LinearLayout) child).getChildAt(j);
                    if(childIn instanceof EditText)
                    {
                        if(!TextUtils.isEmpty(((EditText) childIn).getText())) {
                            // Log.e("Value Of J",Integer.toString(n));
                            DatabaseReference databaseReference = database.getReference().child("Providers").child(firebaseAuth.getCurrentUser().getUid());

                            databaseReference.child("waypoints").child(Integer.toString(n)).setValue(((EditText) childIn).getText().toString());

                            String waypoints = ((EditText) childIn).getText().toString().replace(" ", "%20");
                            n++;

                            arrayList.add(waypoints);
                        }else {
                            Toast.makeText(getApplicationContext(),"Fill Up All The Fields",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            }
        Intent intent = new Intent(AddRoute.this,MainActivity.class);
        intent.putStringArrayListExtra("waypoints",arrayList);
        intent.putExtra("buscode",pid);
        startActivity(intent);

    }

}
