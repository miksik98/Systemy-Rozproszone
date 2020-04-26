module IntelligentHouse
{
    enum WorkingState { ON, OFF };
    enum TemperatureScale { C, F };
    struct Temperature
    {
        int value;
        TemperatureScale scale;
    };
    interface Device
    {
        WorkingState getState();
        void changeWorkingState(); // turn-on/turn-off
    };
    exception OffDeviceError
    {
        string reason = "Device is turned off. Please turn it on to perform this operation.";
    };
    interface TemperatureSensor extends Device
    {
        Temperature getIndoorTemperature() throws OffDeviceError;
        Temperature getOutdoorTemperature() throws OffDeviceError;
    };
    interface Fridge extends Device
    {
        Temperature getCurrentTemperature() throws OffDeviceError;
        void changeTemperature(Temperature temperature) throws OffDeviceError;
    };
    enum Color { RED, GREEN, BLUE };
    interface Lamp extends Device
    {
        void changeColor(Color color);
    };
    interface FlashingLamp extends Lamp
    {
        void setFrequency(int frequency);
    };
    interface MotionLamp extends Lamp
    {
        void setTime(int time);
    };
    struct DayPeriod
    {
        int startHour;
        int endHour;
    };
    dictionary<DayPeriod ,Temperature> HeatingPlan;
    interface ElectricStove extends TemperatureSensor
    {
        void setDefaultTemperature(Temperature t);
        Temperature getDefaultTemperature();
        HeatingPlan getHeatingPlan();
        void changeHeatingPlanElement(DayPeriod dayPeriod, Temperature temperature);
    };
};