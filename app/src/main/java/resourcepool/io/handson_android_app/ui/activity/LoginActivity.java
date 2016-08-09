package resourcepool.io.handson_android_app.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import resourcepool.io.handson_android_app.task.LoginTask;
import resourcepool.io.handson_android_app.R;
import resourcepool.io.handson_android_app.model.User;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginTask.LoginListener {

    public static final String PREF_IS_REGISTERED = "pref_registered";
    public static final String PREF_LOGIN = "pref_login";
    public static final String PREF_PASSWORD = "pref_password";

    private Button mSendButton;
    private Button mResetButton;
    private EditText mLogin;
    private EditText mPassword;
    private ProgressBar mProgressBar;
    private LoginTask mLoginTask;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate activity layout
        setContentView(R.layout.activity_login);

        // Retrieve views
        mLogin = (EditText) findViewById(R.id.login);
        mPassword = (EditText) findViewById(R.id.password);
        mSendButton = (Button) findViewById(R.id.send_button);
        mResetButton = (Button) findViewById(R.id.reset_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // Retrieve Shared Preferences
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Bind actions to views
        bindViews();

        // Attempt login if registered
        loginIfRegistered();

    }

    @Override
    protected void onPause() {
        mLoginTask.cancel(true);
        super.onPause();
    }

    private void bindViews() {
        mSendButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_button:
                attemptLogin(mLogin.getText().toString(), mPassword.getText().toString());
                break;
            case R.id.reset_button:
                mLogin.setText("");
                mPassword.setText("");
                break;
        }
    }

    /**
     * Login if application is already registered.
     */
    private void loginIfRegistered() {
        if (mSharedPreferences.getBoolean(PREF_IS_REGISTERED, false)) {
            String login = mSharedPreferences.getString(PREF_LOGIN, "");
            String password = mSharedPreferences.getString(PREF_PASSWORD, "");
            mLogin.setText(login);
            mPassword.setText(password);
            attemptLogin(login, password);
        }
    }

    /**
     * Attempt login.
     * @param login the login
     * @param password the password
     */
    private void attemptLogin(String login, String password) {
        if (mLoginTask != null && mLoginTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            // Ensure that any running task is canceled before starting new
            mLoginTask.cancel(true);
        }
        mLoginTask = new LoginTask(login, password, this);
        mLoginTask.execute();
    }


    /**
     * LoginTask callbacks
     */
    @Override
    public void onLoginAttempt() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginResult(boolean success, User user) {
        mProgressBar.setVisibility(View.GONE);
        if (success) {
            // Save user in shared preferences
            saveUser(user);

            // Notify login status
            Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();

            // Launch Dashboard
            launchDashboard();
        } else {
            // Notify login status
            Toast.makeText(LoginActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Launch dashboard activity.
     */
    private void launchDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(DashboardActivity.EXTRA_LOGIN, mLogin.getText().toString());
        startActivity(intent);
    }

    /**
     * Save user into shared preferences.
     * @param user the user to persist
     */
    private void saveUser(User user) {
        mSharedPreferences.edit()
                .putBoolean(PREF_IS_REGISTERED, true)
                .putString(PREF_LOGIN, user.getLogin())
                .putString(PREF_PASSWORD, user.getPassword())
                .apply();
    }
}
