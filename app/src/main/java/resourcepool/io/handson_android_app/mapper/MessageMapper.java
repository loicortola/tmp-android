package resourcepool.io.handson_android_app.mapper;

import java.util.ArrayList;
import java.util.List;

import resourcepool.io.handson_android_app.dto.MessageDto;
import resourcepool.io.handson_android_app.model.Message;

/**
 * Created by loicortola on 09/08/2016.
 */
public abstract class MessageMapper {

    public static Message fromDto(MessageDto dto) {
        return new Message(dto.getLogin(), dto.getMessage());
    }

    public static List<Message> fromDto(List<MessageDto> dtos) {
        if (dtos == null) {
            return null;
        }
        List<Message> result = new ArrayList<>(dtos.size());
        for (int i = 0; i < dtos.size(); i++) {
            MessageDto dto = dtos.get(i);
            result.add(fromDto(dto));
        }
        return result;
    }
}
