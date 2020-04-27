import grpc
import time
import sys
from grpc._channel import _MultiThreadedRendezvous
from src.gen import event_pb2 as event, event_pb2_grpc as eventGrpc

# sys.path = "../../.."

channel = grpc.insecure_channel('localhost:50051')
stub = eventGrpc.EventNotifyStub(channel)

cities = set([])
print('Provide Cities\' numbers in one line separated by spaces')
for city in event._CITY.values:
    print(str(city.index) + ' (' + city.name + ')')

cityNumbers = input().split(" ")
for cityNumber in cityNumbers:
    cities.add(int(cityNumber))
print('Provide Type number')
for e in event._EVENTTYPE.values:
    print(str(e.index) + ' (' + e.name + ')')
print('------------')

subscribedEvent = event._EVENTTYPE.values[int(input())].index
request = event.Subscription(cities=list(cities), event=subscribedEvent)


def subscribe(request):
    stream = stub.GetInterestingEvents(request)
    try:
        for e in stream:
            print(event._EVENTTYPE.values[e.event].name)
            print(e.description)
            print(e.organizers)
            print(event._CITY.values[e.city].name)
            print('------------')
    except _MultiThreadedRendezvous:
        return


while True:
    subscribe(request)
    time.sleep(4)
    print("Trying to renew connection...")
