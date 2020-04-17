import javax.naming.InvalidNameException;
import java.util.HashMap;
import java.util.Map;

public enum ServiceType {
    PEOPLE_TRANSPORTATION("peopleQueue"), CARGO_TRANSPORTATION("cargoQueue"), SATELLITE_PLACEMENT("satelliteQueue");

    public static final Map<String, ServiceType> queueMap = new HashMap<>();
    static{
        queueMap.put("people", PEOPLE_TRANSPORTATION);
        queueMap.put("cargo", CARGO_TRANSPORTATION);
        queueMap.put("satellite", SATELLITE_PLACEMENT);
    }

    private String queueName;

    ServiceType(String queueName){
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }
}
