import java.io.*;
import java.util.*;

public class B15971 {

	//N개의 방이 있다
	// edge는 N-1개이다
	// 사이클이 없다
	// 모든 정점은 연결되어 있다
	
	// 같은 정점 또는 하나의 간선의 양끝에 있어야만 통신이 가능하다
	
	static List<Edge>[] edges;		//간선 정보 저장
	static int[] prevs;			//현재 정점에 오는데 이전에 방문한 정점의 정보 저장
	static int[] distances;		//거리 정보 저장
	static Map<FromTo, Integer> fromTo;
	
	static final int LIMIT = 100_000 * 1_000 + 1;
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
		//정점은 100_000개
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int numVertex = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		edges = new List[numVertex+1];		//0번은 사용하지 않는다
		prevs = new int[numVertex+1];		//0번은 사용하지 않는다
		distances = new int[numVertex+1];	//0번은 사용하지 않는다
		fromTo = new HashMap<>();
		
		//모든 장소 거리 정보 초기화
		for(int i = 1; i <= numVertex; i++) {
			distances[i] = LIMIT;
		}
		
		
		for(int i = 1; i <= numVertex; i++) {
			edges[i] = new LinkedList<>();
		}
		
		int from, to, weight;
		for(int i = 1; i < numVertex; i++) {
			st = new StringTokenizer(in.readLine());
			
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			edges[from].add(new Edge(to, weight));
			edges[to].add(new Edge(from, weight));
			fromTo.put(new FromTo(from, to), weight);
			fromTo.put(new FromTo(to, from), weight);
		}
		//입력 종료
		
		PriorityQueue<Data> pq = new PriorityQueue<>();
		pq.add(new Data(start, 0));	//start정점에서 시작하자
		distances[start] = 0;	//첫 위치 거리 0이다
		
		
		//다익스트라 돌리자
		Data curr = null;
		int nWeight;
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge edge: edges[curr.vertex]) {
				//현재와 연결된 간선에 대해
				nWeight = curr.weight + edge.weight;
				if(distances[edge.vertex] > nWeight) {
					//가는게 이득이면
					prevs[edge.vertex] = curr.vertex;	//다음 위치의 이전은 지금이라고 표시해놓고
					distances[edge.vertex] = nWeight;	//거리정보 넣기
					pq.add(new Data(edge.vertex, nWeight));
					
				}
			}
		}
		
		int currVertex = end;
		int prev = end;
		
		int maxWeight = 0;
		while(prev != start) {
			prev = prevs[currVertex];
			
			int currWeight = fromTo.get(new FromTo(prev, currVertex));
			
			if(currWeight > maxWeight) {
				maxWeight = currWeight;
			}
			
			currVertex = prev;
		}
		
		System.out.println(distances[end] - maxWeight);
	}
	
	static class FromTo{
		int from;
		int to;
		
		public FromTo(int from, int to) {
			this.from = from;
			this.to = to;
		}
	
		@Override
		public int hashCode() {
			return from*100_001 + to;
		}
		
		@Override
		public boolean equals(Object o) {
			FromTo other = (FromTo)o;
			return this.from == other.from && this.to == other.to;
		}
	}
	
	static class Data implements Comparable<Data>{
		int vertex;
		int weight;
		
		public Data(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Data other) {
			return this.weight - other.weight;
		}
	}
	
	static class Edge{
		int vertex;
		int weight;
		
		public Edge(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
	}
	
	
}
