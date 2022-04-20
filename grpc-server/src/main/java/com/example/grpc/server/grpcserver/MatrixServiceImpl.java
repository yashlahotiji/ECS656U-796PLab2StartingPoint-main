package com.example.grpc.server.grpcserver;


import java.util.Arrays;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
@GrpcService
public class MatrixServiceImpl extends MatrixServiceGrpc.MatrixServiceImplBase
{
	@Override
	public void addBlock(MatrixRequest request, StreamObserver<MatrixReply> reply)
	{

		String a = request.getA();
		String b = request.getB();
		int[][] matrixfirst = stringToDeep(a);
		int[][] matrixsecond = stringToDeep(b);
		int[][] matrixthird = new int[matrixfirst.length][matrixfirst[0].length];
		int i,j;
		for(i=0;i<matrixfirst.length;i++){
			for(j=0;j<matrixsecond.length;j++){
					matrixthird[i][j] = matrixfirst[i][j]+matrixsecond[i][j];
			}
		}
		MatrixReply response = MatrixReply.newBuilder().setC(Arrays.deepToString(matrixthird)).build();
		reply.onNext(response);
		reply.onCompleted();
    }
	@Override
    	public void multiplyBlock(MatrixRequest request, StreamObserver<MatrixReply> reply)
    	{

		String a = request.getA();
		String b = request.getB();
		int[][] matrixfirst = stringToDeep(a);
		int[][] matrixsecond = stringToDeep(b);
		int[][] matrixthird = new int[matrixfirst.length][matrixfirst[0].length];
		int p,q,r;
		for(p=0;p<matrixfirst.length;p++){
			for(q=0;q<matrixsecond.length;q++){
				for(r=0;r<matrixsecond.length;r++){
					matrixthird[p][q]+= matrixfirst[p][r]*matrixsecond[r][q];
				}
			}
		}
		MatrixReply response = MatrixReply.newBuilder().setC(Arrays.deepToString(matrixthird)).build();
		reply.onNext(response);
		reply.onCompleted();
    }

	private static int[][] stringToDeep(String s) {
		int row = 0;
		int column = 0;
		for (int p = 0; p < s.length(); p++) {
			if (s.charAt(p) == '[') {
				row++;
			}
		}
		row--;
		for (int p = 0;; p++) {
			if (s.charAt(p) == ',') {
				column++;
			}
			if (s.charAt(p) == ']') {
				break;
			}
		}
		column++;
		int[][] out = new int[row][column];
		s = s.replaceAll("\\[", "").replaceAll("\\]", "");
		String[] string1 = s.split(", ");
		int j = -1;
		for (int i = 0; i < string1.length; i++) {
			if (i % column == 0) {
				j++;
			}
			out[j][i % column] = Integer.parseInt(string1[i]);
		}
		return out;
	}
}
