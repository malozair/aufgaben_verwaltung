package de.hsos.prog3.hausarbeit.ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.hsos.prog3.hausarbeit.R;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText, emailEditText,passwordEditText, confirmPasswordEditText;
    Button registerButton;
    SharedPreferences sp;
    String username,email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        usernameEditText= findViewById(R.id.username);
        emailEditText= findViewById(R.id.email);
        passwordEditText= findViewById(R.id.password);
        confirmPasswordEditText= findViewById(R.id.confirm_password);
        registerButton= findViewById(R.id.register);

        sp=getSharedPreferences("MyUserprefs", Context.MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username= usernameEditText.getText().toString();
                email= emailEditText.getText().toString();
                password= passwordEditText.getText().toString();
                confirmPassword= confirmPasswordEditText.getText().toString();

                if (validateInput (username,email,password,confirmPassword)) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("username", username);
                editor.commit();
                Toast.makeText(RegisterActivity.this, "Hallo "+ username, Toast.LENGTH_LONG).show();



            }
        });
    }
    private boolean validateInput(String username, String email, String password, String confirmPassword){

        if (username.isEmpty() || email.isEmpty()|| password.isEmpty()|| confirmPassword.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(RegisterActivity.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)){
            Toast.makeText(RegisterActivity.this, "Password do not match.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



}