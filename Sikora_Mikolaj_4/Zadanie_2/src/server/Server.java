package server;

import IntelligentHouse.Color;
import IntelligentHouse.Temperature;
import IntelligentHouse.TemperatureScale;
import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<Object> devicesList = new ArrayList<>();
    public void t1(String[] args)
    {
        int status = 0;
        Communicator communicator = null;

        try
        {
            communicator = Util.initialize(args);

            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p 10000:udp -h localhost -p 10000");

            adapter.addServantLocator(new ServantLocator() {
                @Override
                public LocateResult locate(Current current) throws UserException {
                    System.out.println(current.id.name);
                    Object object = null;
                    switch(current.id.name) {
                        case "motionLamp":
                            object = new MotionLamp(Color.BLUE, 2);
                            break;
                        case "flashingLamp":
                            object = new FlashingLamp(Color.GREEN, 1);
                            break;
                        case "electricStove":
                            object = new ElectricStove(new Temperature(20, TemperatureScale.C));
                            break;
                        case "fridge":
                            object = new Fridge(new Temperature(7, TemperatureScale.C));
                            break;
                    }
                    devicesList.add(object);
                    adapter.add(object, new Identity(current.id.name,current.id.category));
                    return new LocateResult(object, null);
                }

                @Override
                public void finished(Current current, Object object, java.lang.Object o) throws UserException {

                }

                @Override
                public void deactivate(String s) {

                }
            }, "device");

            adapter.activate();

            System.out.println("Entering event processing loop...");

            new Thread(()->{
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String input = "";
                while(true){
                    try {
                        input = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(input.equals("available devices")){
                        System.out.println(devicesList);
                    }
                }
            }).start();

            communicator.waitForShutdown();

        }
        catch (Exception e)
        {
            System.err.println(e);
            status = 1;
        }
        if (communicator != null)
        {
            // Clean up
            //
            try
            {
                communicator.destroy();
            }
            catch (Exception e)
            {
                System.err.println(e);
                status = 1;
            }
        }
        System.exit(status);
    }


    public static void main(String[] args)
    {
        Server app = new Server();
        app.t1(args);
    }
}
