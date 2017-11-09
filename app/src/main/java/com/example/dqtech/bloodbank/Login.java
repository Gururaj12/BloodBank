package com.example.dqtech.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    public static final String TAG="login";

    EditText user,password;
    Button login,registration,display;

    DatabaseReference databaseArtists;

    List<Users> userlist;

    public static final String mypreference = "mypref";

  //  public static final String Name = "nameKey";
    public static final String EMAIL = "emailKey";
    public static final String CONTACT = "contactKey";
    public static final String ADDRESS = "addressKey";
    public static final String CITY = "cityKey";
    public static final String USERNAME = "usernameKey";
    public static final String BLOOD = "bloodKey";
    public static final String GENDER = "genderKey";

    String id,name,email1,contact,address,city,gblood,gender;

    SharedPreferences sharedpreferences;
//hello
    private FirebaseAuth mAuth;
  //  public static final String mypreference = "mypref";

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.log);
        registration = (Button) findViewById(R.id.reg);
        display = (Button) findViewById(R.id.display);

        mAuth = FirebaseAuth.getInstance();


        userlist = new ArrayList<>();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                     id = user.getUid();

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    databaseArtists = FirebaseDatabase.getInstance().getReference("users");

                    sharedpreferences = getSharedPreferences(mypreference,
                            Context.MODE_PRIVATE);
                    if (sharedpreferences.contains(USERNAME)) {
                        name=sharedpreferences.getString(USERNAME, "");
                    }
                    if (sharedpreferences.contains(EMAIL)) {
                       email1=sharedpreferences.getString(EMAIL, "");

                    }
                    if (sharedpreferences.contains(CONTACT)) {
                        contact=sharedpreferences.getString(CONTACT, "");
                    }
                    if (sharedpreferences.contains(CITY)) {
                        city=sharedpreferences.getString(CITY, "");

                    }
                    if (sharedpreferences.contains(BLOOD)) {
                        gblood=sharedpreferences.getString(BLOOD, "");
                    }
                    if (sharedpreferences.contains(ADDRESS)) {
                        address=sharedpreferences.getString(ADDRESS, "");

                    }
                    if (sharedpreferences.contains(GENDER)) {
                        gender=sharedpreferences.getString(GENDER, "");

                    }


                    Users artist = new Users( id,name,email1,contact,address,city,gblood,gender);

                    //Saving the Artist
                    databaseArtists.child(id).setValue(artist);


                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(),"Succesfully signed with :"+user.getEmail()")
                    //Toast.makeText(Login.this,user.getEmail(), Toast.LENGTH_LONG)).show();
                    Toast.makeText(getApplicationContext(), user.getEmail(), Toast.LENGTH_LONG).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(getApplicationContext(), "signout", Toast.LENGTH_LONG).show();
                }
                // ...
            }
        };
    }

    private void add(){

      /*  String user_name=name.getText().toString();
        String usermail=email.getText().toString();
        String usercontact=contact.getText().toString();
        String useraddress=address.getText().toString();
        String usercity=city.getText().toString();
        String users=username.getText().toString();
        //  String userpassword=password.getText().toString();
        String usermale=male.getText().toString();
        String userfemale=fenmale.getText().toString();
        String blood=spinner.getSelectedItem().toString();

        String id=user.getUid();
        databaseArtists = FirebaseDatabase.getInstance().getReference();*/


         {

           /* //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseArtists.push().getKey();

            //creating an Artist Object
            Users artist = new Users(id, user_name,usermale,usermail,
                    usercontact,useraddress,usercity,users,blood);

            //Saving the Artist
            databaseArtists.child(id).setValue(artist);

            //setting edittext to blank again
            name.setText("");

            //displaying a success toast
          *//*  Toast.makeText(this, "Registered", Toast.LENGTH_LONG).show();

            Intent intent=new Intent(getApplicationContext(),Mydetails.class);
            Bundle b=new Bundle();
            b.putString("name",user_name);
            b.putString("email",usermail);
            b.putString("contact",usercontact);
            b.putString("address",useraddress);
            b.putString("city",usercity);
            b.putString("users",users);
            b.putString("male",usermale);
            intent.putExtras(b);*//*
*/


        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usermail=user.getText().toString();
                String userpass=password.getText().toString();
                if(!usermail.equals("") && !userpass.equals("")){

                    mAuth.signInWithEmailAndPassword(usermail, userpass);


                }

            }
        });


        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });
     /*   display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences(mypreference,MODE_PRIVATE);
                String name=sharedPreferences.getString("nameKey","");
                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
            }
        });*/







    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
