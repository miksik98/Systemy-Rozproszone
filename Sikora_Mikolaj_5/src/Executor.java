import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {

    DataMonitor dm;

    ZooKeeper zk;

    String execName;

    Process process;

    String znode;

    public Executor(String hostPort, String znode,
                    String execName) throws IOException, InterruptedException {
        this.execName = execName;
        this.znode = znode;
        zk = new ZooKeeper(hostPort, 3000, this);
        dm = new DataMonitor(zk, znode, this);
        while(zk.getState() == ZooKeeper.States.CONNECTING){
            Thread.sleep(1000);
            System.out.println("Connecting");
        }
        System.out.println("Connected");
    }

    public static void main(String[] args) {
        try {
            Logger.getRootLogger().setLevel(Level.OFF);
            System.out.println("Usage: <HostPort> <Znode> <ExecutablePath>");
            Scanner scanner = new Scanner(System.in);
            String[] input = scanner.nextLine().split(" ");
            if(input.length < 3 ){
                System.out.println("Wrong input");
                System.exit(-1);
            }
            new Executor("127.0.0.1:"+input[0], input[1], input[2]).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent event) {
        dm.process(event);
    }

    public void run() {
        try {
            synchronized (this) {
                while (!dm.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }
    }

    public void closing() {
        synchronized (this) {
            notifyAll();
        }
    }

    public void exists(boolean deleting) {
        if(deleting) {
            process.destroy();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            try {
                process = new ProcessBuilder(execName).start();
                try {
                    zk.getChildren(znode, this);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}