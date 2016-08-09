package resourcepool.io.handson_android_app.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import resourcepool.io.handson_android_app.R;
import resourcepool.io.handson_android_app.listener.RequestListener;
import resourcepool.io.handson_android_app.model.Message;
import resourcepool.io.handson_android_app.service.ChatRestApi;
import resourcepool.io.handson_android_app.service.impl.ChatRestApiImpl;
import resourcepool.io.handson_android_app.task.RefreshMessagesTask;
import resourcepool.io.handson_android_app.task.SendMessageTask;
import resourcepool.io.handson_android_app.ui.adapter.MessageAdapter;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_LOGIN = "ext_login";

    // Adapters
    private MessageAdapter mMessageAdapter;

    // Views
    private RecyclerView mMessages;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mWelcomeMessage;
    private EditText mMessageContent;
    private FloatingActionButton mSendButton;

    private SendMessageTask mSendMessageTask;
    private RefreshMessagesTask mRefreshMessagesTask;

    // Attributes
    private String currentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate layout
        setContentView(R.layout.activity_dashboard);
        // Retrieve views
        mWelcomeMessage = (TextView) findViewById(R.id.welcome_msg);
        mMessages = (RecyclerView) findViewById(R.id.message_container);
        mSendButton = (FloatingActionButton) findViewById(R.id.send_button);
        mMessageContent = (EditText) findViewById(R.id.message_content);

        mSendButton.setOnClickListener(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mMessages.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mMessages.setLayoutManager(mLayoutManager);

        // Set login message
        currentLogin = getIntent().getExtras().getString(EXTRA_LOGIN);
        mWelcomeMessage.setText(getString(R.string.welcome_user, currentLogin));


        // Create Adapter
        mMessageAdapter = new MessageAdapter();

        // Bind adapter to view
        mMessages.setAdapter(mMessageAdapter);

        // Refresh messages
        refreshMessages();

    }

    @Override
    protected void onPause() {
        cancelTasks();
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_button:
                sendMessage(mMessageContent.getText().toString());
                break;
        }
    }


    private void cancelTasks() {
        if (mRefreshMessagesTask != null) {
            mRefreshMessagesTask.cancel(true);
        }
        if (mSendMessageTask != null) {
            mSendMessageTask.cancel(true);
        }
    }

    /**
     * Refresh messages from API.
     */
    private void refreshMessages() {
        if (mRefreshMessagesTask != null && mRefreshMessagesTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            mRefreshMessagesTask.cancel(true);
        }
        mRefreshMessagesTask = new RefreshMessagesTask(new RefreshMessagesTask.MessagesRefreshListener() {
            @Override
            public void onResult(List<Message> result) {
                if (result != null) {
                    mMessageAdapter.setItems(result);
                    mMessageAdapter.notifyDataSetChanged();
                    mMessages.scrollToPosition(result.size() - 1);

                }
            }
        });
        mRefreshMessagesTask.execute();
    }

    /**
     * Send message to Chat.
     * @param message the message
     */
    private void sendMessage(String message) {
        if (mSendMessageTask != null && mSendMessageTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            mSendMessageTask.cancel(true);
        }
        mSendMessageTask = new SendMessageTask(message, new RequestListener() {
            @Override
            public void onResult(boolean success, Object result) {
                if (success) {
                    Toast.makeText(DashboardActivity.this, getString(R.string.message_sent_success), Toast.LENGTH_SHORT).show();
                    refreshMessages();
                } else {
                    Toast.makeText(DashboardActivity.this, getString(R.string.message_sent_failure), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSendMessageTask.execute();
    }
}
