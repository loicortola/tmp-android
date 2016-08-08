package resourcepool.io.handson_android_app;

import android.util.Log;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by loicortola on 08/08/2016.
 */
public class ChatRestApiImpl implements ChatRestApi {

    private static final ChatRestApi INSTANCE = new ChatRestApiImpl();

    private OkHttpClient client;

    public static ChatRestApi getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean connect(final String login, final String password) {
        OkHttpClient client = createClient(login, password);
        Request req = new Request.Builder()
                .url(CHAT_REST_API_BASE_URL + "/connect")
                .build();
        try {
            Response res = client.newCall(req).execute();
            return res.isSuccessful();
        } catch (IOException e) {
            return false;
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
