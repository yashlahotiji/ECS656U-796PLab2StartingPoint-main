package com.example.grpc.client.grpcclient;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class PingPongEndpoint
{    
	GRPCClientService grpcClientService;    
	@Autowired
    	public PingPongEndpoint(GRPCClientService grpcClientService) {
        	this.grpcClientService = grpcClientService;
    	}    
	@GetMapping("/ping")
    	public String ping() {
        	return grpcClientService.ping();
    	}
    //     @GetMapping("/add")
	// public String add() {
	// 	return grpcClientService.add();
	//}
	@PostMapping("/uploadandmultiply")
	public String Mul(@RequestParam("matfile") MultipartFile matfile,@RequestParam("matfiletwo") MultipartFile matfiletwo,@RequestParam int deadline) throws IOException {
		//convert into matrix
		List <String> l = new ArrayList<>();
		List <String> ltwo = new ArrayList<>();
		//InputStreamReader ir = new InputStreamReader(System.in);
		//For Matrix 1
		InputStream is = matfile.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		//For Matrix 2
		InputStream ij = matfiletwo.getInputStream();
		BufferedReader bj = new BufferedReader(new InputStreamReader(ij));

		l = br.lines().collect(Collectors.toList());
		ltwo = bj.lines().collect(Collectors.toList());
		int [][] mata = getMatrix(l);
		int [][] matb = getMatrix(ltwo);
		int [][] matc = grpcClientService.mult(mata,matb);
		return Arrays.deepToString(matc);
	}
	
	//created a matrix
	public int[][] getMatrix(List<String> l) {
		int row = l.size();
		int col = l.get(0).split( " ").length;
		int [][] mat = new int [row][col];
		int i,j;
		for(i=0;i<row;i++){
			String arr[]=l.get(i).split(" ");
			for(j=0;j<col;j++){
				mat [i][j] = Integer.parseInt(arr[j]);
			}
		}
		return mat;

	}

}
