import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class B2623{
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		StringBuilder sb = new StringBuilder("");
		
		int numVertex = Integer.parseInt(st.nextToken());
		int numPD = Integer.parseInt(st.nextToken());
		
		Node[] edges = new Node[numVertex+1];	//0번은 없으므로 +1로
		int[] inDegree = new int[numVertex+1];	//진입차수 세기
		
		int from = -1;
		int to;
		for(int i = 0; i < numPD; i++) {
			//PD의 수만큼 받아오자
			st = new StringTokenizer(in.readLine());
			int numHuman = Integer.parseInt(st.nextToken());
			from = Integer.parseInt(st.nextToken());
			for(int j = 1; j < numHuman; j++) {
				to = Integer.parseInt(st.nextToken());
				
				edges[from] = new Node(to, edges[from]);
				inDegree[to]++;
				
				from = to;
			}
		}
		//입력 종료
		
		Queue<Integer> q = new ArrayDeque<>();
		
		for(int i = 1; i <= numVertex; i++) {
			if(inDegree[i] == 0) {
				//진입 차수가 0인거부터 넣기
				q.add(i);
			}
		}
		
		if(q.isEmpty()) {
			//진입차수 0인 시작점이 없는 경우
			System.out.println("0");
			return;
		}
		
		List<Integer> list = new LinkedList<>();
		int current;
		while( !q.isEmpty() ) {
			current = q.poll();
			list.add(current);
			
			for(Node n = edges[current]; n != null; n = n.next) {
				inDegree[n.vertex]--;	//진입차수 줄이기
				if(inDegree[n.vertex] == 0) {
					//진입차수 0인 정점에 대해
					q.add(n.vertex);
				}
			}
		}
		
//		for(int i = 1; i <= numVertex; i++) {
//			if(inDegree[i] != 0){
//				//진입차수 0인 아닌 경우가 있다 -> 사이클이였던 경우다
//				System.out.println("0");
//				return;
//			}
//		}
		
		for(int n: list) {
			sb.append(n).append("\n");
		}
		System.out.println(sb.toString());
	}
	
	static class Node{
		int vertex;
		Node next;
		
		public Node(int vertex, Node next) {
			this.vertex = vertex;
			this.next = next;
		}
	}
}