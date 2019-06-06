package com.bernal.jonatan.whip.Views;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.bernal.jonatan.whip.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.services.calendar.CalendarScopes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity to demonstrate basic retrieval of the Google user's ID, email address, and basic
 * profile.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 55664; //9001
    private static final int RC_REQUEST_PERMISSION_SUCCESS_CONTINUE_FILE_CREATION = 9001;
    UserLoggedIn ul;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private String URL;
    private RequestQueue requestqueue;
    private String tokenCalendar = "";


    //facebook
    private CallbackManager callbackManager;
    private LoginButton loginButtonFacebook;
    private boolean facebook = false;
    private String authCode ="";
    static int logOut=0;
    static int firstTime=1;

    public static void doLogOut(int i) {
        logOut=1;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //facebook
        //textViewFacebook = findViewById(R.id.profile_email_facebook);
        loginButtonFacebook = (LoginButton) findViewById(R.id.login_facebook_button);
        //loginButtonFacebook.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        facebook = false;
        loginButtonFacebook.setReadPermissions(Arrays.asList("email","public_profile"));
        checkLoginStatus();



        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }
            @Override
            public void onCancel() {

            }
            @Override
            public void onError(FacebookException error) {

            }
        });





        //Coneixón con la API
        URL = "https://whip-api.herokuapp.com/users/login";
        requestqueue = Volley.newRequestQueue(this);

        // Views
        mStatusTextView = findViewById(R.id.status);

        // Button listeners

        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.perfil_button).setOnClickListener(this);
        findViewById(R.id.login_facebook_button).setOnClickListener(this);
        findViewById(R.id.login_google_button).setOnClickListener(this);



        String server_client_id = "165813394161-g8paonp3kugst715s14eloh84l9lvneu.apps.googleusercontent.com";

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope("https://www.googleapis.com/auth/admin.directory.resource.calendar"))
                .requestScopes(new Scope("https://www.googleapis.com/auth/calendar"))
                .requestScopes(new Scope("https://www.googleapis.com/auth/calendar.events"))
                .requestServerAuthCode(server_client_id)
                .requestIdToken(server_client_id)
                .requestEmail()
                .build();

        // [END configure_signin]

        // The serverClientId is an OAuth 2.0 web client ID
        // Details at: https://developers.google.com/identity/sign-in/android/?utm_campaign=android_discussion_server_021116&utm_source=anddev&utm_medium=blogstart step 4

        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        SignInButton loginFacebook = findViewById(R.id.login_google_button);
        loginFacebook.setSize(SignInButton.SIZE_STANDARD);
        loginFacebook.setColorScheme(SignInButton.COLOR_LIGHT);
                // [END customize_button]

        printKeyHash();


        if(firstTime==1) {
            signOut();
            firstTime=0;
        }

        if (logOut==1) {
            signOut();
            logOut=0;
        }

    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.bernal.jonatan.whip", PackageManager.GET_SIGNATURES);

            for (Signature signature: info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        //if (!GoogleSignIn.hasPermissions(account,  new Scope(CalendarScopes.CALENDAR))) {
        GoogleSignIn.requestPermissions(
                    this,
                    RC_REQUEST_PERMISSION_SUCCESS_CONTINUE_FILE_CREATION,
                    account,

                   new Scope(CalendarScopes.CALENDAR));
        /*
        } else {
            //saveToDriveAppFolder();
       }*/
        updateUI(account);
        // [END on_start_sign_in]
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //if (facebook)  Toast.makeText(MainActivity.this,"login FACEBOOK",Toast.LENGTH_LONG).show();
        //else Toast.makeText(MainActivity.this,"login GOOGLE",Toast.LENGTH_LONG).show();
        if (facebook) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {

            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }

        }
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
        {
            //facebook
            if(currentAccessToken==null) {
                Toast.makeText(MainActivity.this,"User Logged out",Toast.LENGTH_LONG).show();

            }
            else
                loadUserProfile(currentAccessToken);
        }
    };

    private void loadUserProfile(AccessToken newAccessToken)  {
        //facebook = true;
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                //AQUI JA TINC LES DADES DEL FACEBOOK
                try {

                    String first_name = object.getString("first_name");
                    //String email = object.getString("email");

                    Toast.makeText(MainActivity.this,"User facebook Logged IN",Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this,first_name,Toast.LENGTH_LONG).show();

                    mStatusTextView.setText(getString(R.string.signed_in_fmt, first_name));

                    findViewById(R.id.login_facebook_button).setVisibility(View.GONE);
                    findViewById(R.id.login_google_button).setVisibility(View.GONE);
                    findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);

                    userJsonFacebook(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }


    private void checkLoginStatus() { //FACEBOOK

        if(AccessToken.getCurrentAccessToken()!=null)
        {
            loadUserProfile(AccessToken.getCurrentAccessToken());
            findViewById(R.id.login_facebook_button).setVisibility(View.GONE);
            findViewById(R.id.login_google_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, te));

        }
    }
    // [END onActivityResult]



    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            authCode = account.getServerAuthCode();

            //dona d alta aqui
            //Toast.makeText(getApplicationContext(), "login  per iniciar ", Toast.LENGTH_SHORT).show();

            assert account != null;
            userJsonGoogle(account);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void userJsonGoogle (final GoogleSignInAccount account) {
        JSONObject user = new JSONObject();
        try {
            user.put("mail", account.getEmail());
            String[] name = account.getDisplayName().split(" ");
            user.put("name", name[0]);
            user.put("fam_name", account.getFamilyName());
            user.put("username", "");
            user.put("photo_url", account.getPhotoUrl());


            Log.w(TAG, "token de calendar: " + tokenCalendar);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        guardarUsuari(user);
    }

    private void userJsonFacebook(JSONObject object) {
        JSONObject user = new JSONObject();
        try {

            String first_name = object.getString("first_name");
            String last_name = object.getString("last_name");
            String email = object.getString("email");
            String id = object.getString("id");
            String image_url = "https://graph.facebook.com/"+id+ "/picture?type=normal";

            user.put("mail", email);
            user.put("name", first_name);
            user.put("fam_name", last_name);
            user.put("username", "");
            user.put("photo_url", image_url);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        guardarUsuari(user);
    }
    private void guardarUsuari(JSONObject user) {

        Log.w(TAG, "guardarUsuari; facebook = " + facebook);
        Log.w(TAG, "guardarUsuari; token calendar = " + tokenCalendar);

        JsonObjectRequest objectJsonrequest = new JsonObjectRequest(
                JsonRequest.Method.POST,
                URL,
                user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Usuari logejat  correctament", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, MainMenu.class));
                        //todo guardar api key en el singleton
                        try {
                            ul = UserLoggedIn.getUsuariLogejat(response.getString("api_key"), response.getString("email"), authCode );
                            ul.setAPI_KEY(response.getString("api_key"));
                            ul.setCorreo_user(response.getString("email"));
                            ul.setToken(authCode);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "guardarUsuari : ERROOOOOOOR", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestqueue.add(objectJsonrequest);
        //if (!facebook) userPutToken();


    }

    private void userPutToken() {
      //  new RetrieveTokenTask().execute(ul.getCorreo_user()); //nomes amb google
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() { //GOOGLE
       // facebook = false;

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);





    }
    // [END signIn]

    // [START signOut]
    private void signOut() {

       // if (!facebook) {
            //GOOGLE
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]
                            updateUI(null);
                            // [END_EXCLUDE]
                        }
                    });
       // }
        //if (facebook) {
            //loginButtonFacebook.

            //TODO: Fer el logout de facebook

            LoginManager.getInstance().logOut();

            updateUI(null);
        //}
    }
    // [END signOut]



    public void updateUI(@Nullable GoogleSignInAccount account) { // GOOGLE
        //facebook = false;
        if (account != null) {
            mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));

            findViewById(R.id.login_facebook_button).setVisibility(View.GONE);
            findViewById(R.id.login_google_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);

        } else {

            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.login_facebook_button).setVisibility(View.VISIBLE);
            findViewById(R.id.login_google_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        //facebook = false;
        switch (v.getId()) {
            case R.id.login_google_button:
                facebook = false;
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.perfil_button:
                startActivity(new Intent(MainActivity.this, MainMenu.class));
                break;
            case R.id.login_facebook_button:
                facebook = true;
                break;
        }
    }

    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), RC_SIGN_IN);
            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            }
            if (ul != null) ul.setToken(token);

            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}