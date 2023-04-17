
public class Main {
	
	public static void print(int[][] arr) {
		
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();		
	}

	public static void main(String[] args) {
		
		int[] arr = new int[] {1,2,3,4,5,6};
	    int n = arr.length;
	    int[][] dp = new int[n][n/2 + 1]; // 배열의 크기는 n x (n/2 + 1)

	    // 초기값 설정
	    dp[0][0] = 0;
	    dp[0][1] = arr[0];
	    dp[1][1] = (arr[0]>arr[1]) ? arr[0] : arr[1];
	    
	    print(dp);

	    // 점화식 계산
	    for (int i = 2; i < n; i++) {
	        for (int j = 0; j <= n/2; j++) {
	            int notSelected = dp[i-1][j];
	            int selected = (j == 0) ? 0 : dp[i-2][j-1] + arr[i];
	            dp[i][j] = Math.max(notSelected, selected);
	        }
	    }

	    // 마지막 요소에서 선택된 요소 개수가 n/2인 경우의 최대값 반환
	    System.out.println(dp[n-1][n/2]);
	}
}
