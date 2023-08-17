package Sort;
import java.util.*;

import Coord;

import java.io.*;

class Coord{
	int x;
	int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return this.x + " " + this.y;
	}
}

public class Main {

	static int N;
	static Coord[] coord_array;
	
	static void print1D(Coord[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.println(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		coord_array = new Coord[N];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			coord_array[i] = new Coord(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		Arrays.sort(coord_array, new Comparator<Coord>(){
			@Override
			public int compare(Coord o1, Coord o2) {
				int value = o1.x - o2.x;
				if(value ==0 ) {
					return o1.y - o2.y;
				}				
				return value;
			}
		});
		
		print1D(coord_array);
		
	}
}
