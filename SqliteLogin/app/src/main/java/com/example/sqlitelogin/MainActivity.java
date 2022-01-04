package com.example.sqlitelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EditText password, repassword, username;
        Button signup,signin;
        DBHelper DB;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =(EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);
        repassword=(EditText) findViewById(R.id.repassword);
        signup=(Button) findViewById(R.id.btnsignup);
        signin=(Button) findViewById(R.id.btnsignin);
        DB = new DBHelper(this);

        //listener to input username and pass and retyped pass
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass=repassword.getText().toString();

                //if any field is blank show the toast message
                if (user.equals("") || pass.equals("") || repass.equals("")) Toast.makeText(MainActivity.this,"All Fields Are Required",Toast.LENGTH_SHORT).show();
                else {
                    //when all the fields are filled
                    //check whether the passwords matched or not
                    if (pass.equals(repass)){
                        //checking if the username exist or not using the checkusername function
                        Boolean checkuser = DB.checkusername(user);
                        //if it doesnot exist then insert in the DB using insertdata function
                        if (checkuser==false){
                            Boolean insert =DB.insertData(user,pass);
                            //if the data has been written we will show the text message
                            if (insert==true){
                                //if all okay then take to the home page
                                Toast.makeText(MainActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "User Already Exists! Please Sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Passwords Not Matching!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        //start the login activity that we defined as well
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}