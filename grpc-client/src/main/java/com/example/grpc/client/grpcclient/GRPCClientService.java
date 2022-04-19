package com.example.grpc.client.grpcclient;

import com.example.grpc.server.grpcserver.PingRequest;
import com.example.grpc.server.grpcserver.PongResponse;
import com.example.grpc.server.grpcserver.PingPongServiceGrpc;
import com.example.grpc.server.grpcserver.MatrixRequest;

import java.util.Arrays;

import com.example.grpc.server.grpcserver.MatrixReply;
import com.example.grpc.server.grpcserver.MatrixServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
@Service
public class GRPCClientService {
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
    

	public int[][] mult(int[][] mata, int[][] matb) {
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
		MatrixServiceGrpc.MatrixServiceBlockingStub stub1 =MatrixServiceGrpc.newBlockingStub(channel);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub2 =MatrixServiceGrpc.newBlockingStub(channel);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub3 =MatrixServiceGrpc.newBlockingStub(channel);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub4 =MatrixServiceGrpc.newBlockingStub(channel);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub5 =MatrixServiceGrpc.newBlockingStub(channel);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub6 =MatrixServiceGrpc.newBlockingStub(channel);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub7 =MatrixServiceGrpc.newBlockingStub(channel);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub8 =MatrixServiceGrpc.newBlockingStub(channel);
		String AP1 = stub1.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(A1)).setB(Arrays.deepToString(A2)).build()).getC();
		String AP2 = stub2.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(B1)).setB(Arrays.deepToString(C2)).build()).getC();
		String BP1 = stub3.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(A1)).setB(Arrays.deepToString(B2)).build()).getC();
		String BP2 = stub4.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(B1)).setB(Arrays.deepToString(D2)).build()).getC();
		String CP1 = stub5.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(C1)).setB(Arrays.deepToString(A2)).build()).getC();
		String CP2 = stub6.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(D1)).setB(Arrays.deepToString(C2)).build()).getC();
		String DP1 = stub7.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(C1)).setB(Arrays.deepToString(B2)).build()).getC();
		String DP2 = stub8.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(D1)).setB(Arrays.deepToString(D2)).build()).getC();
		String A3 = stub1.addBlock(MatrixRequest.newBuilder().setA(AP1).setB(AP2).build()).getC();
		String B3 = stub2.addBlock(MatrixRequest.newBuilder().setA(BP1).setB(BP2).build()).getC();
		String C3 = stub3.addBlock(MatrixRequest.newBuilder().setA(CP1).setB(CP2).build()).getC();
		String D3 = stub4.addBlock(MatrixRequest.newBuilder().setA(DP1).setB(DP2).build()).getC();
		A = stringToDeep(A3);
		B = stringToDeep(B3);
		C = stringToDeep(C3);
		D = stringToDeep(D3);
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
		channel.shutdown();
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
