import java.util.*;

public class Main {
	
	static int N, M, cnt=0;
	static Map<String, String> map = new HashMap<>();
	static List<String> answer_list = new ArrayList<>(); //arrayList가 200ms정도 더 빠르다 여기 LinkedList보다 (조회가 더 빠르기때문에)
	
	
	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder();
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		
		sc.nextLine();
		
		for(int i=0; i<N; i++) {
			String noListen = sc.nextLine();
			map.put(noListen, noListen);
		}
		
		for(int i=0; i<M; i++) {
			String noLook = sc.nextLine();
			if(map.get(noLook)==null) {
				continue;
			}else {
				answer_list.add(noLook);
				cnt++;
			}
		}
		
		if(cnt==0) {
			System.out.println(0);
		}else {
			System.out.println(cnt);
			Collections.sort(answer_list);
			for(int i=0; i<answer_list.size(); i++) {
				sb.append(answer_list.get(i)).append("\n");
			}
			sb.deleteCharAt(sb.length()-1); //마지막 개행문자 없애주기.
			System.out.println(sb);
		}
		
		
	}
}
