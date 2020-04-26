import Ice
import sys
from IntelligentHouse import MotionLampPrx, FlashingLampPrx, ElectricStovePrx, FridgePrx, Color, OffDeviceError, \
    Temperature, TemperatureScale, DayPeriod
from builtins import RuntimeError

communicator = Ice.initialize(sys.argv)
try:
    baseMotionLamp = communicator.stringToProxy("device/motionLamp:tcp -h localhost -p 10000")
    baseFlashingLamp = communicator.stringToProxy("device/flashingLamp:tcp -h localhost -p 10000")
    baseElectricStove = communicator.stringToProxy("device/electricStove:tcp -h localhost -p 10000")
    baseFridge = communicator.stringToProxy("device/fridge:tcp -h localhost -p 10000")

    motionLamp = MotionLampPrx.checkedCast(baseMotionLamp)
    if not motionLamp:
        raise RuntimeError("Invalid proxy for motion lamp")
    flashingLamp = FlashingLampPrx.checkedCast(baseFlashingLamp)
    if not flashingLamp:
        raise RuntimeError("Invalid proxy for flashing lamp")
    electricStove = ElectricStovePrx.checkedCast(baseElectricStove)
    if not electricStove:
        raise RuntimeError("Invalid proxy for electric stove")
    fridge = FridgePrx.checkedCast(baseFridge)
    if not fridge:
        raise RuntimeError("Invalid proxy for fridge")

    device = None
    while True:
        print("Provide device name (or \'x\' to exit): ", end="")
        device = input()
        if device == "motion":
            print("command>> ", end="")
            command = input()
            if command == "switch":
                motionLamp.changeWorkingState()
            elif command == "change":
                print("args>> ", end="")
                args = input()
                if args == "red":
                    motionLamp.changeColor(Color.RED)
                elif args == "green":
                    motionLamp.changeColor(Color.GREEN)
                elif args == "blue":
                    motionLamp.changeColor(Color.BLUE)
            elif command == "set time":
                print("args>> ", end="")
                motionLamp.setTime(int(input()))

        elif device == "flashing":
            print("command>> ", end="")
            command = input()
            if command == "switch":
                flashingLamp.changeWorkingState()
            elif command == "change":
                print("args>> ", end="")
                args = input()
                if args == "red":
                    flashingLamp.changeColor(Color.RED)
                elif args == "green":
                    flashingLamp.changeColor(Color.GREEN)
                elif args == "blue":
                    flashingLamp.changeColor(Color.BLUE)
            elif command == "set frequency":
                print("args>> ", end="")
                flashingLamp.setFrequency(int(input()))

        elif device == "stove":
            print("command>> ", end="")
            command = input()
            if command == "switch":
                electricStove.changeWorkingState()
            elif command == "outdoor":
                try:
                    print(electricStove.getOutdoorTemperature())
                except OffDeviceError as e:
                    print(e.reason)
            elif command == "indoor":
                try:
                    print(electricStove.getIndoorTemperature())
                except OffDeviceError as e:
                    print(e.reason)
            elif command == "get default":
                print(electricStove.getDefaultTemperature())
            elif command == "set default":
                print("args>> ", end="")
                value = int(input())
                electricStove.setDefaultTemperature(Temperature(value, TemperatureScale.C))
            elif command == "heating plan":
                plan = electricStove.getHeatingPlan()
                print(plan)
            elif command == "add to plan":
                print("args>> ", end="")
                split = input().split(" ")
                electricStove.changeHeatingPlanElement(DayPeriod(int(split[0]), int(split[1])), Temperature(int(split[2]), TemperatureScale.C));

        elif device == "fridge":
            print("command>> ", end="")
            command = input()
            if command == "switch":
                fridge.changeWorkingState()
            elif command == "get temp":
                try:
                    print(fridge.getCurrentTemperature())
                except OffDeviceError as e:
                    print(e.reason)
            elif command == "change temp":
                print("args>> ", end="")
                try:
                    fridge.changeTemperature(Temperature(int(input()), TemperatureScale.C))
                except OffDeviceError as e:
                    print(e.reason)
except:
    print("Exception occured")
finally:
    communicator.destroy()
