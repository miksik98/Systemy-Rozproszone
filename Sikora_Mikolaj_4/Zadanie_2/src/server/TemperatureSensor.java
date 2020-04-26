package server;

import IntelligentHouse.OffDeviceError;
import IntelligentHouse.Temperature;
import IntelligentHouse.TemperatureScale;
import IntelligentHouse.WorkingState;
import com.zeroc.Ice.Current;

import java.util.Random;

public class TemperatureSensor extends BasicDevice implements IntelligentHouse.TemperatureSensor {
    private WorkingState state = WorkingState.OFF;

    @Override
    public Temperature getIndoorTemperature(Current current) throws OffDeviceError {
        if(this.getState(current) == WorkingState.OFF) throw new OffDeviceError();
        return new Temperature(Math.abs(new Random().nextInt())%10+15, TemperatureScale.C);
    }

    @Override
    public Temperature getOutdoorTemperature(Current current) throws OffDeviceError {
        if(this.getState(current) == WorkingState.OFF) throw new OffDeviceError();
        return new Temperature(Math.abs(new Random().nextInt())%10+15, TemperatureScale.C);
    }
}
