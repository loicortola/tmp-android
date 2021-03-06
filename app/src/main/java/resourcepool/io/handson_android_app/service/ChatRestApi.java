package resourcepool.io.handson_android_app.service;

import java.util.List;

import resourcepool.io.handson_android_app.model.Message;

/**
 * Created by loicortola on 08/08/2016.
 */
public interface ChatRestApi {

    String CHAT_REST_API_BASE_URL = "https://training.loicortola.com/chat-rest/2.0";

    /**
     * Connect to API.
     * @param login the user login
     * @param password the user password
     * @return true if connection successful, false otherwise
     */
    boolean connect(String login, String password);

    /**
     * Send message to API.
     * @param message the message
     * @return true if message sent successfully, false otherwise
     */
    boolean sendMessage(String message);

    /**
     * Retrieve latest messages from API.
     * @return the latest messages, or null otherwise
     */
    List<Message> getMessages();
}
