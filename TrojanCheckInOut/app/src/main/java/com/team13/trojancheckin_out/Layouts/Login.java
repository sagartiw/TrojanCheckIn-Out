package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.team13.trojancheckin_out.Accounts.R;

public class Login extends AppCompatActivity {

    private Button Login;
    private Button Back;
    final static int GOOGLE_REQUEST_CODE = 0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //GOOGLE AUTHENTICATION
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login = findViewByID(R.id.login);

        //requests email through google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient loginClient = GoogleSignIn.getClient(this.gso);
        GoogleSignInAccount loginAccount = GoogleSignIn.getLastSignedInAccount(this);
        //User already signed in
        if (loginAccount != null){
            Toast.makeText(this, "User is signed in already", Toast.LENGTH_SHORT).show();
        }
        Login.setOnClickListener(new View.onClickListener(){
            @Override
                public void onClick(View v) {
                Intent sign = loginClient.getSignInIntent();
                startActivityForResult(sign, GOOGLE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_REQUEST_CODE){
            Task<GoogleSignInAccount> loginTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount loginAcct = loginTask.getResult(ApiException.class);
                Toast.makeText(this, "You are now logged in", Toast.LENGTH_SHORT).show();
            }
            catch (ApiException msg){
                e.printStackTrace();
            }

        }
    }
}