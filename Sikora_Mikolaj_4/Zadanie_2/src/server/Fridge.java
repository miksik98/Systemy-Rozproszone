package server;

import IntelligentHouse.OffDeviceError;
import IntelligentHouse.Temperature;
import IntelligentHouse.WorkingState;
import com.zeroc.Ice.Current;

public class Fridge extends BasicDevice implements IntelligentHouse.Fridge {
    private Temperature temperature;

    public Fridge(Temperature temperature){
        this.temperature = temperature;
    }

    @Override
    public Temperature getCurrentTemperature(Current current) throws OffDeviceError {
        if(this.getState(current) == WorkingState.OFF) throw new OffDeviceError();
        return temperature;
    }

    @Override
    public void changeTemperature(Temperature temperature, Current current) throws OffDeviceError {
        if(this.getState(current) == WorkingState.OFF) throw new OffDeviceError();
        System.out.println("Changing temperature for "+temperature.value+" "+temperature.scale);
        this.temperature = temperature;
    }
}
