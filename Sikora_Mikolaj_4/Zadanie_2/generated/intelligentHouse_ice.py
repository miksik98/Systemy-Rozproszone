# -*- coding: utf-8 -*-
#
# Copyright (c) ZeroC, Inc. All rights reserved.
#
#
# Ice version 3.7.3
#
# <auto-generated>
#
# Generated from file `intelligentHouse.ice'
#
# Warning: do not edit this file.
#
# </auto-generated>
#

from sys import version_info as _version_info_
import Ice, IcePy

# Start of module IntelligentHouse
_M_IntelligentHouse = Ice.openModule('IntelligentHouse')
__name__ = 'IntelligentHouse'

if 'WorkingState' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.WorkingState = Ice.createTempClass()
    class WorkingState(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    WorkingState.ON = WorkingState("ON", 0)
    WorkingState.OFF = WorkingState("OFF", 1)
    WorkingState._enumerators = { 0:WorkingState.ON, 1:WorkingState.OFF }

    _M_IntelligentHouse._t_WorkingState = IcePy.defineEnum('::IntelligentHouse::WorkingState', WorkingState, (), WorkingState._enumerators)

    _M_IntelligentHouse.WorkingState = WorkingState
    del WorkingState

if 'TemperatureScale' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.TemperatureScale = Ice.createTempClass()
    class TemperatureScale(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    TemperatureScale.C = TemperatureScale("C", 0)
    TemperatureScale.F = TemperatureScale("F", 1)
    TemperatureScale._enumerators = { 0:TemperatureScale.C, 1:TemperatureScale.F }

    _M_IntelligentHouse._t_TemperatureScale = IcePy.defineEnum('::IntelligentHouse::TemperatureScale', TemperatureScale, (), TemperatureScale._enumerators)

    _M_IntelligentHouse.TemperatureScale = TemperatureScale
    del TemperatureScale

if 'Temperature' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.Temperature = Ice.createTempClass()
    class Temperature(object):
        def __init__(self, value=0, scale=_M_IntelligentHouse.TemperatureScale.C):
            self.value = value
            self.scale = scale

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.value)
            _h = 5 * _h + Ice.getHash(self.scale)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_IntelligentHouse.Temperature):
                return NotImplemented
            else:
                if self.value is None or other.value is None:
                    if self.value != other.value:
                        return (-1 if self.value is None else 1)
                else:
                    if self.value < other.value:
                        return -1
                    elif self.value > other.value:
                        return 1
                if self.scale is None or other.scale is None:
                    if self.scale != other.scale:
                        return (-1 if self.scale is None else 1)
                else:
                    if self.scale < other.scale:
                        return -1
                    elif self.scale > other.scale:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_IntelligentHouse._t_Temperature)

        __repr__ = __str__

    _M_IntelligentHouse._t_Temperature = IcePy.defineStruct('::IntelligentHouse::Temperature', Temperature, (), (
        ('value', (), IcePy._t_int),
        ('scale', (), _M_IntelligentHouse._t_TemperatureScale)
    ))

    _M_IntelligentHouse.Temperature = Temperature
    del Temperature

_M_IntelligentHouse._t_Device = IcePy.defineValue('::IntelligentHouse::Device', Ice.Value, -1, (), False, True, None, ())

if 'DevicePrx' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.DevicePrx = Ice.createTempClass()
    class DevicePrx(Ice.ObjectPrx):

        def getState(self, context=None):
            return _M_IntelligentHouse.Device._op_getState.invoke(self, ((), context))

        def getStateAsync(self, context=None):
            return _M_IntelligentHouse.Device._op_getState.invokeAsync(self, ((), context))

        def begin_getState(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.Device._op_getState.begin(self, ((), _response, _ex, _sent, context))

        def end_getState(self, _r):
            return _M_IntelligentHouse.Device._op_getState.end(self, _r)

        def changeWorkingState(self, context=None):
            return _M_IntelligentHouse.Device._op_changeWorkingState.invoke(self, ((), context))

        def changeWorkingStateAsync(self, context=None):
            return _M_IntelligentHouse.Device._op_changeWorkingState.invokeAsync(self, ((), context))

        def begin_changeWorkingState(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.Device._op_changeWorkingState.begin(self, ((), _response, _ex, _sent, context))

        def end_changeWorkingState(self, _r):
            return _M_IntelligentHouse.Device._op_changeWorkingState.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IntelligentHouse.DevicePrx.ice_checkedCast(proxy, '::IntelligentHouse::Device', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IntelligentHouse.DevicePrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::Device'
    _M_IntelligentHouse._t_DevicePrx = IcePy.defineProxy('::IntelligentHouse::Device', DevicePrx)

    _M_IntelligentHouse.DevicePrx = DevicePrx
    del DevicePrx

    _M_IntelligentHouse.Device = Ice.createTempClass()
    class Device(Ice.Object):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IntelligentHouse::Device')

        def ice_id(self, current=None):
            return '::IntelligentHouse::Device'

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::Device'

        def getState(self, current=None):
            raise NotImplementedError("servant method 'getState' not implemented")

        def changeWorkingState(self, current=None):
            raise NotImplementedError("servant method 'changeWorkingState' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IntelligentHouse._t_DeviceDisp)

        __repr__ = __str__

    _M_IntelligentHouse._t_DeviceDisp = IcePy.defineClass('::IntelligentHouse::Device', Device, (), None, ())
    Device._ice_type = _M_IntelligentHouse._t_DeviceDisp

    Device._op_getState = IcePy.Operation('getState', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_IntelligentHouse._t_WorkingState, False, 0), ())
    Device._op_changeWorkingState = IcePy.Operation('changeWorkingState', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, ())

    _M_IntelligentHouse.Device = Device
    del Device

if 'OffDeviceError' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.OffDeviceError = Ice.createTempClass()
    class OffDeviceError(Ice.UserException):
        def __init__(self, reason="Device is turned off. Please turn it on to perform this operation."):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::IntelligentHouse::OffDeviceError'

    _M_IntelligentHouse._t_OffDeviceError = IcePy.defineException('::IntelligentHouse::OffDeviceError', OffDeviceError, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    OffDeviceError._ice_type = _M_IntelligentHouse._t_OffDeviceError

    _M_IntelligentHouse.OffDeviceError = OffDeviceError
    del OffDeviceError

_M_IntelligentHouse._t_TemperatureSensor = IcePy.defineValue('::IntelligentHouse::TemperatureSensor', Ice.Value, -1, (), False, True, None, ())

if 'TemperatureSensorPrx' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.TemperatureSensorPrx = Ice.createTempClass()
    class TemperatureSensorPrx(_M_IntelligentHouse.DevicePrx):

        def getIndoorTemperature(self, context=None):
            return _M_IntelligentHouse.TemperatureSensor._op_getIndoorTemperature.invoke(self, ((), context))

        def getIndoorTemperatureAsync(self, context=None):
            return _M_IntelligentHouse.TemperatureSensor._op_getIndoorTemperature.invokeAsync(self, ((), context))

        def begin_getIndoorTemperature(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.TemperatureSensor._op_getIndoorTemperature.begin(self, ((), _response, _ex, _sent, context))

        def end_getIndoorTemperature(self, _r):
            return _M_IntelligentHouse.TemperatureSensor._op_getIndoorTemperature.end(self, _r)

        def getOutdoorTemperature(self, context=None):
            return _M_IntelligentHouse.TemperatureSensor._op_getOutdoorTemperature.invoke(self, ((), context))

        def getOutdoorTemperatureAsync(self, context=None):
            return _M_IntelligentHouse.TemperatureSensor._op_getOutdoorTemperature.invokeAsync(self, ((), context))

        def begin_getOutdoorTemperature(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.TemperatureSensor._op_getOutdoorTemperature.begin(self, ((), _response, _ex, _sent, context))

        def end_getOutdoorTemperature(self, _r):
            return _M_IntelligentHouse.TemperatureSensor._op_getOutdoorTemperature.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IntelligentHouse.TemperatureSensorPrx.ice_checkedCast(proxy, '::IntelligentHouse::TemperatureSensor', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IntelligentHouse.TemperatureSensorPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::TemperatureSensor'
    _M_IntelligentHouse._t_TemperatureSensorPrx = IcePy.defineProxy('::IntelligentHouse::TemperatureSensor', TemperatureSensorPrx)

    _M_IntelligentHouse.TemperatureSensorPrx = TemperatureSensorPrx
    del TemperatureSensorPrx

    _M_IntelligentHouse.TemperatureSensor = Ice.createTempClass()
    class TemperatureSensor(_M_IntelligentHouse.Device):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IntelligentHouse::Device', '::IntelligentHouse::TemperatureSensor')

        def ice_id(self, current=None):
            return '::IntelligentHouse::TemperatureSensor'

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::TemperatureSensor'

        def getIndoorTemperature(self, current=None):
            raise NotImplementedError("servant method 'getIndoorTemperature' not implemented")

        def getOutdoorTemperature(self, current=None):
            raise NotImplementedError("servant method 'getOutdoorTemperature' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IntelligentHouse._t_TemperatureSensorDisp)

        __repr__ = __str__

    _M_IntelligentHouse._t_TemperatureSensorDisp = IcePy.defineClass('::IntelligentHouse::TemperatureSensor', TemperatureSensor, (), None, (_M_IntelligentHouse._t_DeviceDisp,))
    TemperatureSensor._ice_type = _M_IntelligentHouse._t_TemperatureSensorDisp

    TemperatureSensor._op_getIndoorTemperature = IcePy.Operation('getIndoorTemperature', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_IntelligentHouse._t_Temperature, False, 0), (_M_IntelligentHouse._t_OffDeviceError,))
    TemperatureSensor._op_getOutdoorTemperature = IcePy.Operation('getOutdoorTemperature', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_IntelligentHouse._t_Temperature, False, 0), (_M_IntelligentHouse._t_OffDeviceError,))

    _M_IntelligentHouse.TemperatureSensor = TemperatureSensor
    del TemperatureSensor

_M_IntelligentHouse._t_Fridge = IcePy.defineValue('::IntelligentHouse::Fridge', Ice.Value, -1, (), False, True, None, ())

if 'FridgePrx' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.FridgePrx = Ice.createTempClass()
    class FridgePrx(_M_IntelligentHouse.DevicePrx):

        def getCurrentTemperature(self, context=None):
            return _M_IntelligentHouse.Fridge._op_getCurrentTemperature.invoke(self, ((), context))

        def getCurrentTemperatureAsync(self, context=None):
            return _M_IntelligentHouse.Fridge._op_getCurrentTemperature.invokeAsync(self, ((), context))

        def begin_getCurrentTemperature(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.Fridge._op_getCurrentTemperature.begin(self, ((), _response, _ex, _sent, context))

        def end_getCurrentTemperature(self, _r):
            return _M_IntelligentHouse.Fridge._op_getCurrentTemperature.end(self, _r)

        def changeTemperature(self, temperature, context=None):
            return _M_IntelligentHouse.Fridge._op_changeTemperature.invoke(self, ((temperature, ), context))

        def changeTemperatureAsync(self, temperature, context=None):
            return _M_IntelligentHouse.Fridge._op_changeTemperature.invokeAsync(self, ((temperature, ), context))

        def begin_changeTemperature(self, temperature, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.Fridge._op_changeTemperature.begin(self, ((temperature, ), _response, _ex, _sent, context))

        def end_changeTemperature(self, _r):
            return _M_IntelligentHouse.Fridge._op_changeTemperature.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IntelligentHouse.FridgePrx.ice_checkedCast(proxy, '::IntelligentHouse::Fridge', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IntelligentHouse.FridgePrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::Fridge'
    _M_IntelligentHouse._t_FridgePrx = IcePy.defineProxy('::IntelligentHouse::Fridge', FridgePrx)

    _M_IntelligentHouse.FridgePrx = FridgePrx
    del FridgePrx

    _M_IntelligentHouse.Fridge = Ice.createTempClass()
    class Fridge(_M_IntelligentHouse.Device):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IntelligentHouse::Device', '::IntelligentHouse::Fridge')

        def ice_id(self, current=None):
            return '::IntelligentHouse::Fridge'

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::Fridge'

        def getCurrentTemperature(self, current=None):
            raise NotImplementedError("servant method 'getCurrentTemperature' not implemented")

        def changeTemperature(self, temperature, current=None):
            raise NotImplementedError("servant method 'changeTemperature' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IntelligentHouse._t_FridgeDisp)

        __repr__ = __str__

    _M_IntelligentHouse._t_FridgeDisp = IcePy.defineClass('::IntelligentHouse::Fridge', Fridge, (), None, (_M_IntelligentHouse._t_DeviceDisp,))
    Fridge._ice_type = _M_IntelligentHouse._t_FridgeDisp

    Fridge._op_getCurrentTemperature = IcePy.Operation('getCurrentTemperature', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_IntelligentHouse._t_Temperature, False, 0), (_M_IntelligentHouse._t_OffDeviceError,))
    Fridge._op_changeTemperature = IcePy.Operation('changeTemperature', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_IntelligentHouse._t_Temperature, False, 0),), (), None, (_M_IntelligentHouse._t_OffDeviceError,))

    _M_IntelligentHouse.Fridge = Fridge
    del Fridge

if 'Color' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.Color = Ice.createTempClass()
    class Color(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    Color.RED = Color("RED", 0)
    Color.GREEN = Color("GREEN", 1)
    Color.BLUE = Color("BLUE", 2)
    Color._enumerators = { 0:Color.RED, 1:Color.GREEN, 2:Color.BLUE }

    _M_IntelligentHouse._t_Color = IcePy.defineEnum('::IntelligentHouse::Color', Color, (), Color._enumerators)

    _M_IntelligentHouse.Color = Color
    del Color

_M_IntelligentHouse._t_Lamp = IcePy.defineValue('::IntelligentHouse::Lamp', Ice.Value, -1, (), False, True, None, ())

if 'LampPrx' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.LampPrx = Ice.createTempClass()
    class LampPrx(_M_IntelligentHouse.DevicePrx):

        def changeColor(self, color, context=None):
            return _M_IntelligentHouse.Lamp._op_changeColor.invoke(self, ((color, ), context))

        def changeColorAsync(self, color, context=None):
            return _M_IntelligentHouse.Lamp._op_changeColor.invokeAsync(self, ((color, ), context))

        def begin_changeColor(self, color, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.Lamp._op_changeColor.begin(self, ((color, ), _response, _ex, _sent, context))

        def end_changeColor(self, _r):
            return _M_IntelligentHouse.Lamp._op_changeColor.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IntelligentHouse.LampPrx.ice_checkedCast(proxy, '::IntelligentHouse::Lamp', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IntelligentHouse.LampPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::Lamp'
    _M_IntelligentHouse._t_LampPrx = IcePy.defineProxy('::IntelligentHouse::Lamp', LampPrx)

    _M_IntelligentHouse.LampPrx = LampPrx
    del LampPrx

    _M_IntelligentHouse.Lamp = Ice.createTempClass()
    class Lamp(_M_IntelligentHouse.Device):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IntelligentHouse::Device', '::IntelligentHouse::Lamp')

        def ice_id(self, current=None):
            return '::IntelligentHouse::Lamp'

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::Lamp'

        def changeColor(self, color, current=None):
            raise NotImplementedError("servant method 'changeColor' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IntelligentHouse._t_LampDisp)

        __repr__ = __str__

    _M_IntelligentHouse._t_LampDisp = IcePy.defineClass('::IntelligentHouse::Lamp', Lamp, (), None, (_M_IntelligentHouse._t_DeviceDisp,))
    Lamp._ice_type = _M_IntelligentHouse._t_LampDisp

    Lamp._op_changeColor = IcePy.Operation('changeColor', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_IntelligentHouse._t_Color, False, 0),), (), None, ())

    _M_IntelligentHouse.Lamp = Lamp
    del Lamp

_M_IntelligentHouse._t_FlashingLamp = IcePy.defineValue('::IntelligentHouse::FlashingLamp', Ice.Value, -1, (), False, True, None, ())

if 'FlashingLampPrx' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.FlashingLampPrx = Ice.createTempClass()
    class FlashingLampPrx(_M_IntelligentHouse.LampPrx):

        def setFrequency(self, frequency, context=None):
            return _M_IntelligentHouse.FlashingLamp._op_setFrequency.invoke(self, ((frequency, ), context))

        def setFrequencyAsync(self, frequency, context=None):
            return _M_IntelligentHouse.FlashingLamp._op_setFrequency.invokeAsync(self, ((frequency, ), context))

        def begin_setFrequency(self, frequency, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.FlashingLamp._op_setFrequency.begin(self, ((frequency, ), _response, _ex, _sent, context))

        def end_setFrequency(self, _r):
            return _M_IntelligentHouse.FlashingLamp._op_setFrequency.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IntelligentHouse.FlashingLampPrx.ice_checkedCast(proxy, '::IntelligentHouse::FlashingLamp', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IntelligentHouse.FlashingLampPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::FlashingLamp'
    _M_IntelligentHouse._t_FlashingLampPrx = IcePy.defineProxy('::IntelligentHouse::FlashingLamp', FlashingLampPrx)

    _M_IntelligentHouse.FlashingLampPrx = FlashingLampPrx
    del FlashingLampPrx

    _M_IntelligentHouse.FlashingLamp = Ice.createTempClass()
    class FlashingLamp(_M_IntelligentHouse.Lamp):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IntelligentHouse::Device', '::IntelligentHouse::FlashingLamp', '::IntelligentHouse::Lamp')

        def ice_id(self, current=None):
            return '::IntelligentHouse::FlashingLamp'

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::FlashingLamp'

        def setFrequency(self, frequency, current=None):
            raise NotImplementedError("servant method 'setFrequency' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IntelligentHouse._t_FlashingLampDisp)

        __repr__ = __str__

    _M_IntelligentHouse._t_FlashingLampDisp = IcePy.defineClass('::IntelligentHouse::FlashingLamp', FlashingLamp, (), None, (_M_IntelligentHouse._t_LampDisp,))
    FlashingLamp._ice_type = _M_IntelligentHouse._t_FlashingLampDisp

    FlashingLamp._op_setFrequency = IcePy.Operation('setFrequency', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_int, False, 0),), (), None, ())

    _M_IntelligentHouse.FlashingLamp = FlashingLamp
    del FlashingLamp

_M_IntelligentHouse._t_MotionLamp = IcePy.defineValue('::IntelligentHouse::MotionLamp', Ice.Value, -1, (), False, True, None, ())

if 'MotionLampPrx' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.MotionLampPrx = Ice.createTempClass()
    class MotionLampPrx(_M_IntelligentHouse.LampPrx):

        def setTime(self, time, context=None):
            return _M_IntelligentHouse.MotionLamp._op_setTime.invoke(self, ((time, ), context))

        def setTimeAsync(self, time, context=None):
            return _M_IntelligentHouse.MotionLamp._op_setTime.invokeAsync(self, ((time, ), context))

        def begin_setTime(self, time, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.MotionLamp._op_setTime.begin(self, ((time, ), _response, _ex, _sent, context))

        def end_setTime(self, _r):
            return _M_IntelligentHouse.MotionLamp._op_setTime.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IntelligentHouse.MotionLampPrx.ice_checkedCast(proxy, '::IntelligentHouse::MotionLamp', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IntelligentHouse.MotionLampPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::MotionLamp'
    _M_IntelligentHouse._t_MotionLampPrx = IcePy.defineProxy('::IntelligentHouse::MotionLamp', MotionLampPrx)

    _M_IntelligentHouse.MotionLampPrx = MotionLampPrx
    del MotionLampPrx

    _M_IntelligentHouse.MotionLamp = Ice.createTempClass()
    class MotionLamp(_M_IntelligentHouse.Lamp):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IntelligentHouse::Device', '::IntelligentHouse::Lamp', '::IntelligentHouse::MotionLamp')

        def ice_id(self, current=None):
            return '::IntelligentHouse::MotionLamp'

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::MotionLamp'

        def setTime(self, time, current=None):
            raise NotImplementedError("servant method 'setTime' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IntelligentHouse._t_MotionLampDisp)

        __repr__ = __str__

    _M_IntelligentHouse._t_MotionLampDisp = IcePy.defineClass('::IntelligentHouse::MotionLamp', MotionLamp, (), None, (_M_IntelligentHouse._t_LampDisp,))
    MotionLamp._ice_type = _M_IntelligentHouse._t_MotionLampDisp

    MotionLamp._op_setTime = IcePy.Operation('setTime', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_int, False, 0),), (), None, ())

    _M_IntelligentHouse.MotionLamp = MotionLamp
    del MotionLamp

if 'DayPeriod' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.DayPeriod = Ice.createTempClass()
    class DayPeriod(object):
        def __init__(self, startHour=0, endHour=0):
            self.startHour = startHour
            self.endHour = endHour

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.startHour)
            _h = 5 * _h + Ice.getHash(self.endHour)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_IntelligentHouse.DayPeriod):
                return NotImplemented
            else:
                if self.startHour is None or other.startHour is None:
                    if self.startHour != other.startHour:
                        return (-1 if self.startHour is None else 1)
                else:
                    if self.startHour < other.startHour:
                        return -1
                    elif self.startHour > other.startHour:
                        return 1
                if self.endHour is None or other.endHour is None:
                    if self.endHour != other.endHour:
                        return (-1 if self.endHour is None else 1)
                else:
                    if self.endHour < other.endHour:
                        return -1
                    elif self.endHour > other.endHour:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_IntelligentHouse._t_DayPeriod)

        __repr__ = __str__

    _M_IntelligentHouse._t_DayPeriod = IcePy.defineStruct('::IntelligentHouse::DayPeriod', DayPeriod, (), (
        ('startHour', (), IcePy._t_int),
        ('endHour', (), IcePy._t_int)
    ))

    _M_IntelligentHouse.DayPeriod = DayPeriod
    del DayPeriod

if '_t_HeatingPlan' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse._t_HeatingPlan = IcePy.defineDictionary('::IntelligentHouse::HeatingPlan', (), _M_IntelligentHouse._t_DayPeriod, _M_IntelligentHouse._t_Temperature)

_M_IntelligentHouse._t_ElectricStove = IcePy.defineValue('::IntelligentHouse::ElectricStove', Ice.Value, -1, (), False, True, None, ())

if 'ElectricStovePrx' not in _M_IntelligentHouse.__dict__:
    _M_IntelligentHouse.ElectricStovePrx = Ice.createTempClass()
    class ElectricStovePrx(_M_IntelligentHouse.TemperatureSensorPrx):

        def setDefaultTemperature(self, t, context=None):
            return _M_IntelligentHouse.ElectricStove._op_setDefaultTemperature.invoke(self, ((t, ), context))

        def setDefaultTemperatureAsync(self, t, context=None):
            return _M_IntelligentHouse.ElectricStove._op_setDefaultTemperature.invokeAsync(self, ((t, ), context))

        def begin_setDefaultTemperature(self, t, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.ElectricStove._op_setDefaultTemperature.begin(self, ((t, ), _response, _ex, _sent, context))

        def end_setDefaultTemperature(self, _r):
            return _M_IntelligentHouse.ElectricStove._op_setDefaultTemperature.end(self, _r)

        def getDefaultTemperature(self, context=None):
            return _M_IntelligentHouse.ElectricStove._op_getDefaultTemperature.invoke(self, ((), context))

        def getDefaultTemperatureAsync(self, context=None):
            return _M_IntelligentHouse.ElectricStove._op_getDefaultTemperature.invokeAsync(self, ((), context))

        def begin_getDefaultTemperature(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.ElectricStove._op_getDefaultTemperature.begin(self, ((), _response, _ex, _sent, context))

        def end_getDefaultTemperature(self, _r):
            return _M_IntelligentHouse.ElectricStove._op_getDefaultTemperature.end(self, _r)

        def getHeatingPlan(self, context=None):
            return _M_IntelligentHouse.ElectricStove._op_getHeatingPlan.invoke(self, ((), context))

        def getHeatingPlanAsync(self, context=None):
            return _M_IntelligentHouse.ElectricStove._op_getHeatingPlan.invokeAsync(self, ((), context))

        def begin_getHeatingPlan(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.ElectricStove._op_getHeatingPlan.begin(self, ((), _response, _ex, _sent, context))

        def end_getHeatingPlan(self, _r):
            return _M_IntelligentHouse.ElectricStove._op_getHeatingPlan.end(self, _r)

        def changeHeatingPlanElement(self, dayPeriod, temperature, context=None):
            return _M_IntelligentHouse.ElectricStove._op_changeHeatingPlanElement.invoke(self, ((dayPeriod, temperature), context))

        def changeHeatingPlanElementAsync(self, dayPeriod, temperature, context=None):
            return _M_IntelligentHouse.ElectricStove._op_changeHeatingPlanElement.invokeAsync(self, ((dayPeriod, temperature), context))

        def begin_changeHeatingPlanElement(self, dayPeriod, temperature, _response=None, _ex=None, _sent=None, context=None):
            return _M_IntelligentHouse.ElectricStove._op_changeHeatingPlanElement.begin(self, ((dayPeriod, temperature), _response, _ex, _sent, context))

        def end_changeHeatingPlanElement(self, _r):
            return _M_IntelligentHouse.ElectricStove._op_changeHeatingPlanElement.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IntelligentHouse.ElectricStovePrx.ice_checkedCast(proxy, '::IntelligentHouse::ElectricStove', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IntelligentHouse.ElectricStovePrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::ElectricStove'
    _M_IntelligentHouse._t_ElectricStovePrx = IcePy.defineProxy('::IntelligentHouse::ElectricStove', ElectricStovePrx)

    _M_IntelligentHouse.ElectricStovePrx = ElectricStovePrx
    del ElectricStovePrx

    _M_IntelligentHouse.ElectricStove = Ice.createTempClass()
    class ElectricStove(_M_IntelligentHouse.TemperatureSensor):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IntelligentHouse::Device', '::IntelligentHouse::ElectricStove', '::IntelligentHouse::TemperatureSensor')

        def ice_id(self, current=None):
            return '::IntelligentHouse::ElectricStove'

        @staticmethod
        def ice_staticId():
            return '::IntelligentHouse::ElectricStove'

        def setDefaultTemperature(self, t, current=None):
            raise NotImplementedError("servant method 'setDefaultTemperature' not implemented")

        def getDefaultTemperature(self, current=None):
            raise NotImplementedError("servant method 'getDefaultTemperature' not implemented")

        def getHeatingPlan(self, current=None):
            raise NotImplementedError("servant method 'getHeatingPlan' not implemented")

        def changeHeatingPlanElement(self, dayPeriod, temperature, current=None):
            raise NotImplementedError("servant method 'changeHeatingPlanElement' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IntelligentHouse._t_ElectricStoveDisp)

        __repr__ = __str__

    _M_IntelligentHouse._t_ElectricStoveDisp = IcePy.defineClass('::IntelligentHouse::ElectricStove', ElectricStove, (), None, (_M_IntelligentHouse._t_TemperatureSensorDisp,))
    ElectricStove._ice_type = _M_IntelligentHouse._t_ElectricStoveDisp

    ElectricStove._op_setDefaultTemperature = IcePy.Operation('setDefaultTemperature', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_IntelligentHouse._t_Temperature, False, 0),), (), None, ())
    ElectricStove._op_getDefaultTemperature = IcePy.Operation('getDefaultTemperature', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_IntelligentHouse._t_Temperature, False, 0), ())
    ElectricStove._op_getHeatingPlan = IcePy.Operation('getHeatingPlan', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_IntelligentHouse._t_HeatingPlan, False, 0), ())
    ElectricStove._op_changeHeatingPlanElement = IcePy.Operation('changeHeatingPlanElement', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_IntelligentHouse._t_DayPeriod, False, 0), ((), _M_IntelligentHouse._t_Temperature, False, 0)), (), None, ())

    _M_IntelligentHouse.ElectricStove = ElectricStove
    del ElectricStove

# End of module IntelligentHouse