package resourcepool.io.handson_android_app.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import resourcepool.io.handson_android_app.listener.RequestListener;
import resourcepool.io.handson_android_app.model.Message;
import resourcepool.io.handson_android_app.service.ChatRestApi;
import resourcepool.io.handson_android_app.service.impl.ChatRestApiImpl;

/**
 * Created by loicortola on 08/08/2016.
 */
public class RefreshMessagesTask extends AsyncTask<Void, Void, List<Message>> {

    private static final String TAG = RefreshMessagesTask.class.getSimpleName();

    public interface MessagesRefreshListener {
        void onResult(List<Message> result);
    }

    private ChatRestApi api;
    private final MessagesRefreshListener mListener;

    public RefreshMessagesTask(MessagesRefreshListener listener) {
        this.mListener = listener;
        this.api = ChatRestApiImpl.getInstance();
    }

    @Override
    protected List<Message> doInBackground(Void... voids) {
        return api.getMessages();
    }

    @Override
    protected void onPostExecute(List<Message> result) {
        Log.w(TAG, "Result was: " + result);
        if (mListener != null) {
            mListener.onResult(result);
        }
    }
}