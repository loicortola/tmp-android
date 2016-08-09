package resourcepool.io.handson_android_app.listener;

/**
 * Created by loicortola on 09/08/2016.
 */
public interface RequestListener<T> {
    void onResult(boolean success, T result);
}
