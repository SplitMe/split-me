package com.example.timothy.splitme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Timothy on 2/1/2016.
 */
public class RegistrationActivity extends Activity
{
    @Bind(R.id.email_reg) EditText email;
    @Bind(R.id.name_reg)EditText name;
    @Bind(R.id.password_reg)EditText password;
    @Bind(R.id.reg_btn) Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);
        ButterKnife.bind(this);
        String appVer = "v1";
        Backendless.initApp(this, "@string/backendless_app_id", "@string/backendless_api_key", appVer);

        regBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerUserAsync();
            }
        });
    }

    protected boolean validate()
    {
        boolean isValid = true;
        String emailText = email.getText().toString().trim();
        String nameText = name.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        final String PWD_REQ = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{6,15})";

        if(emailText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            email.setError("Please enter a valid email address");
            isValid = false;
        }
        else email.setError(null);
        if(name.length() < 3)
        {
            name.setError("Must be atleast 3 characters");
            isValid = false;
        }
        else name.setError(null);
        if(!Pattern.compile(PWD_REQ).matcher(passwordText).matches())
        {
            password.setError("Must be 6-15 characters 1 upper/lower letter, 1 number, and 1 special character");
            isValid = false;
        }
        else password.setError(null);
        return isValid;
    }

    protected void registerUserAsync() {

        BackendlessUser user = new BackendlessUser();

        if(validate())
        {
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user.setProperty("name", name.getText().toString());

            Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser registeredUser) {
                    finish();
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    Toast.makeText(RegistrationActivity.this, "Server reported an error - " + backendlessFault.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
