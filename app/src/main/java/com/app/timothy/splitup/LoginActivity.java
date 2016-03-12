package com.app.timothy.splitup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Timothy on 1/25/2016.
 */
public class LoginActivity extends Activity
{
    private static final int LOGIN_REQUEST = 0;
    private TinyDB tinyDB;
    @Bind(R.id.sign_up_text) TextView signUp;
    @Bind(R.id.email) EditText email;
    @Bind(R.id.password) EditText password;
    @Bind(R.id.sign_in_btn) Button signInBtn;
    @Bind(R.id.skip)TextView skip;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(getApplicationContext());

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                    loginUser();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent register = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(register);
            }
        });
        skip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tinyDB.putBoolean("Logged in", true);
                finish();
            }
        });
    }

    protected boolean validate()
    {
        boolean isValid = true;
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        final String PWD_REQ = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{6,15})";

        if(emailText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            email.setError("Please enter a valid email address");
            isValid = false;
        }
        else email.setError(null);
        if(!Pattern.compile(PWD_REQ).matcher(passwordText).matches())
        {
            password.setError("Invalid Password");
            isValid = false;
        }
        else password.setError(null);
        return isValid;
    }

    public void finishUp()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    protected void loginUser()
    {

        SplitUpUser user = new SplitUpUser();

        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());

        Backendless.UserService.login(user.getEmail(), user.getPassword(), new DefaultCallback<BackendlessUser>(LoginActivity.this) {

            public void handleResponse(BackendlessUser backendlessUser)
            {
                super.handleResponse(backendlessUser);
                finishUp();
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(LoginActivity.this, "Server reported an error - " + backendlessFault.getMessage(), Toast.LENGTH_LONG).show();

            }
        }, true);
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
}

