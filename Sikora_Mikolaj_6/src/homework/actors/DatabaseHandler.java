package homework.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import homework.Application;
import homework.model.ProductQuery;

import java.sql.*;

public class DatabaseHandler extends AbstractActor {
    private ProductQuery productQuery;
    private Connection connection = Application.connection;
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ProductQuery.class, productQuery -> {
                    this.productQuery = productQuery;
                    int occurrences = getOccurrences();
                    getSender().tell(occurrences+1, getSelf());
                    if(occurrences == 0) create();
                    else update(occurrences+1);
                    //log.info("DB updated");
                    context().stop(self());
                })
                .matchAny(unknown -> {
                    System.err.println("Warning: unrecognized message class \"" + unknown.getClass() + "\" received by DatabaseHandler.\n");
                })
                .build();
    }

    private void create() {
        String sql = "INSERT INTO query (product_name) VALUES (?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, this.productQuery.getName());
            statement.executeUpdate();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void update(int currentOccurrences) {
        String sql = String.format("UPDATE query SET occurrences = %d WHERE product_name = '%s'", currentOccurrences, this.productQuery.getName());
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private int getOccurrences() {
        String sql = String.format("SELECT occurrences FROM query WHERE product_name = '%s'", this.productQuery.getName());
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                int result = rs.getInt("occurrences");
                statement.close();
                return result;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
