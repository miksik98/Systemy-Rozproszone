package server;

import IntelligentHouse.Color;
import com.zeroc.Ice.Current;

public class FlashingLamp extends Lamp implements IntelligentHouse.FlashingLamp {
    private int frequency;

    public FlashingLamp(Color color, int frequency) {
        super(color);
        this.frequency = frequency;
    }

    @Override
    public void setFrequency(int frequency, Current current) {
        System.out.println("Changing frequency of flashing for "+frequency+" seconds");
        this.frequency = frequency;
    }
}
