package com.example.grpc.client.grpcclient;

import com.example.grpc.server.grpcserver.PingRequest;
import com.example.grpc.server.grpcserver.PongResponse;
import com.example.grpc.server.grpcserver.PingPongServiceGrpc;
import com.example.grpc.server.grpcserver.MatrixRequest;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.grpc.server.grpcserver.MatrixReply;
import com.example.grpc.server.grpcserver.MatrixServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
@Service
public class GRPCClientService {

	@Autowired
	matrixcal matcal;

    public String ping() {
        	ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();        
		PingPongServiceGrpc.PingPongServiceBlockingStub stub
                = PingPongServiceGrpc.newBlockingStub(channel);        
		PongResponse helloResponse = stub.ping(PingRequest.newBuilder()
                .setPing("")
                .build());        
		channel.shutdown();        
		return helloResponse.getPong();
    }
    MatrixServiceGrpc.MatrixServiceBlockingStub[] stubs = new MatrixServiceGrpc.MatrixServiceBlockingStub[8];
	int stubnum = 0;
	public int[][] mult(int[][] mata, int[][] matb, int deadline) throws InterruptedException, ExecutionException {
		ManagedChannel channel1 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel channel2 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel channel3 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel channel4 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel channel5 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel channel6 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel channel7 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel channel8 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		int MAX = mata.length;
		int bSize = MAX/2;
		int[][] A = new int[bSize][bSize];
    	int[][] B = new int[bSize][bSize];
    	int[][] C = new int[bSize][bSize];
    	int[][] D = new int[bSize][bSize];
		int[][] A1 = new int[bSize][bSize];
		int[][] A2 = new int[bSize][bSize];
		int[][] B1 = new int[bSize][bSize];
		int[][] B2 = new int[bSize][bSize];
		int[][] C1 = new int[bSize][bSize];
		int[][] C2 = new int[bSize][bSize];
		int[][] D1 = new int[bSize][bSize];
		int[][] D2 = new int[bSize][bSize];
		for (int i = 0; i < bSize; i++) 
        { 
            for (int j = 0; j < bSize; j++)
            {
                A1[i][j]=mata[i][j];
                A2[i][j]=matb[i][j];
            }
        }
    	for (int i = 0; i < bSize; i++) 
        { 
            for (int j = bSize; j < MAX; j++)
            {
                B1[i][j-bSize]=mata[i][j];
                B2[i][j-bSize]=matb[i][j];
            }
        }
    	for (int i = bSize; i < MAX; i++) 
        { 
            for (int j = 0; j < bSize; j++)
            {
                C1[i-bSize][j]=mata[i][j];
                C2[i-bSize][j]=matb[i][j];
            }
        } 
    	for (int i = bSize; i < MAX; i++) 
        { 
            for (int j = bSize; j < MAX; j++)
            {
                D1[i-bSize][j-bSize]=mata[i][j];
                D2[i-bSize][j-bSize]=matb[i][j];
            }
        }
		MatrixServiceGrpc.MatrixServiceBlockingStub stub1 =MatrixServiceGrpc.newBlockingStub(channel1);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub2 =MatrixServiceGrpc.newBlockingStub(channel2);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub3 =MatrixServiceGrpc.newBlockingStub(channel3);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub4 =MatrixServiceGrpc.newBlockingStub(channel4);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub5 =MatrixServiceGrpc.newBlockingStub(channel5);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub6 =MatrixServiceGrpc.newBlockingStub(channel6);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub7 =MatrixServiceGrpc.newBlockingStub(channel7);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub8 =MatrixServiceGrpc.newBlockingStub(channel8);
		long starttime = System.currentTimeMillis(); 
		String AP1 = stub1.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(A1)).setB(Arrays.deepToString(A2)).build()).getC();
		long stoptime = System.currentTimeMillis();
		long footprint = stoptime - starttime; //timetaken for just 1 block of multiplication, so that we can find time reqd for entire operation
		int serversneeded = (int) Math.ceil( (footprint*12)/deadline);

		stubs[0] = stub1;
		stubs[1] = stub2;
		stubs[2] = stub3;
		stubs[3] = stub4;
		stubs[4] = stub5;
		stubs[5] = stub6;
		stubs[6] = stub7;
		stubs[7] = stub8;
		int a= 0;
		//APART1
		//String AP1 = stub1.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(A1)).setB(Arrays.deepToString(A2)).build()).getC();
		CompletableFuture<String> AmatP1 = matcal.matrixCalc(Arrays.deepToString(A1), Arrays.deepToString(A2), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> AmatP2 = matcal.matrixCalc(Arrays.deepToString(B1), Arrays.deepToString(C2), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> BmatP1 = matcal.matrixCalc(Arrays.deepToString(A1), Arrays.deepToString(B2), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> BmatP2 = matcal.matrixCalc(Arrays.deepToString(B1), Arrays.deepToString(D2), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> CmatP1 = matcal.matrixCalc(Arrays.deepToString(C1), Arrays.deepToString(A2), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> CmatP2 = matcal.matrixCalc(Arrays.deepToString(D1), Arrays.deepToString(C2), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> DmatP1 = matcal.matrixCalc(Arrays.deepToString(C1), Arrays.deepToString(B2), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> DmatP2 = matcal.matrixCalc(Arrays.deepToString(D1), Arrays.deepToString(D2), stubs[a<serversneeded ? a++ : 0]);
		
		CompletableFuture<String> A3 = matcal.matrixAdd(AmatP1.get(), AmatP2.get(), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> B3 = matcal.matrixAdd(BmatP1.get(), BmatP2.get(), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> C3 = matcal.matrixAdd(CmatP1.get(), CmatP2.get(), stubs[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> D3 = matcal.matrixAdd(DmatP1.get(), DmatP2.get(), stubs[a<serversneeded ? a++ : 0]);

		A = stringToDeep(A3.get());
		B = stringToDeep(B3.get());
		C = stringToDeep(C3.get());
		D = stringToDeep(D3.get());
		int[][] res = new int[MAX][MAX];
		for (int i = 0; i < bSize; i++) 
        { 
            for (int j = 0; j < bSize; j++)
            {
                res[i][j]=A[i][j];
            }
        }
    	for (int i = 0; i < bSize; i++) 
        { 
            for (int j = bSize; j < MAX; j++)
            {
                res[i][j]=B[i][j-bSize];
            }
        }
    	for (int i = bSize; i < MAX; i++) 
        { 
            for (int j = 0; j < bSize; j++)
            {
                res[i][j]=C[i-bSize][j];
            }
        } 
    	for (int i = bSize; i < MAX; i++) 
        { 
            for (int j = bSize; j < MAX; j++)
            {
                res[i][j]=D[i-bSize][j-bSize];
            }
        } 
    	for (int i=0; i<MAX; i++)
    	{
    		for (int j=0; j<MAX;j++)
    		{
    			System.out.print(res[i][j]+" ");
    		}
    		System.out.println("");
    	}
		channel1.shutdown();
		channel2.shutdown();
		channel3.shutdown();
		channel4.shutdown();
		channel5.shutdown();
		channel6.shutdown();
		channel7.shutdown();
		channel8.shutdown();
    	return res;
		//int[][] matrc = stringToDeep(res);
	
}
//String to Matrix
private static int[][] stringToDeep(String str) {
	int row = 0;
	int col = 0;
	for (int i = 0; i < str.length(); i++) {
		if (str.charAt(i) == '[') {
			row++;
		}
	}
	row--;
	for (int i = 0;; i++) {
		if (str.charAt(i) == ',') {
			col++;
		}
		if (str.charAt(i) == ']') {
			break;
		}
	}
	col++;
	int[][] out = new int[row][col];
	str = str.replaceAll("\\[", "").replaceAll("\\]", "");
	String[] s1 = str.split(", ");
	int j = -1;
	for (int i = 0; i < s1.length; i++) {
		if (i % col == 0) {
			j++;
		}
		out[j][i % col] = Integer.parseInt(s1[i]);
	}
	return out;
}
}
