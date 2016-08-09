package resourcepool.io.handson_android_app.task;

import android.os.AsyncTask;
import android.util.Log;

import resourcepool.io.handson_android_app.service.ChatRestApi;
import resourcepool.io.handson_android_app.service.impl.ChatRestApiImpl;
import resourcepool.io.handson_android_app.model.User;

/**
 * Created by loicortola on 08/08/2016.
 */
public class LoginTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = LoginTask.class.getSimpleName();


    private final String password;
    private final String login;
    private ChatRestApi api;
    private LoginListener mListener;

    public interface LoginListener {
        void onLoginAttempt();
        void onLoginResult(boolean success, User user);

    }

    public LoginTask(String login, String password, LoginListener listener) {
        this.login = login;
        this.password = password;
        this.mListener = listener;
        this.api = ChatRestApiImpl.getInstance();
    }

    @Override
    protected void onPreExecute() {
        if (mListener != null) {
            mListener.onLoginAttempt();
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return api.connect(login, password);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.w(TAG, "Result was: " + result);
        if (mListener != null) {
            mListener.onLoginResult(result, new User(login, password));
        }
    }
}