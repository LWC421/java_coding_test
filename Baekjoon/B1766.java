import java.io.*;
import java.util.*;

public class B1766 {
	
	static int[] degrees;		//차수 계산
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{

//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		int numQ = Integer.parseInt(st.nextToken());
		int numEdge = Integer.parseInt(st.nextToken());
		
		List<Integer>[] edges = new LinkedList[numQ+1];
		
		for(int i = 1; i <= numQ; i++) {
			edges[i] = new LinkedList<>();
		}
		
		int[] inDegrees = new int[numQ +1 ];	//0번은 사용하지 않는다
		
		int from, to;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			
			inDegrees[to]++;	//진입 차수 증가
			
			edges[from].add(to);
		}
		
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
			return a - b;
		});
		boolean[] visited = new boolean[numQ+1];
		
		//아직 해결안한게 있으면
		int curr;
		for(int i = 1; i <= numQ; i++) {
			if(!visited[i] && inDegrees[i] == 0) {
				//진입차수가 0이면 이 문제부터 풀어야 한다
				visited[i] = true;	//이 문제부터 풀어야 한다
				pq.add(i);		//현재 지점에서부터 시작해서
				
			}
			
			while( !pq.isEmpty() && pq.peek() <= i) {
				curr = pq.poll();	//하나 꺼내서
				sb.append(curr).append(' ');
				
				for(int next: edges[curr]) {
					inDegrees[next] = inDegrees[next] - 1;
					if(inDegrees[next] == 0) {
						//진입 차수가 0이 되면
						visited[next] = true;	//방문 예정이다
						pq.add(next);
					}
				}
			}
		}
		
//		while( !pq.isEmpty() ) {
//			sb.append(pq.poll()).append(' ');
//		}
		
		System.out.println(sb.toString());
	}
}
