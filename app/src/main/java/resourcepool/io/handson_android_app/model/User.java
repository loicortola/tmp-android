package resourcepool.io.handson_android_app.model;

/**
 * Created by loicortola on 09/08/2016.
 */
public class User {
    private String login;
    private String password;

    public User() {

    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
