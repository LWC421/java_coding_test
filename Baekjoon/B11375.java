import java.io.*;
import java.util.*;

public class B11375 {

	static int numLeft;
	static int numRight;
	
	static int[] leftToRight;
	static int[] rightToLeft;
	static boolean[] visited;
	
	static List<Integer>[] edges;
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		numLeft = Integer.parseInt(st.nextToken());		//직원 수
		numRight = Integer.parseInt(st.nextToken());	//해야할 일
		
		leftToRight = new int[numLeft+1];
		rightToLeft = new int[numRight+1];
		visited = new boolean[numLeft+1];
	
		edges = new LinkedList[numLeft+1];
		
		for(int i = 1; i<= numLeft; i++) {
			edges[i] = new LinkedList<>();
			
			st = new StringTokenizer(in.readLine());
			
			int numRight = Integer.parseInt(st.nextToken());
			
			for(int j = 0; j < numRight; j++) {
				edges[i].add(Integer.parseInt(st.nextToken()));
			}
		}
		//입력 종료
		
		int result = 0;
		
		for(int i = 1; i <= numLeft; i++) {
			for(int j = 1; j <= numLeft; j++) {
				visited[j] = false;	//방문기록 없애기
			}
			
			if(dfs(i)) {
				result++;
			}
		}
		
		System.out.println(result);
	}


	public static boolean dfs(int l) {
		visited[l] = true;

		List<Integer> edge = edges[l];
		
		for(int r: edge) {
			if(rightToLeft[r] == 0 || !visited[rightToLeft[r]] && dfs(rightToLeft[r])) {
				leftToRight[l] = r;
				rightToLeft[r] = l;
				return true;
			}
		}
		
		return false;
	}
}
