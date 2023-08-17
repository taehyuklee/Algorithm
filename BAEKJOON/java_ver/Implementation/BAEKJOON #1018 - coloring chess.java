package Implementation;
import java.util.*;
import java.io.*;


public class Main {

	static int M, N;
	static String[][] board;
	static List<Integer> answerList = new ArrayList<>();
	
	
	static void print2D(String[][] arr) {
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	static void print1D(String[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		
		sc.nextLine(); //nextLine의 경우 개행문자 "\n"을 만날때까지를 읽어들인다. sc.nextInt()를 하고 나면 마지막에 bufferdp \n이 남아 있어서 한 번 띄워줘야 한다.
		
		board = new String[N][M];
		
		//board 만들
		for(int i=0; i<N; i++) {
			
			String input_line = sc.nextLine();
			String[] string_line = input_line.split("");

			for(int j=0; j<M; j++) {
				//System.out.println(string_line[j]);
				board[i][j] = string_line[j];
			}
			
		}
		
		translateBoard();
		
		//정렬 
		Collections.sort(answerList, Comparator.naturalOrder());
		
		//answer
		System.out.println(answerList.get(0));
		
	}
	
	static void translateBoard() {

		//Sliding으로 오른쪽으로 움직이고 다시 아래로 내려가서 움직인다.
		for(int i=0; i<=N-8; i++) {
			for(int j=0; j<=M-8; j++) {

				//맨 좌측 상단에 있는걸 W로 시작 또는 B로 둘 다 따져봐야 
				for(int turn=0; turn<2; turn++) {
					String[][] newBoard = deepCopyBoard(i, j);
					if(newBoard[0][0].equals("W")) {
						getAnswerList(i,j,"B");

					}else {
						getAnswerList(i,j,"W");
					}
				}

			}
		}

	}
	
	static void getAnswerList(int i, int j, String color) {
		int count = 0;
		String[][] newBoard = deepCopyBoard(i, j);
		answerList.add(checkBoardColor(newBoard, count)); 
		
		int count2 = 1;
		String[][] newBoard2 = deepCopyBoard(i, j);
		newBoard2[0][0] = color;
		answerList.add(checkBoardColor(newBoard2, count2)); 
	}
	
	
	static int checkBoardColor(String[][] board, int count) {
		
		for(int i=0 ;i<8; i++) {
			for(int j=0; j<7; j++) {
				
				if(board[i][j].equals("W")) {
					
					if(board[i][j+1].equals("B")) {
						continue;
					}else {
						count ++;
						board[i][j+1] = "B"; //update해줘야 한다. 그래야 그 뒷판에서 바꿀수 있음.
					}
					
				}else {
					if(board[i][j+1].equals("W")) {
						continue;
					}else {
						count ++;
						board[i][j+1] = "W"; //update해줘야 한다. 그래야 그 뒷판에서 바꿀수 있음.
					}
				}
			}
			
			//다음 행으로 넘어갈때 보고update한다.
			if(i+1<=7 && !board[i+1][0].equals(board[i][7])) {
				count ++;
				if(board[i][7].equals("W")) {
					board[i+1][0] = "W";
				}else {
					board[i+1][0] = "B"; 
				}
			}
		}

		return count;
	}
	
	static String[][] deepCopyBoard(int i, int j) {
		
		String[][] newString = new String[8][8];
		
		for(int k=i; k<i+8; k++) {
			for(int o=j; o<j+8; o++) {
				newString[k-i][o-j] = board[k][o];
			}
		}

		return newString;	
	}
}
