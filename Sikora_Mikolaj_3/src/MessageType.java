import java.util.HashMap;
import java.util.Map;

public enum MessageType {
    ADMIN_INFO, ORDER, ACKNOWLEDGEMENT;

    static final Map<String, MessageType> messageTypeMap = new HashMap<>();
    static {
        messageTypeMap.put(ADMIN_INFO.toString(), ADMIN_INFO);
        messageTypeMap.put(ORDER.toString(), ORDER);
        messageTypeMap.put(ACKNOWLEDGEMENT.toString(), ACKNOWLEDGEMENT);
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
