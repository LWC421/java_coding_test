import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class B2252 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st 	= new StringTokenizer(in.readLine());
		
		int numVertex = Integer.parseInt(st.nextToken());
		int numEdge = Integer.parseInt(st.nextToken());
		
		Node[] edges = new Node[numVertex+1];
		int[] inDegree = new int[numVertex+1];
		
		int from, to;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			inDegree[to]++;	//진입 차수 늘리기
			
			edges[from] = new Node(to, edges[from]);
		}
		//입력 종료
		
		Queue<Integer> q = new ArrayDeque<>();
		
		for(int i = 1; i <= numVertex; i++) {
			if(inDegree[i] == 0) {
				//진입 차수 0인 노드 넣기
				q.add(i);
			}
		}
		
		List<Integer> list = new ArrayList<>();

		int current;
		while( !q.isEmpty() ) {
			current = q.poll();
			list.add(current);
			for(Node n = edges[current]; n != null; n = n.next) {
				//연결된 간선에 대해
				inDegree[n.vertex]--;	//진입차수 낮추고
				if(inDegree[n.vertex] == 0) {
					//진입 차수가 없는 애들에 대해
					q.add(n.vertex);
				}
			}
		}
		
		for(int num: list) {
			sb.append(String.format("%d ", num));
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
