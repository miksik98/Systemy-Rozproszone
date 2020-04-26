package server;

import IntelligentHouse.Color;
import com.zeroc.Ice.Current;

public class Lamp extends BasicDevice implements IntelligentHouse.Lamp {
    private Color color;

    public Lamp(Color color){
        this.color = color;
    }
    @Override
    public void changeColor(Color color, Current current) {
        if(color != this.color) {
            System.out.println("Changing lamp color from " + this.color.name().toLowerCase() + " to " + color.name().toLowerCase());
            this.color = color;
        } else {
            System.out.println("Lamp color didn't changed");
        }
    }
}
