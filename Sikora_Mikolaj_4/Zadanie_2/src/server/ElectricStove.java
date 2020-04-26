package server;


import IntelligentHouse.DayPeriod;
import IntelligentHouse.Temperature;
import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectricStove extends TemperatureSensor implements IntelligentHouse.ElectricStove {

    public Temperature defaultTemperature;
    public Map<DayPeriod, Temperature> heatingPlan = new HashMap<>();

    public ElectricStove(Temperature defaultTemperature){
        this.defaultTemperature = defaultTemperature;
    }

    @Override
    public void setDefaultTemperature(Temperature t, Current current) {
        System.out.println("Setting default temperature to "+t.value+" "+t.scale.toString());
        this.defaultTemperature = t;
    }

    @Override
    public Temperature getDefaultTemperature(Current current) {
        return defaultTemperature;
    }

    @Override
    public Map<DayPeriod, Temperature> getHeatingPlan(Current current) {
        return heatingPlan;
    }


    @Override
    public void changeHeatingPlanElement(DayPeriod dayPeriod, Temperature temperature, Current current) {
        System.out.println("Changing heating plan element. Adding temperature for: "+dayPeriod.startHour+"-"+dayPeriod.endHour);
        List<DayPeriod> dayPeriodsToDelete = new ArrayList<>();
        for(DayPeriod periodFromPlan: heatingPlan.keySet()){
            if((dayPeriod.startHour<=periodFromPlan.startHour && dayPeriod.endHour>periodFromPlan.startHour)
            || (periodFromPlan.startHour<=dayPeriod.startHour && periodFromPlan.endHour>dayPeriod.startHour)){
                dayPeriodsToDelete.add(periodFromPlan);
            }
        }
        dayPeriodsToDelete.forEach(x->heatingPlan.remove(x));
        heatingPlan.put(dayPeriod, temperature);
    }
}
