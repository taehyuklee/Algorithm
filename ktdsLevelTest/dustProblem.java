package JavaL2Second;

public class JavaL2Second {
	
	static char[][] input = {{'.', '$','.','.','$','.'},{'$', '.','.','.','.','$'},{'.', '$','.','.','#','#'},{'$', '.','.','$','$','#'},{'#', '#','#','#','#','#'}};
	
	public static void print2D(char[][] arr) {
		
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void main(String[] args) {
		
		print2D(input);
		
		int answer =0;
		
		//for문
		for(int i=0; i<1; i++) { //input.length
			for(int j=0; j<input[0].length; j++) {
				
				//행을 하나씩 돌아가면서 $인 경우 count를 한 번 한다 
				if(input[i][j] == '$') {
					answer+=1;

					//$가 #위에 있는거면 .으로 치환하고 없앤다
					if(input[i+1][j] =='#') {
						input[i][j] ='.';
					}else {
						//$먼지를 떨어뜨린다. $는 통과하고 #위에 올라가게 한다
						int row=0;
						int breakSize=0;
						while(true) {
							//우선 먼지를 치우고 그 아래 조건에 따라 달라진다
							
							if(input[i+row][j] == '&') {
								input[i+row][j] = '$';
								if(input[i+row+1][j] != '#') {
									input[i+row+1][j] = '$';
								}
							}
							else {
								input[i+row][j] = '.';
								
								//아래가 .일때
								if(input[i+row+1][j] == '.') {		
									input[i+row+1][j] = '$';
									
								//아래가 $일때
								}else if(input[i+row+1][j] == '$'){
									input[i+row+1][j] = '&';	
								}
								//아래가 #일때
								else if(input[i+row+1][j] == '#') {
									input[i+row][j] = '$';//벽이면 그대로 유지시키기 위해 $를 다시 놓아줌
									break;
								}
								
								if(breakSize ==2) {
									break;
								}
								row+=1;
								breakSize+=1;
	
							}
						}
					}

				}

			}
		
		}
		
		System.out.println();
		print2D(input);
		
	}
}
