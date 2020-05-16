import java.util.List;
import java.util.Scanner;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.data.Stat;

import static org.apache.zookeeper.KeeperException.Code.*;

public class DataMonitor implements Watcher, StatCallback {

    ZooKeeper zooKeeper;

    String znode;

    boolean dead;

    DataMonitorListener listener;

    long numberOfDescendants;

    Thread listSubtree = new Thread(()->{
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(scanner.nextLine().equals("ls")) {
                try {
                    printTree(znode, "", 0);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    void printTree(String root, String prefix, int offset) throws KeeperException, InterruptedException {
        int i = offset;
        while(--i >= 0) System.out.print(" ");
        System.out.print(root+": ");
        zooKeeper.getChildren(prefix+root, true).forEach(n -> System.out.print(n+" "));
        System.out.println();
        zooKeeper.getChildren(prefix+root, true).forEach(n -> {
            try {
                printTree("/"+n, prefix+root, offset+2);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }



    public DataMonitor(ZooKeeper zooKeeper, String znode,
                       DataMonitorListener listener) {
        this.zooKeeper = zooKeeper;
        this.znode = znode;
        this.listener = listener;
        this.listSubtree.start();
        this.numberOfDescendants = -1;
        zooKeeper.exists(znode, true, this, null);
    }

    public interface DataMonitorListener {
        void exists(boolean deleting);
        void closing();
    }

    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    break;
                case Expired:
                    dead = true;
                    listener.closing();
                    break;
            }
        } else {
            if(event.getType() == Event.EventType.NodeChildrenChanged){
                try {
                    long newNumberOfDescendants = countDescendants();
                    if(newNumberOfDescendants > numberOfDescendants) {
                        System.out.println("Number of descendants: " + newNumberOfDescendants);
                    }
                    numberOfDescendants = newNumberOfDescendants;
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (path != null && path.equals(znode)) zooKeeper.exists(znode, true, this, null);
            }
        }
    }
    private long countDescendants() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(znode, this);
        return children.size() + children.stream().mapToLong(child -> countDescendants(znode+"/"+child)).sum();
    }

    private long countDescendants(String path) {
        try {
            List<String> children = zooKeeper.getChildren(path, this);
            return children.size() + children.stream().mapToLong(c -> countDescendants(path+"/"+c)).sum();
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        switch (get(rc)) {
            case OK:
                try {
                    numberOfDescendants = countDescendants();
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
                listener.exists(false);
                break;
            case NONODE:
                listener.exists(true);
                break;
            case SESSIONEXPIRED:
            case NOAUTH:
                dead = true;
                listener.closing();
                return;
            default:
                zooKeeper.exists(znode, true, this, null);
        }
    }
}