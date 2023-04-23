import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;


public class B5719 {
	
	static final int INF = 500*(int)1e3 + 1;
	
	static int numVertex;
	static int numEdge;
	
	static int start;		//시작 정점 번호
	static int destination;	//도착지 정점 번호
	
	static List<Edge>[] edges;
	static Set<Integer>[] prevs;	//각 정점을 오는데 이전 노드들에 대해 기록
	static Set<Integer>[] used;
	
	static boolean[] visited;


	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st =  null;
		StringBuilder sb = new StringBuilder("");
		
		while(true) {
			st = new StringTokenizer(in.readLine());
			numVertex = Integer.parseInt(st.nextToken());
			numEdge = Integer.parseInt(st.nextToken());
			prevs = new Set[numVertex];	//각 정점을 오는데 이전 노드들에 대해 기록
			used = new Set[numVertex];	//실직적으로 해당 정점에서 다른 정점으로 가는 정보에 대해 사용한 기록을 넣기
			visited = new boolean[numVertex];
			
			if(numVertex == 0 && numEdge == 0) {
				break;
			}
			
			edges = new LinkedList[numVertex];
			
			for(int i = 0; i < numVertex; i++) {
				edges[i] = new LinkedList<>();
				prevs[i] = new HashSet<>();
				used[i] = new HashSet<>();
			}
			
			st = new StringTokenizer(in.readLine(), " ");
			start = Integer.parseInt(st.nextToken());
			destination = Integer.parseInt(st.nextToken());
			
			int id = 0;
			int from, to, weight;
			for(int i = 0; i < numEdge; i++) {
				st = new StringTokenizer(in.readLine(), " ");
				from = Integer.parseInt(st.nextToken());
				to = Integer.parseInt(st.nextToken());
				weight = Integer.parseInt(st.nextToken());
				
				edges[from].add(new Edge(id++, to, weight));
			}
			
			int distance = dijk();
			if(distance == INF) {
				//거리를 못 찾은 경우다
                sb.append(-1).append('\n');
				continue;
			}
			
			distance = dijk2();
			if(distance == INF) {
				//거리를 못 찾은 경우다
				sb.append(-1).append('\n');
			} else {
				sb.append(distance).append('\n');
			}
		}
		
		System.out.println(sb.toString());
	}
	
	@SuppressWarnings("unchecked")
	static int dijk() {
		int[] distances = new int[numVertex];
		
		for(int i = 0; i < numVertex; i++) {
			distances[i] = INF;
		}
		
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		distances[start] = 0;	//처음 지점은 0으로 표시
		pq.add(new Edge(-1, start, 0));
		
		Edge curr = null;
		int nWeight;
		int prevWeight;
		
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge edge: edges[curr.vertex]) {
				nWeight = distances[curr.vertex] + edge.weight;
				prevWeight = distances[edge.vertex];
				if(nWeight < prevWeight) {
					//갱신이 가능하면
					prevs[edge.vertex].clear();		//최소값이 온거이므로 그전것들은 삭제
					prevs[edge.vertex].add(curr.vertex);
					
					distances[edge.vertex] = nWeight;
					pq.add(edge);
				}
				else if(nWeight == prevWeight) {
					//동등하게 올 수 잇으면
					prevs[edge.vertex].add(curr.vertex);
				}
			}
		}
		

		
		return distances[destination];
	}
	
	@SuppressWarnings("unchecked")
	static int dijk2() {
		int[] distances = new int[numVertex];
		
		processUsed(destination);		//도착점을 기준으로 올라가면서 사용한 간선들 넣기
		
		for(int i = 0; i < numVertex; i++) {
			distances[i] = INF;
		}
		
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		distances[start] = 0;	//처음 지점은 0으로 표시
		pq.add(new Edge(-1, start, 0));
		
		Set<Integer> currUsed = null;
		Edge curr = null;
		int nWeight;
		int prevWeight;
		
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			currUsed = used[curr.vertex];	//현재 정점을 기준으로 사용한 간선들 정보 가져오기
			for(Edge edge: edges[curr.vertex]) {
				if(currUsed.contains(edge.vertex)) continue;	//사용한 간선이면 사용하지 않는다
				nWeight = distances[curr.vertex] + edge.weight;
				prevWeight = distances[edge.vertex];
				if(nWeight < prevWeight) {
					//갱신이 가능하면
					distances[edge.vertex] = nWeight;
					pq.add(edge);
				}
			}
		}
		
		return distances[destination];
	}
	
	public static void processUsed(int curr) {
		if(curr == start) return;	//출발지까지 왔으면 그만
		
		visited[curr] = true;
		for(int prev: prevs[curr]) {
			used[prev].add(curr);	//prev -> curr로 가는 간선은 사용했다는 의미이다
			if(visited[prev]) continue;	//이미 체크한 곳이면 가지않는다
			processUsed(prev);		//prev도 이전 간선들에 대해 작업해주자
		}
	}
	
	static class Edge implements Comparable<Edge>{
		int id;
		int vertex;
		int weight;
		
		public Edge(int id, int vertex, int weight) {
			this.id = id;
			this.vertex = vertex;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Edge other) {
			return this.weight - other.weight;
		}
		
		@Override
		public String toString() {
			return String.format("%d : %dw", vertex, weight);
		}
	}
}
