package com.example.grpc.client.grpcclient;

import com.example.grpc.server.grpcserver.HelloRequest;
import com.example.grpc.server.grpcserver.HelloResponse;
import com.example.grpc.server.grpcserver.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class GrpcClientApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
		/*ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", 9090)
				.usePlaintext()
				.build();

		HelloServiceGrpc.HelloServiceBlockingStub stub
				= HelloServiceGrpc.newBlockingStub(ch);

		HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
				.setFirstName("Baeldung")
				.setLastName("gRPC")
				.build());

		System.out.println(helloResponse);

		ch.shutdown();*/

		/*ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", 8080)
				.usePlaintext()
				.build();

		HelloServiceGrpc.HelloServiceBlockingStub stub
				= HelloServiceGrpc.newBlockingStub(ch);

		HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
				.setFirstName("Baeldung")
				.setLastName("gRPC")
				.build());

		ch.shutdown();*/
//		SpringApplication.run(GrpcClientApplication.class, args);
	}

}
