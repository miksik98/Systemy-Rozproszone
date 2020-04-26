package server;

import IntelligentHouse.Device;
import IntelligentHouse.WorkingState;
import com.zeroc.Ice.Current;

public class BasicDevice implements Device {
    private WorkingState state = WorkingState.OFF;
    @Override
    public WorkingState getState(Current current) {
        return state;
    }

    @Override
    public void changeWorkingState(Current current) {
        switch(state){
            case ON:
                state = WorkingState.OFF;
                System.out.println(this.toString()+" turned off");
                break;
            case OFF:
                state = WorkingState.ON;
                System.out.println(this.toString()+" turned on");
                break;
        }
    }
}
