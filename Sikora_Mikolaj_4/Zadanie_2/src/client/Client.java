package client;

import IntelligentHouse.*;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import java.util.Map;

public class Client {
    public static void main(String[] args)
    {
        int status = 0;
        Communicator communicator = null;

        try {
            communicator = Util.initialize(args);

            ObjectPrx baseMotionLamp = communicator.stringToProxy("device/motionLamp:tcp -h localhost -p 10000");
            ObjectPrx baseFlashingLamp = communicator.stringToProxy("device/flashingLamp:tcp -h localhost -p 10000");
            ObjectPrx baseElectricStove = communicator.stringToProxy("device/electricStove:tcp -h localhost -p 10000");
            ObjectPrx baseFridge = communicator.stringToProxy("device/fridge:tcp -h localhost -p 10000");

            MotionLampPrx motionLamp = MotionLampPrx.checkedCast(baseMotionLamp);
            if (motionLamp == null) throw new Error("Invalid proxy for motion lamp");
            FlashingLampPrx flashingLamp = FlashingLampPrx.checkedCast(baseFlashingLamp);
            if (flashingLamp == null) throw new Error("Invalid proxy for flashing lamp");
            ElectricStovePrx electricStove = ElectricStovePrx.checkedCast(baseElectricStove);
            if (electricStove == null) throw new Error("Invalid proxy for electric stove");
            FridgePrx fridge = FridgePrx.checkedCast(baseFridge);
            if (fridge == null) throw new Error("Invalid proxy for fridge");

            String device = null;
            String command = null;
            String arguments = null;
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            while(true)
            {
                try
                {
                    System.out.print("Provide device name (or \'x\' to exit): ");
                    device = in.readLine();
                    if(device.equals("x")) break;
                    switch(device){
                        case "motion":
                            System.out.print("command>> ");
                            command = in.readLine();
                            switch(command){
                                case "switch":
                                    motionLamp.changeWorkingState();
                                    break;
                                case "change":
                                    System.out.print("args>> ");
                                    arguments = in.readLine();
                                    switch(arguments){
                                        case "red":
                                            motionLamp.changeColor(Color.RED);
                                            break;
                                        case "green":
                                            motionLamp.changeColor(Color.GREEN);
                                            break;
                                        case "blue":
                                            motionLamp.changeColor(Color.BLUE);
                                            break;
                                    }
                                    break;
                                case "set time":
                                    System.out.print("args>>> ");
                                    arguments = in.readLine();
                                    motionLamp.setTime(Integer.parseInt(arguments));
                                    break;
                            }
                            break;
                        case "flashing":
                            System.out.print("command>> ");
                            command = in.readLine();
                            switch(command){
                                case "switch":
                                    flashingLamp.changeWorkingState();
                                    break;
                                case "change":
                                    System.out.print("args>> ");
                                    arguments = in.readLine();
                                    switch(arguments){
                                        case "red":
                                            flashingLamp.changeColor(Color.RED);
                                            break;
                                        case "green":
                                            flashingLamp.changeColor(Color.GREEN);
                                            break;
                                        case "blue":
                                            flashingLamp.changeColor(Color.BLUE);
                                            break;
                                    }
                                    break;
                                case "set frequency":
                                    System.out.print("args>>> ");
                                    arguments = in.readLine();
                                    flashingLamp.setFrequency(Integer.parseInt(arguments));
                                    break;
                            }
                            break;
                        case "stove":
                            System.out.print("command>> ");
                            command = in.readLine();
                            Temperature t;
                            switch(command){
                                case "switch":
                                    electricStove.changeWorkingState();
                                    break;
                                case "outdoor":
                                    try {
                                        t = electricStove.getOutdoorTemperature();
                                        System.out.println(t.value + " " + t.scale.toString());
                                    } catch(OffDeviceError e){
                                        System.out.println(e.reason);
                                    }
                                    break;
                                case "indoor":
                                    try {
                                        t = electricStove.getIndoorTemperature();
                                        System.out.println(t.value + " " + t.scale.toString());
                                    } catch(OffDeviceError e){
                                        System.out.println(e.reason);
                                    }
                                    break;
                                case "get default":
                                    t = electricStove.getDefaultTemperature();
                                    System.out.println(t.value+" "+t.scale.toString());
                                    break;
                                case "set default":
                                    System.out.print("args>> ");
                                    arguments = in.readLine();
                                    electricStove.setDefaultTemperature(new Temperature(Integer.parseInt(arguments), TemperatureScale.C));
                                    break;
                                case "heating plan":
                                    Map<DayPeriod, Temperature> heatingPlan = electricStove.getHeatingPlan();
                                    for(Map.Entry<DayPeriod, Temperature> entry: heatingPlan.entrySet()){
                                        System.out.print(entry.getKey().startHour+"-"+entry.getKey().endHour+" ");
                                        System.out.println(entry.getValue().value+" "+entry.getValue().scale.toString());
                                    }
                                    break;
                                case "add to plan":
                                    System.out.print("args>> ");
                                    String[] split = in.readLine().split(" ");
                                    electricStove.changeHeatingPlanElement(new DayPeriod(Integer.parseInt(split[0]), Integer.parseInt(split[1])), new Temperature(Integer.parseInt(split[2]), TemperatureScale.C));
                                    break;
                            }
                            break;
                        case "fridge":
                            System.out.print("command>> ");
                            command = in.readLine();
                            switch(command) {
                                case "switch":
                                    fridge.changeWorkingState();
                                    break;
                                case "get temp":
                                    try {
                                        t = fridge.getCurrentTemperature();
                                        System.out.println(t.value+" "+t.scale.toString());
                                    } catch(OffDeviceError e){
                                        System.out.println(e.reason);
                                    }
                                    break;
                                case "change temp":
                                    System.out.print("args>> ");
                                    arguments = in.readLine();
                                    try {
                                        fridge.changeTemperature(new Temperature(Integer.parseInt(arguments), TemperatureScale.C));
                                    } catch(OffDeviceError e){
                                        System.out.println(e.reason);
                                    }
                                    break;
                            }
                        break;
                    }
                }
                catch (java.io.IOException ex)
                {
                    System.err.println(ex);
                }
            }


        } catch (LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }
        if (communicator != null) {
            // Clean up
            //
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }
}
