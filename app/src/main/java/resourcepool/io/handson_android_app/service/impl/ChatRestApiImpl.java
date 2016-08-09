package resourcepool.io.handson_android_app.service.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import resourcepool.io.handson_android_app.dto.MessageDto;
import resourcepool.io.handson_android_app.mapper.MessageMapper;
import resourcepool.io.handson_android_app.model.Message;
import resourcepool.io.handson_android_app.service.ChatRestApi;

/**
 * Created by loicortola on 08/08/2016.
 */
public class ChatRestApiImpl implements ChatRestApi {

    private static final String TAG = ChatRestApi.class.getSimpleName();

    private static final ChatRestApi INSTANCE = new ChatRestApiImpl();

    private static final MediaType JSON = MediaType.parse("application/json");

    private OkHttpClient client;
    private String login;

    public static ChatRestApi getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean connect(final String login, final String password) {
        client = createClient(login, password);
        Request req = new Request.Builder()
                .url(CHAT_REST_API_BASE_URL + "/connect")
                .build();
        try {
            Response res = client.newCall(req).execute();
            boolean success = res.isSuccessful();
            res.close();
            if (success) {
                this.login = login;
            }
            return success;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean sendMessage(String message) {
        RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(MessageDto.builder()
                .author(login)
                .message(message)
                .build()));

        Request req = new Request.Builder()
                .url(CHAT_REST_API_BASE_URL + "/messages")
                .post(requestBody)
                .build();
        try {
            Response res = client.newCall(req).execute();
            boolean success = res.isSuccessful();
            res.close();
            return success;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<Message> getMessages() {
        Request req = new Request.Builder()
                .url(CHAT_REST_API_BASE_URL + "/messages")
                .build();
        try {
            Response res = client.newCall(req).execute();
            if (res.isSuccessful()) {
                List<MessageDto> messages = new Gson().fromJson(res.body().charStream(), new TypeToken<List<MessageDto>>(){}.getType());
                return MessageMapper.fromDto(messages);
            }
            return null;
        } catch (IOException e) {
            Log.w(TAG, "Exception occured: " + e.getMessage());
            return null;
        }
    }

    private OkHttpClient createClient(final String login, final String password) {
        return new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        String credentials = Credentials.basic(login, password); // Basic Base64HashOfCredentials
                        return response.request().newBuilder().header("Authorization", credentials).build();
                    }
                }).build();
    }
}
