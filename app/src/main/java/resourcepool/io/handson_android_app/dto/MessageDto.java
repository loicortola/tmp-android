package resourcepool.io.handson_android_app.dto;

import java.util.List;
import java.util.UUID;

/**
 * Created by loicortola on 09/08/2016.
 */
public class MessageDto {
    private String uuid;
    private String login;
    private String message;
    private List<String> images;
    private List<Object> attachments;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MessageDto m;

        private Builder() {
            m = new MessageDto();
        }

        public Builder message(String message) {
            this.m.message = message;
            return this;
        }

        public Builder author(String author) {
            this.m.login = author;
            return this;
        }

        public Builder addAttachment(Object attachment) {
            //TODO
            throw new UnsupportedOperationException();
        }

        public MessageDto build() {
            this.m.uuid = UUID.randomUUID().toString();
            return this.m;
        }
    }
}
