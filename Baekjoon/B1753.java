import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B1753 {
	
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		int numVertex;	//V, 정점의 개수, 1부터 시작한다
		int numEdge;	//E, 간선의 개수
		int start;		//K, 시작 정점
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		//1번째 줄 입력
		numVertex = Integer.parseInt(st.nextToken());
		numEdge = Integer.parseInt(st.nextToken());
		
		//2번째 줄 입력
		start = Integer.parseInt(in.readLine());
		
		//간선 정보 입력
		Node[] edges = new Node[numVertex+1];	//1부터 시작이므로 +1해서
		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			edges[from] = new Node(to, weight, edges[from]);	//간선 넣기
		}
		//입력 종료
		
		//최단 경로 저장
		int[] distance = new int[numVertex+1];
		Arrays.fill(distance, INF);	//일단 무한으로 채우기
		distance[start] = 0;		//시작점은 거리가 0이다
		boolean[] visited = new boolean[numVertex+1];
		
		PriorityQueue<Data> pq = new PriorityQueue<>();
		pq.add(new Data(start, 0));	//시작점 넣기
		
		Data current;	//PQ에서 뽑을때 사용
		Node next;		//from -> to 로 가는 반복문 돌기위해 사용
		while( !pq.isEmpty() ) {
			current = pq.poll();
			from = current.vertex;
			weight = current.weight;
			
			if(visited[from]) continue;	//이미 봤을 경우 패스
			
			visited[from] = true;
			
			next = edges[from];	//간선 받아오기
			
			while(next != null) {
				if(distance[next.vertex] > weight + next.weight) {
					//거쳐 가는게 더 작은 값일 경우 
					distance[next.vertex] = weight + next.weight;
					pq.add(new Data(next.vertex, weight + next.weight));	//다음 경유지로 설정
				}
				next = next.next;
			}
		}
		
		for(int i = 1; i <= numVertex; i++) {
			if(distance[i] == INF) {
				sb.append("INF");
			}
			else {
				sb.append(distance[i]);
			}
			
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	static class Node{
		int vertex;
		int weight;
		Node next;
		
		//생성자
		public Node(int vertex, int weight, Node next) {
			this.vertex = vertex;
			this.weight = weight;
			this.next = next;
		}
	}
	
	//PQ에서 사용할 Data
	static class Data implements Comparable<Data>{
		int vertex;
		int weight;
		
		public Data(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
		
		public int compareTo(Data o) {
			return Integer.compare(this.weight, o.weight);
		}
	}
}
