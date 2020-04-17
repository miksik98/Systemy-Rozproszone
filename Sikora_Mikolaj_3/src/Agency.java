import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Agency {

    private static String agencyName, agencyQueueName;
    private static int orderID = 0;

    public enum HeaderType{
        ORDER_ID, AGENCY_NAME, SERVICE_TYPE;
        @Override
        public String toString() {
            String result = "";
            switch(this){
                case ORDER_ID:
                    result = "orderID";
                    break;
                case AGENCY_NAME:
                    result = "agencyName";
                    break;
                case SERVICE_TYPE:
                    result = "serviceType";
                    break;
            }
            return result;
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("AGENCY");
        System.out.print("Type name:\n> ");
        Scanner input = new Scanner(System.in);
        agencyName = input.nextLine();

        Channel channel = createChannel();

        Consumer freighterMessageConsumer = createFreighterMessageConsumer(channel);

        System.out.println("Waiting for messages...");
        channel.basicConsume(agencyQueueName, true, freighterMessageConsumer);

        while(true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("\nEnter service type> ");
            ServiceType serviceType;
            while((serviceType = ServiceType.queueMap.get(br.readLine())) == null){
                System.out.print("Bad service type. Please choose from set "+ServiceType.queueMap.keySet()+". Try again:\n> ");
            }
            System.out.print("Enter message> ");
            String message = br.readLine();

            publish(channel, serviceType, message);
        }
    }

    private static void publish(Channel channel, ServiceType serviceType, String message) throws IOException {
        Map<String, Object> headers = new HashMap<>();
        headers.put(HeaderType.ORDER_ID.toString(), orderID++);
        headers.put(HeaderType.AGENCY_NAME.toString(), agencyName);
        headers.put(HeaderType.SERVICE_TYPE.toString(), serviceType.toString());
        channel.basicPublish(Admin.EXCHANGE_TOPIC, serviceType.toString()+".admin", new AMQP.BasicProperties().builder().type("order").headers(headers).build(), message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Sent: "+message);
    }

    private static DefaultConsumer createFreighterMessageConsumer(Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                switch(MessageType.messageTypeMap.get(properties.getType())) {
                    case ADMIN_INFO:
                        System.out.println("{Message from ADMIN: " + message + "}");
                        System.out.print("> ");
                        break;
                    case ACKNOWLEDGEMENT:
                        System.out.println("{Received: " + message + "}");
                        System.out.print("> ");
                        break;
                    default:
                        System.out.println("{Received message with unsupported type}");
                        System.out.print("> ");
                }
            }
        };
    }

    private static Channel createChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Admin.EXCHANGE_TOPIC, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(Admin.EXCHANGE_FANOUT_AGENCY, BuiltinExchangeType.FANOUT);
        agencyQueueName = agencyName+"Queue";
        channel.queueDeclare(agencyQueueName,true,true, true, null);
        channel.queueBind(agencyQueueName, Admin.EXCHANGE_FANOUT_AGENCY, "");
        channel.queueBind(agencyQueueName, Admin.EXCHANGE_TOPIC, agencyName+".*");

        return channel;
    }
}
