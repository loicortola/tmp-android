package resourcepool.io.handson_android_app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import resourcepool.io.handson_android_app.R;
import resourcepool.io.handson_android_app.model.Message;

/**
 * Created by loicortola on 08/08/2016.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    List<Message> messages = Collections.emptyList();

    /**
     * The ViewHolder holds references to the views and provides fast retrieval features
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView authorView;
        public final TextView messageView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.authorView = (TextView) itemView.findViewById(R.id.author);
            this.messageView = (TextView) itemView.findViewById(R.id.message);
        }
    }

    public MessageAdapter() {

    }

    public void setItems(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // New view is being created for the first time
        View rootView = inflater.inflate(R.layout.item_message, parent, false);
        // Return ViewHolder with view
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // ViewHolder is being bound to your data
        holder.authorView.setText(messages.get(position).author);
        holder.messageView.setText(messages.get(position).message);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
