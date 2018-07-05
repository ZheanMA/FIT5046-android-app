package com.example.yjw.assignment2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * Created by yjw on 2018/4/18.
 */



public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passWord;
    private Button loginButton;
    private TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        userName = (EditText) findViewById(R.id.usernameText);
        passWord = (EditText) findViewById(R.id.passwordText);
        signupLink = (TextView) findViewById(R.id.registerText);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {



                if (!userName.getText().toString().trim().equals("") && !passWord.getText().toString().trim().equals("")) {
                    String userNameString = userName.getText().toString().trim();
                    //Send encrypted password to server
                    String encryPW = Encryption.convertSHA1(passWord.getText().toString());



                    new AsyncTask<String, Void, String>() {

                        @Override
                        protected String doInBackground(String... params) {
                            //test create
                            //RestClient.createResident("{\"address\":\"5 Martin Street Brighton\",\"dob\":\"1961-06-05T00:00:00+10:00\",\"email\":\"jeremyclarkson@outlook.com\",\"firstname\":\"Jeremy\",\"mobile\":\"0458963584\",\"nameofenergyprovider\":\"Ocean Energy\",\"numofresident\":3,\"postcode\":\"3186\",\"resid\":8,\"surname\":\"Clarkson\"}");
                            return RestClient.getUserInfo(params[0], params[1]);
                        }
                        @Override
                        protected void onPostExecute(String userInfo){
                            if (userInfo.equals("")){
                                showText("User Name/Passoword is not valid.");
                            } else {
                                System.out.println(Encryption.convertSHA1("123456"));
                                showText("Welcome, " + userName.getText().toString());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                JsonArray logedUser = new JsonParser().parse(userInfo).getAsJsonArray();
                                Resident resident = new Resident(logedUser);
                                intent.putExtra("user",resident);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }.execute(new String[] {userNameString, encryPW});
                } else {
                    showText("User name/password can not be empty!");
                }
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }



    public void showText(String string) {
        Toast.makeText(LoginActivity.this, string, Toast.LENGTH_SHORT).show();
    }






}
