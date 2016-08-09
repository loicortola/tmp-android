package resourcepool.io.handson_android_app.task;

import android.os.AsyncTask;
import android.util.Log;

import resourcepool.io.handson_android_app.listener.RequestListener;
import resourcepool.io.handson_android_app.model.User;
import resourcepool.io.handson_android_app.service.ChatRestApi;
import resourcepool.io.handson_android_app.service.impl.ChatRestApiImpl;

/**
 * Created by loicortola on 08/08/2016.
 */
public class SendMessageTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = SendMessageTask.class.getSimpleName();


    private final String message;
    private ChatRestApi api;
    private RequestListener mListener;

    public SendMessageTask(String message, RequestListener listener) {
        this.mListener = listener;
        this.message = message;
        this.api = ChatRestApiImpl.getInstance();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return api.sendMessage(message);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.w(TAG, "Result was: " + result);
        if (mListener != null) {
            mListener.onResult(result, null);
        }
    }
}