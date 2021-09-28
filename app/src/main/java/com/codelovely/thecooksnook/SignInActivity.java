package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codelovely.thecooksnook.data.entities.User;
import com.codelovely.thecooksnook.models.UserModel;
import com.codelovely.thecooksnook.viewmodels.SignInViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    TextView statusTextView;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private SignInViewModel mSignInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSignInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("25884934595-v6hdvactber2deeecgd4q6qsroiahnc2.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.signIn_signInButton).setOnClickListener(this);
    }

    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    protected void updateUI(GoogleSignInAccount account) {
    if(account != null ) {

        String displayName = account.getDisplayName();
        final String familyName = account.getFamilyName();
        final String givenName = account.getGivenName();
        final String personId = account.getId();
        /*
        UserModel user = new UserModel();
        user.setFirstName(givenName);
        user.setLastName(familyName);
        user.setUserId(personId);

         */
        UserModel userModel = new UserModel();
        userModel.setFirstName("Default user");
        userModel.setLastName("Default user");
        userModel.setUserId("Default user");
        mSignInViewModel.insertUser(userModel);

        System.out.println("Display name: " + displayName);
        System.out.println("Family name: " + familyName);
        System.out.println("Given name: " + givenName);
        System.out.println("ID: " + personId);


        ValueEventListener changeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User();
                user.setFirstName(givenName);
                user.setLastName(familyName);
                user.setUserId(personId);

                if (! dataSnapshot.hasChild("/users/" + personId)) {
                    dataSnapshot.getRef().child("users").child(personId).setValue(user);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = database.getReference();
        dbReference.addValueEventListener(changeListener);

        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
    else {
        Toast.makeText(this,"That account was null",Toast.LENGTH_LONG).show();
    }
/*
If GoogleSignIn.getLastSignedInAccount returns null, the user has not yet signed in to your app with Google. Update your UI to display the Google Sign-in button.
         */
    }

    private void signIn() {
        // Launches the sign in flow, the result is returned in onActivityResult
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signIn_signInButton:
                signIn();
                break;
            // ...
        }
    }
}