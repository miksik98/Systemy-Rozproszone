package homework;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import homework.actors.Client;
import homework.actors.Server;
import homework.model.ProductRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

public class Application {
    public static Connection connection;
    private static Map<String, ActorRef> clients = new HashMap<>();
    private static final int clientsNumber = 5;

    private static Connection connect() {
        String url = "jdbc:sqlite:./db/queries.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String [] args) throws SQLException {
            try {
                connection = connect();
                ActorSystem system = ActorSystem.create("system");
                ActorRef server = system.actorOf(Props.create(Server.class), "server");
                final Materializer materializer = Materializer.createMaterializer(system);
                final ServerHttp serverHttp = new ServerHttp(server, system, materializer);
                final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = serverHttp.route().flow(system, materializer);
                final Http http = Http.get(system);

                http.bindAndHandle(routeFlow, ConnectHttp.toHost("localhost", 9000), materializer);

                for (int i = 0; i < clientsNumber; i++) {
                    String clientName = "client" + (i + 1);
                    clients.put(clientName, system.actorOf(Client.props(server), clientName));
                }

                System.out.println("Please provide: \"<clientName> <productName>\" or \"simulate\"");
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String[] arguments = scanner.nextLine().split(" ");
                    if(arguments.length == 1 && arguments[0].equals("quit")) break;
                    if(arguments.length == 1 && arguments[0].equals("simulate")) {simulate(); continue;}
                    if (arguments.length != 2) {
                        System.out.println("Wrong number of args!");
                        continue;
                    }
                    String clientName = arguments[0];
                    String productName = arguments[1];
                    if (!clients.containsKey(clientName)) {
                        System.out.println("Client " + clientName + " does not exist.");
                        continue;
                    }
                    ActorRef currentClient = clients.get(clientName);
                    currentClient.tell(new ProductRequest(productName), currentClient);
                }
            }
            finally {
                connection.close();
            }
    }

    private static void simulate() {
        Random random = new Random();
        List<String> products = Arrays.asList("laptop","myszka","monitor", "klawiatura", "sluchawki");
        int i = 0;
        for (Entry<String, ActorRef> entry : clients.entrySet()) {
            ActorRef currentClient = entry.getValue();
            String productName = products.get(i++);
            currentClient.tell(new ProductRequest(productName), currentClient);
        }
    }
}
