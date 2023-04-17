import java.util.*;
import java.io.*;


public class Main2 {
	
	public static ArrayList<int[]> outArr = new ArrayList<>();
	
	public static void print(int [] arr, int r) {
		for(int i=0; i<r; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		int[] arr = new int[] {1,2,3,4,5};
		int n = arr.length;
		int r = 4;
		int depth = 0;
		boolean[] visit = new boolean[n];
		int[] output = new int[n];
		
		perm(arr,visit,output,n,r, 0);
	}
	
	public static void perm(int[] arr, boolean[] visit, int[] output, int n, int r, int depth) {
		
		if(depth == r) {
			outArr.add(output);
			print(output, r);
			return;
		}
		
		for(int i=0; i<arr.length; i++) {
			
			if(visit[i] != true) {
				
				visit[i] = true;
				output[depth] = arr[i];
				perm(arr,visit,output,n,r,depth+1);
				output[depth] = 0;
				visit[i] = false;			
				
			}
			
		}
		
	}
}
