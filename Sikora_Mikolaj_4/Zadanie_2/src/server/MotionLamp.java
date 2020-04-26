package server;

import IntelligentHouse.Color;
import com.zeroc.Ice.Current;

public class MotionLamp extends Lamp implements IntelligentHouse.MotionLamp {
    private int time;

    public MotionLamp(Color color, int time){
        super(color);
        this.time = time;
    }
    @Override
    public void setTime(int time, Current current) {
        System.out.println("Changing time of shining when motion detected for "+time+" seconds");
        this.time = time;
    }
}
