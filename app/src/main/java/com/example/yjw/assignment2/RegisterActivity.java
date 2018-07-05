package com.example.yjw.assignment2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yjw on 2018/4/21.
 */

public class RegisterActivity extends AppCompatActivity {

    private int resId;
    private int year = 1960, month = 5, date = 5;
    private String datePicked;
    private String pCode = "Please choose";
    private Button bnSetDate;
    private Button btSignup;
    private Spinner spSuburb;

    private TextView tvUserName, tvPassword, tvCPassword,
            tvFName, tvLName, tvStreet, tvEmail, tvDOB,
            tvMobile, tvEnergyProvider, tvNoOfRes, tvSuburb, tvPostCode, postCode;

    private EditText userName, password, confirmPassword,
            edFName, edLName, edStreet, edEmail,edMobile,
            edEnergyProvider, edNoOfRes;

    private boolean validUserName = false, validPassowrd = false,
            validCPassword = false, validFName = false, validLName = false,
            validStreet = false, validEmail = false, validSuburb = false,
            validDOB = false, validMobile, validEnergyProvider = false,
            validNoOfRes = false, validPostCode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        spSuburb = (Spinner) findViewById(R.id.spSuburb);

        btSignup = (Button) findViewById(R.id.btSignup);
        bnSetDate = (Button) findViewById(R.id.bnSetDate);
        btSignup.setFocusableInTouchMode(true);

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvPassword = (TextView) findViewById(R.id.tvPssword);
        tvCPassword = (TextView) findViewById(R.id.tvCPasswrod);
        tvFName = (TextView) findViewById(R.id.tvFName);
        tvLName = (TextView) findViewById(R.id.tvLName);
        tvStreet = (TextView) findViewById(R.id.tvStreet);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvDOB = (TextView) findViewById(R.id.tvDOB);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvEnergyProvider = (TextView) findViewById(R.id.tvEnergyProvider);
        tvNoOfRes = (TextView) findViewById(R.id.tvNoOfRes);
        tvSuburb = (TextView) findViewById(R.id.tvSuburb);
        tvPostCode = (TextView) findViewById(R.id.tvPostCode);
        postCode = (TextView) findViewById(R.id.postCode);

        userName = (EditText) findViewById(R.id.edUserName);
        password = (EditText) findViewById(R.id.edPassword);
        confirmPassword = (EditText) findViewById(R.id.edCPassword);
        edFName = (EditText) findViewById(R.id.edFName);
        edLName = (EditText) findViewById(R.id.edLName);
        edStreet = (EditText) findViewById(R.id.edStreet);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edMobile = (EditText) findViewById(R.id.edMobile);
        edEnergyProvider = (EditText) findViewById(R.id.edEnergyProvider);
        edNoOfRes = (EditText) findViewById(R.id.edNoOfRes);

        userName.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    if (!isEmpty(userName.getText().toString())){
                        new AsyncTask<String, Void, Boolean>() {

                            @Override
                            protected Boolean doInBackground(String... params) {
                                return RestClient.isExist(params[0].toLowerCase(),"userName");
                            }
                            @Override
                            protected void onPostExecute(Boolean exist) {
                                if (exist) {
                                    tvUserName.setText("User Name-User Name Exist");
                                    tvUserName.setTextColor(0xFFFF4081);
                                    validUserName = false;
                                }
                                else {
                                    tvUserName.setText("User Name");
                                    tvUserName.setTextColor(0xFF303F9F);
                                    validUserName = true;
                                }
                            }
                        }.execute(userName.getText().toString());
                    } else {
                        tvUserName.setText("User Name-Required");
                        tvUserName.setTextColor(0xFFFF4081);
                        validUserName = false;
                    }
                }
            }
        });

        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!isEmpty(confirmPassword.getText().toString())) {
                        if (confirmPassword.getText().toString().equals(password.getText().toString())) {
                            tvCPassword.setText("Confirm Password");
                            tvCPassword.setTextColor(0xFF303F9F);
                            validCPassword = true;
                        } else {
                            tvCPassword.setText("Confirm Password-Not Match");
                            tvCPassword.setTextColor(0xFFFF4081);
                            validCPassword = false;
                        }
                    } else {
                        tvCPassword.setText("Confirm Password-Required");
                        tvCPassword.setTextColor(0xFFFF4081);
                        validCPassword = false;
                    }
                }
            }
        });

        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            final String pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!isEmpty(edEmail.getText().toString())) {
                        String email = edEmail.getText().toString();
                        Pattern r =Pattern.compile(pattern);
                        Matcher m = r.matcher(email);
                        if (!m.find()) {
                            tvEmail.setText("Email format is incorrect");
                            tvEmail.setTextColor(0xFFFF4081);
                            validEmail = false;
                        } else {
                            tvEmail.setText("Email Address");
                            tvEmail.setTextColor(0xFF303F9F);
                            validEmail = true;
                        }
                    } else {
                        tvEmail.setText("Email Address-Required");
                        tvEmail.setTextColor(0xFFFF4081);
                        validEmail = false;
                    }
                }
            }
        });

        edMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            final String pattern = "^04+[0-9]{8}$";
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!isEmpty(edMobile.getText().toString())) {
                        String mobile = edMobile.getText().toString();
                        Pattern r =Pattern.compile(pattern);
                        Matcher m = r.matcher(mobile);
                        if (!m.find()) {
                            tvMobile.setText("Mobile format is incorrect");
                            tvMobile.setTextColor(0xFFFF4081);
                            validMobile = false;
                        } else {
                            tvMobile.setText("Mobile");
                            tvMobile.setTextColor(0xFF303F9F);
                            validMobile = true;
                        }
                    } else {
                        tvMobile.setText("Mobile-Required");
                        tvMobile.setTextColor(0xFFFF4081);
                        validMobile = false;
                    }
                }
            }
        });

        edNoOfRes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            final String pattern = "^[1-9]\\d*$";
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!isEmpty(edNoOfRes.getText().toString())) {
                        String mobile = edNoOfRes.getText().toString();
                        Pattern r =Pattern.compile(pattern);
                        Matcher m = r.matcher(mobile);
                        if (!m.find()) {
                            tvNoOfRes.setText("Number of Resident is bigger than 0");
                            tvNoOfRes.setTextColor(0xFFFF4081);
                            validNoOfRes = false;
                        } else {
                            tvNoOfRes.setText("Number of Resident");
                            tvNoOfRes.setTextColor(0xFF303F9F);
                            validNoOfRes = true;
                        }
                    } else {
                        tvNoOfRes.setText("Number of Resident-Required");
                        tvNoOfRes.setTextColor(0xFFFF4081);
                        validNoOfRes = false;
                    }
                }
            }
        });

        spSuburb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pCode = getPostCode(spSuburb.getSelectedItem().toString());
                postCode.setText(pCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        hasFocus(password,tvPassword,"validPassowrd");
        hasFocus(edFName,tvFName,"validFName");
        hasFocus(edLName,tvLName,"validLName");
        hasFocus(edStreet,tvStreet,"validStreet");
        hasFocus(edEnergyProvider,tvEnergyProvider,"validEnergyProvider");



        bnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, dateListener, year, month, date).show();
            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Debug
                System.out.println(validUserName+ "," +validFName+ "," +validLName+ "," +validEmail+ "," +validCPassword
                        + "," + validStreet);
                if (validUserName && validCPassword && validFName && validLName && validStreet && validEmail
                        && validMobile && validEnergyProvider && validNoOfRes) {
                    if (validDOB && validPostCode) {
                        new AsyncTask<Void, Void, Resident>(){

                            @Override
                            protected Resident doInBackground(Void... params) {
                                resId = RestClient.getMaxId() + 1;
                                Resident resident = new Resident(resId,edFName.getText().toString(),edLName.getText().toString(),edStreet.getText().toString(),
                                        datePicked, edEmail.getText().toString(),edMobile.getText().toString(),edEnergyProvider.getText().toString(),
                                        pCode,edNoOfRes.getText().toString());
                                Credentials credential = new Credentials(resId, userName.getText().toString(),
                                        Encryption.convertSHA1(confirmPassword.getText().toString()), Time.getCurrentFormatedDate() + "T00:00:00+10:00");

                                System.out.println(resident.toString());
                                System.out.println(credential.toCString(resident.toString()));

                                RestClient.createResident(resident.toString());
                                RestClient.createCredential(credential.toCString(resident.toString()));

                                return resident;
                            }
                            @Override
                            protected void onPostExecute(Resident resident){
                                showText("Welcome, " + userName.getText().toString());
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                //JsonArray logedUser = new JsonParser().parse(resident).getAsJsonArray();
                                //Resident resident = new Resident(logedUser);
                                intent.putExtra("user",resident);
                                startActivity(intent);
                                finish();
                            }
                        }.execute();
                    } else if (!validDOB) {
                        tvDOB.setText("Date of Birth-Incorrect Date");
                        tvDOB.setTextColor(0xFFFF4081);
                    } else if (!validPostCode){
                        tvSuburb.setText("Suburb-Required");
                        tvSuburb.setTextColor(0xFFFF4081);
                    }

                }
            }
        });

    }

    public boolean hasFocus(final EditText ed, final TextView tv, final String valid) {
        boolean s = false;
        final String tvTextOriginal = tv.getText().toString();
        ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean b1 = false;
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!isEmpty(ed.getText().toString())) {
                        tv.setText(tvTextOriginal);
                        tv.setTextColor(0xFF303F9F);
                        switch(valid){
                            case "validPassword":
                                validPassowrd = true; break;
                            case "validFName":
                                validFName = true; break;
                            case "validLName":
                                validLName = true; break;
                            case "validStreet":
                                validStreet = true; break;
                            case "validEnergyProvider":
                                validEnergyProvider = true; break;
                        }
                    } else {
                        tv.setText(tvTextOriginal + "-Required");
                        tv.setTextColor(0xFFFF4081);
                        switch(valid){
                            case "validPassword":
                                validPassowrd = false; break;
                            case "validFName":
                                validFName = false; break;
                            case "validLName":
                                validLName = false; break;
                            case "validStreet":
                                validStreet = false; break;
                            case "validEnergyProvider":
                                validEnergyProvider = false; break;
                        }
                    }
                }
            }
        });
        return s;
    }

    public boolean isEmpty(String input) {
        boolean empty = true;
        if (!input.trim().equals(""))
            empty = false;
        return empty;
    }

    public String getPostCode(String suburb) {
        String postCode = "Please Choose";
        switch (suburb) {
            case "Armadale": postCode = "3143"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Ashburton": postCode = "3147"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Ashwood": postCode = "3147"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Aspendale": postCode = "3195"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Balaclava": postCode = "3183"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Bangholme": postCode = "3175"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Bayswater": postCode = "3153"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Beaumaris": postCode = "3193"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Belgrave": postCode = "3160"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Bentleigh": postCode = "3204"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Bonbeach": postCode = "3196"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Boronia": postCode = "3155"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Braeside": postCode = "3195"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Brighton": postCode = "3186"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Carlton": postCode = "3053"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Carnegie": postCode = "3163"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Carrum": postCode = "3197"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Caulfield": postCode = "3162"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Chadstone": postCode = "3148"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Chelsea": postCode = "3196"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Cheltenham": postCode = "3192"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Clarinda": postCode = "3169"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Clayton": postCode = "3168"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Dandenong": postCode = "3175"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Doveton": postCode = "3177"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Edithvale": postCode = "3196"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Elsternwick": postCode = "3185"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Elwood": postCode = "3184"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Eumemmerring": postCode = "3177"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Footscray": postCode = "3011"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Frankston": postCode = "3199"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Gardenvale": postCode = "3185"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Hampton": postCode = "3188"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Heatherton": postCode = "3202"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Highett": postCode = "3190"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Hughesdale": postCode = "3166"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Huntingdale": postCode = "3166"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Keysborough": postCode = "3173"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Knoxfield": postCode = "3180"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Kooyong": postCode = "3144"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Lysterfield": postCode = "3156"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "McKinnon": postCode = "3204"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Mentone": postCode = "3194"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Moorabbin": postCode = "3189"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Mordialloc": postCode = "3195"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Mulgrave": postCode = "3170"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Murrumbeena": postCode = "3163"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Oakleigh": postCode = "3166"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Ormond": postCode = "3204"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Parkdale": postCode = "3195"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Richmond": postCode = "3121"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Ripponlea": postCode = "3185"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Rowville": postCode = "3178"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Sandringham": postCode = "3191"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Scoresby": postCode = "3179"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Seaford": postCode = "3198"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Selby": postCode = "3159"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Springvale": postCode = "3171"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Tecoma": postCode = "3160"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Toorak": postCode = "3142"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Upwey": postCode = "3158"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Wantirna": postCode = "3152"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Waterways": postCode = "3195"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;
            case "Windsor": postCode = "3181"; tvSuburb.setText("Suburb"); tvSuburb.setTextColor(0xFF303F9F); validPostCode = true; break;



            default: validPostCode = false; tvSuburb.setText("Suburb-Required"); tvSuburb.setTextColor(0xFFFF4081);break;
        }
        return postCode;
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear, int newMonth, int newDate) {
            year = newYear;
            month = newMonth;
            date = newDate;
            String displayDate = date + "/" + Integer.toString(month + 1) + "/" + year;

            Date temp = Time.toDate(displayDate, "dd/MM/yyyy");
            if (temp.after(Time.getCurrentDate())) {
                tvDOB.setText("Date of Birth-Incorrect Date");
                tvDOB.setTextColor(0xFFFF4081);
                validDOB = false;
            } else {
                String strDay = "";
                String strMonth = "";
                if (date < 10)
                    strDay = "0" + date;
                else
                    strDay = date + "";
                if (month + 1 < 10)
                    strMonth = "0" + Integer.toString(month + 1);
                else
                    strMonth = month + "";

                datePicked = year + "-" + strMonth + "-" + strDay + "T00:00:00+10:00";
                bnSetDate.setText("Date of birth: " + displayDate);
                tvDOB.setText("Date of Birth");
                tvDOB.setTextColor(0xFF303F9F);
                validDOB = true;
            }
        }
    };

    public void showText(String string) {
        Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_SHORT).show();
    }
}
