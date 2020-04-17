import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Admin {

    public static String EXCHANGE_TOPIC = "exchange_topic";
    public static String EXCHANGE_FANOUT_FREIGHTER = "exchange_fanout_freighter";
    public static String EXCHANGE_FANOUT_AGENCY = "exchange_fanout_agency";
    private static String adminQueueName = "adminQueue";

    private enum Mode{
        TO_AGENCIES, TO_FREIGHTERS, TO_ALL;

        static final Map<String, Mode> modeMap = new HashMap<>();
        static {
            modeMap.put("A", TO_AGENCIES);
            modeMap.put("F", TO_FREIGHTERS);
            modeMap.put("AF", TO_ALL);
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("ADMIN");

        Channel channel = prepareChannel();
        Consumer consumer = createConsumer(channel);
        channel.basicConsume(adminQueueName, true, consumer);

        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("\nEnter mode from set [A, F, AF]> ");
            Mode mode;
            while((mode = Mode.modeMap.get(br.readLine().trim())) == null){
                System.out.print("Bad service type. Please choose from set "+Mode.modeMap.keySet()+". Try again:\n> ");
            }
            System.out.print("Enter message> ");
            String message = br.readLine();

            publish(channel, mode, message);
        }
    }

    private static DefaultConsumer createConsumer(Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                switch(MessageType.messageTypeMap.get(properties.getType())){
                    case ORDER:
                        String agencyName = properties.getHeaders().get(Agency.HeaderType.AGENCY_NAME.toString()).toString();
                        int orderID = (int) properties.getHeaders().get(Agency.HeaderType.ORDER_ID.toString());
                        String serviceType = properties.getHeaders().get(Agency.HeaderType.SERVICE_TYPE.toString()).toString();
                        System.out.println("{Received order: ["+agencyName+"-"+orderID+"/"+serviceType+"] "+message+"}");
                        System.out.print("> ");
                        break;
                    case ACKNOWLEDGEMENT:
                        System.out.println("{Received acknowledgement: " + message+"}");
                        System.out.print("> ");
                        break;
                    default:
                        System.out.println("{Unsupported type}");
                }
            }
        };
    }

    private static Channel prepareChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_TOPIC, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(EXCHANGE_FANOUT_FREIGHTER, BuiltinExchangeType.FANOUT);
        channel.exchangeDeclare(EXCHANGE_FANOUT_AGENCY, BuiltinExchangeType.FANOUT);

        adminQueueName = channel.queueDeclare().getQueue();
        channel.queueBind(adminQueueName, EXCHANGE_TOPIC, "*.admin");

        return channel;
    }

    private static void publish(Channel channel, Mode mode, String message) throws IOException {
        if(mode == Mode.TO_ALL){
            channel.basicPublish(EXCHANGE_FANOUT_FREIGHTER, "", new AMQP.BasicProperties().builder().type(MessageType.ADMIN_INFO.toString()).build(), message.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_FANOUT_AGENCY, "", new AMQP.BasicProperties().builder().type(MessageType.ADMIN_INFO.toString()).build(), message.getBytes(StandardCharsets.UTF_8));
        }else{
            String exchangeKey;
            if(mode == Mode.TO_AGENCIES) {
                exchangeKey = EXCHANGE_FANOUT_AGENCY;
            }else {
                exchangeKey = EXCHANGE_FANOUT_FREIGHTER;
            }
            channel.basicPublish(exchangeKey, "", new AMQP.BasicProperties().builder().type(MessageType.ADMIN_INFO.toString()).build(), message.getBytes(StandardCharsets.UTF_8));
        }

        System.out.println("Sent: "+message+" ["+mode.toString()+"]");
    }
}
