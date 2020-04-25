package gen.event;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.0)",
    comments = "Source: event.proto")
public final class EventNotifyGrpc {

  private EventNotifyGrpc() {}

  public static final String SERVICE_NAME = "event.EventNotify";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gen.event.Subscription,
      gen.event.Notification> getGetInterestingEventsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetInterestingEvents",
      requestType = gen.event.Subscription.class,
      responseType = gen.event.Notification.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<gen.event.Subscription,
      gen.event.Notification> getGetInterestingEventsMethod() {
    io.grpc.MethodDescriptor<gen.event.Subscription, gen.event.Notification> getGetInterestingEventsMethod;
    if ((getGetInterestingEventsMethod = EventNotifyGrpc.getGetInterestingEventsMethod) == null) {
      synchronized (EventNotifyGrpc.class) {
        if ((getGetInterestingEventsMethod = EventNotifyGrpc.getGetInterestingEventsMethod) == null) {
          EventNotifyGrpc.getGetInterestingEventsMethod = getGetInterestingEventsMethod =
              io.grpc.MethodDescriptor.<gen.event.Subscription, gen.event.Notification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetInterestingEvents"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gen.event.Subscription.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gen.event.Notification.getDefaultInstance()))
              .setSchemaDescriptor(new EventNotifyMethodDescriptorSupplier("GetInterestingEvents"))
              .build();
        }
      }
    }
    return getGetInterestingEventsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EventNotifyStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventNotifyStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventNotifyStub>() {
        @java.lang.Override
        public EventNotifyStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventNotifyStub(channel, callOptions);
        }
      };
    return EventNotifyStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EventNotifyBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventNotifyBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventNotifyBlockingStub>() {
        @java.lang.Override
        public EventNotifyBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventNotifyBlockingStub(channel, callOptions);
        }
      };
    return EventNotifyBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EventNotifyFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EventNotifyFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EventNotifyFutureStub>() {
        @java.lang.Override
        public EventNotifyFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EventNotifyFutureStub(channel, callOptions);
        }
      };
    return EventNotifyFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class EventNotifyImplBase implements io.grpc.BindableService {

    /**
     */
    public void getInterestingEvents(gen.event.Subscription request,
        io.grpc.stub.StreamObserver<gen.event.Notification> responseObserver) {
      asyncUnimplementedUnaryCall(getGetInterestingEventsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetInterestingEventsMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                gen.event.Subscription,
                gen.event.Notification>(
                  this, METHODID_GET_INTERESTING_EVENTS)))
          .build();
    }
  }

  /**
   */
  public static final class EventNotifyStub extends io.grpc.stub.AbstractAsyncStub<EventNotifyStub> {
    private EventNotifyStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventNotifyStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventNotifyStub(channel, callOptions);
    }

    /**
     */
    public void getInterestingEvents(gen.event.Subscription request,
        io.grpc.stub.StreamObserver<gen.event.Notification> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetInterestingEventsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class EventNotifyBlockingStub extends io.grpc.stub.AbstractBlockingStub<EventNotifyBlockingStub> {
    private EventNotifyBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventNotifyBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventNotifyBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<gen.event.Notification> getInterestingEvents(
        gen.event.Subscription request) {
      return blockingServerStreamingCall(
          getChannel(), getGetInterestingEventsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EventNotifyFutureStub extends io.grpc.stub.AbstractFutureStub<EventNotifyFutureStub> {
    private EventNotifyFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EventNotifyFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EventNotifyFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GET_INTERESTING_EVENTS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EventNotifyImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EventNotifyImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_INTERESTING_EVENTS:
          serviceImpl.getInterestingEvents((gen.event.Subscription) request,
              (io.grpc.stub.StreamObserver<gen.event.Notification>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class EventNotifyBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EventNotifyBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gen.event.EventProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EventNotify");
    }
  }

  private static final class EventNotifyFileDescriptorSupplier
      extends EventNotifyBaseDescriptorSupplier {
    EventNotifyFileDescriptorSupplier() {}
  }

  private static final class EventNotifyMethodDescriptorSupplier
      extends EventNotifyBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EventNotifyMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EventNotifyGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EventNotifyFileDescriptorSupplier())
              .addMethod(getGetInterestingEventsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
