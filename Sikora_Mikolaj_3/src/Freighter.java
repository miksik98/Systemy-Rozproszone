import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Freighter {

    private static ServiceType firstService, secondService;
    private static String firstQueueName, secondQueueName, freighterQueueName;

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("FREIGHTER");
        getServiceNames();

        Channel channel = createChannel();

        Consumer agencyMessageConsumer = createAgencyMessageConsumer(channel);
        Consumer adminMessageConsumer = createAdminMessageConsumer(channel);

        System.out.println("Waiting for messages...");
        channel.basicConsume(firstQueueName, true, agencyMessageConsumer);
        channel.basicConsume(secondQueueName, true, agencyMessageConsumer);
        channel.basicConsume(freighterQueueName, true, adminMessageConsumer);
    }

    private static Channel createChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Admin.EXCHANGE_TOPIC, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(Admin.EXCHANGE_FANOUT_FREIGHTER, BuiltinExchangeType.FANOUT);

        freighterQueueName = channel.queueDeclare().getQueue();
        channel.queueDeclare(firstQueueName,true,false, true, null);
        channel.queueDeclare(secondQueueName,true,false, true, null);
        channel.queueBind(firstQueueName, Admin.EXCHANGE_TOPIC, firstService.toString()+".*");
        channel.queueBind(secondQueueName, Admin.EXCHANGE_TOPIC, secondService.toString()+".*");
        channel.queueBind(freighterQueueName, Admin.EXCHANGE_FANOUT_FREIGHTER, "");
        channel.basicQos(1);

        return channel;
    }

    private static DefaultConsumer createAdminMessageConsumer(Channel channel) {
        return new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Message from ADMIN: "+message);
            }
        };
    }

    private static DefaultConsumer createAgencyMessageConsumer(Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                String agencyName = properties.getHeaders().get(Agency.HeaderType.AGENCY_NAME.toString()).toString();
                int orderID = (int) properties.getHeaders().get(Agency.HeaderType.ORDER_ID.toString());
                String serviceType = properties.getHeaders().get(Agency.HeaderType.SERVICE_TYPE.toString()).toString();
                System.out.println("Received order: ["+agencyName+"-"+orderID+"/"+serviceType+"] "+message);
                try {
                    Random random = new Random();
                    Thread.sleep((Math.abs(random.nextInt())%15+1)*500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String response = "["+agencyName+"-"+orderID+"/"+serviceType+"] was done!";
                channel.basicPublish(Admin.EXCHANGE_TOPIC, agencyName+".admin", new AMQP.BasicProperties().builder().type(MessageType.ACKNOWLEDGEMENT.toString()).build(), response.getBytes(StandardCharsets.UTF_8));
            }
        };
    }

    private static void getServiceNames() {
        System.out.print("Choose two service types from set "+ServiceType.queueMap.keySet()+":\n> ");
        Scanner input = new Scanner(System.in);
        while((firstService = ServiceType.queueMap.get(input.nextLine().trim())) == null){
            System.out.print("Bad service type. Please choose from the set "+ServiceType.queueMap.keySet()+". Try again:\n> ");
        }
        System.out.print("First service type saved. Choose second:\n> ");
        while((secondService = ServiceType.queueMap.get(input.nextLine().trim())) == null || secondService == firstService){
            if(secondService == firstService) System.out.print("Please provide other service type than first. Try again:\n> ");
            else System.out.print("Bad service type. Please choose from set "+ServiceType.queueMap.keySet()+". Try again:\n> ");
        }
        System.out.println("Second service type saved.");

        firstQueueName = firstService.getQueueName();
        secondQueueName = secondService.getQueueName();
    }

}
