package com.example.fms;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.*;
import android.view.*;
import android.widget.*;

import models.Model;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;
import serverproxy.ServerProxy;

public class LoginFragment extends Fragment
{
    private String serverHost;
    private String serverPort;
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String gender;
    boolean genderChosen;

    private EditText serverHostText;
    private EditText serverPortText;
    private EditText userNameText;
    private EditText passwordText;
    private EditText emailText;
    private EditText firstnameText;
    private EditText lastnameText;
    private RadioGroup genderButtons;
    private Button registerButton;
    private Button loginButton;

    private RegisterResult registerResult = null;
    private LoginResult loginResult = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serverHost = null;
        serverPort = null;
        username = null;
        password = null;
        email = null;
        gender = null;
        genderChosen = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login_register_layout, container, false);

        serverHostText = (EditText) v.findViewById(R.id.serverHost);
        serverHostText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                    serverHost = s.toString();
                    Model.getInstance().setServerHost(serverHost);
                    registerButton.setEnabled(checkRegister());
                    loginButton.setEnabled(checkLogin());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        serverPortText = (EditText) v.findViewById(R.id.serverPort);
        serverPortText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                serverPort = s.toString();
                Model.getInstance().setServerPort(serverPort);
                registerButton.setEnabled(checkRegister());
                loginButton.setEnabled(checkLogin());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        userNameText = (EditText) v.findViewById(R.id.username);
        userNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                username = s.toString();
                registerButton.setEnabled(checkRegister());
                loginButton.setEnabled(checkLogin());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        passwordText = (EditText) v.findViewById(R.id.password);
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                password = s.toString();
                registerButton.setEnabled(checkRegister());
                loginButton.setEnabled(checkLogin());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        emailText = (EditText) v.findViewById(R.id.email);
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                email = s.toString();
                registerButton.setEnabled(checkRegister());
                loginButton.setEnabled(checkLogin());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        firstnameText = (EditText) v.findViewById(R.id.firstname);
        firstnameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                firstname = s.toString();
                registerButton.setEnabled(checkRegister());
                loginButton.setEnabled(checkLogin());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        lastnameText = (EditText) v.findViewById(R.id.lastname);
        lastnameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                lastname = s.toString();
                registerButton.setEnabled(checkRegister());
                loginButton.setEnabled(checkLogin());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        genderButtons = (RadioGroup) v.findViewById(R.id.radioGroup);
        genderButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.maleButton)
                {
                    gender = "Male";
                    genderChosen = true;
                }
                else if(checkedId == R.id.femaleButton)
                {
                    gender = "Female";
                    genderChosen = true;
                }
                else
                {
                    genderChosen = false;
                }

                registerButton.setEnabled(checkRegister());
                loginButton.setEnabled(checkLogin());
            }
        });

        registerButton = (Button) v.findViewById(R.id.register);
        registerButton.setEnabled(false);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkRegister())
                {
                    Toast.makeText(getActivity(), "Registering User",
                            Toast.LENGTH_LONG).show();

                    RegisterTask task = new RegisterTask();
                    RegisterRequest request = createRegisterRequest();
                    task.execute(request);

                }
                else {
                    Toast.makeText(getActivity(), "Please add more information",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        loginButton = (Button) v.findViewById(R.id.signIn);
        loginButton.setEnabled(false);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLogin())
                {
                    Toast.makeText(getActivity(), "Logging in User",
                            Toast.LENGTH_LONG).show();

                    LoginTask task = new LoginTask();
                    LoginRequest request = createLoginRequest();
                    task.execute(request);
                }
                else {
                    Toast.makeText(getActivity(), "Please add more information",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    private class RegisterTask extends AsyncTask<RegisterRequest, Void, Long> {
        protected Long doInBackground(RegisterRequest... requests) {
            long totalSize = 0;

            RegisterRequest request = requests[0];

            ServerProxy server = new ServerProxy();
            server.setServerHost(serverHost);
            server.setServerPort(serverPort);

            registerResult = server.register(request);

            return totalSize;
        }

        protected void onProgressUpdate() {}

        protected void onPostExecute(Long result) {
            if(registerResult.getErrorMessage() == null)
            {
                String message = "Successfully registered " + firstname + " " + lastname;
                Toast.makeText(getActivity(), message,
                        Toast.LENGTH_LONG).show();

                ((MainActivity)getActivity()).swapToMap();
            }
            else
            {
                Toast.makeText(getActivity(), "Could not register User",
                        Toast.LENGTH_LONG).show();
            }

            Model.getInstance().setLoggedIn(true);
        }
    }

    private class LoginTask extends AsyncTask<LoginRequest, Void, Long> {

        protected Long doInBackground(LoginRequest... requests) {
            long totalSize = 0;

            LoginRequest request = requests[0];

            ServerProxy server = new ServerProxy();
            server.setServerHost(serverHost);
            server.setServerPort(serverPort);

            loginResult = server.login(request);

            return totalSize;
        }

        protected void onProgressUpdate() {}

        protected void onPostExecute(Long result) {

            if(loginResult.getErrorMessage() == null)
            {

                String message = "Successfully logged in " + firstname + " " + lastname;
                Toast.makeText(getActivity(), message,
                        Toast.LENGTH_LONG).show();

                ((MainActivity)getActivity()).swapToMap();
            }
            else
            {
                Toast.makeText(getActivity(), "Could not log in User",
                        Toast.LENGTH_LONG).show();
            }

            Model.getInstance().setLoggedIn(true);
        }
    }

    private RegisterRequest createRegisterRequest()
    {
        RegisterRequest request = new RegisterRequest();
        request.setUserName(username);
        request.setPassword(password);
        request.setEmail(email);
        request.setFirstName(firstname);
        request.setLastName(lastname);
        request.setGender(gender);

        return request;
    }

    private LoginRequest createLoginRequest()
    {
        LoginRequest request = new LoginRequest();
        request.setUserName(username);
        request.setPassword(password);
        return request;
    }

    private boolean checkRegister()
    {
        if((serverHost != null) && (serverPort != null) && (username != null) && (password != null) &&
        (email != null) && (firstname != null) && (lastname != null) && (gender != null) && genderChosen
        && (!serverHost.isEmpty()) && (!serverPort.isEmpty()) && (!username.isEmpty()) && (!password.isEmpty())
        && (!email.isEmpty()) && (!firstname.isEmpty()) && (!lastname.isEmpty()))
        return true;

        else
            return false;
    }

    private boolean checkLogin()
    {
        if((username != null) && (password != null) && (!username.isEmpty())&& (!password.isEmpty())
        && (serverPort != null) && (serverHost != null) && (!serverHost.isEmpty()) && (!serverPort.isEmpty()))
            return true;
        else
            return false;
    }
}
