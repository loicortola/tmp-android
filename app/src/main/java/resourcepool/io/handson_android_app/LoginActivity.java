package resourcepool.io.handson_android_app;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.health.ServiceHealthStats;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginTask.LoginListener {

    private Button mSendButton;
    private Button mResetButton;
    private EditText mLogin;
    private EditText mPassword;
    private ProgressBar mProgressBar;
    private LoginTask mLoginTask;

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

        // Bind actions to views
        bindViews();

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
                if (mLoginTask != null && mLoginTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                    // Ensure that any running task is canceled before starting new
                    mLoginTask.cancel(true);
                }
                mLoginTask = new LoginTask(mLogin.getText().toString(), mPassword.getText().toString(), this);
                mLoginTask.execute();
                break;
            case R.id.reset_button:
                mLogin.setText("");
                mPassword.setText("");
                break;
        }
    }


    /**
     * LoginTask callbacks
     */
    @Override
    public void onLoginAttempt() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginResult(boolean success) {
        mProgressBar.setVisibility(View.GONE);
        if (success) {
            Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
        }
    }
}
