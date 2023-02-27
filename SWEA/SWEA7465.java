import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;


public class SWEA7465 {

	static int[] parents;
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			int numPerson = Integer.parseInt(st.nextToken());	//사람수
			int numEdge = Integer.parseInt(st.nextToken());		//관계 개수
			
			//집합 초기화
			parents = new int[numPerson+1];
			for(int i = 1; i <= numPerson; i++) {
				parents[i] = i;
			}
			
			//관계만큼 돌면서
			for(int i = 0; i < numEdge; i++) {
				st = new StringTokenizer(in.readLine());
				//합치기
				union(Integer.parseInt(st.nextToken()), 
						Integer.parseInt(st.nextToken()));
			}
			
			for(int i = 1; i <= numPerson; i++) {
				union(i, parents[i]);	//덜 합쳐진거 합치기
			}
			
			//set이용해서 중복 제외하고 개수 세기
			Set<Integer> set = new HashSet<>();
			for(int i = 1; i <= numPerson; i++) {
				set.add(parents[i]);
			}
			
			
			sb.append(String.format("#%d %d\n", test_case, set.size()));
		}
		
		System.out.println(sb.toString());
	}
	
	public static int find(int x) {
		if(x == parents[x]) return x;
		return parents[x] = find(parents[x]);
	}
	
	public static void union(int x, int y) {
		x = find(x);
		y = find(y);
		
		parents[y] = x;
	}
}
