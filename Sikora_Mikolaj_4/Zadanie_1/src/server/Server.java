package server;

import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private int port = 50051;
    private io.grpc.Server server;

    private void start() throws IOException
    {
        EventNotifyImpl eventNotify = new EventNotifyImpl();
        Thread generator = new Thread(eventNotify);
        generator.start();
        server = ServerBuilder.forPort(port)
                .addService(eventNotify)
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EventNotifyImpl.getObservers().forEach(StreamObserver::onCompleted);
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            Server.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = new Server();
        server.start();
        server.blockUntilShutdown();
    }

}
