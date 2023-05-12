import java.io.*;
import java.util.*;

public class B1916 {
	
	static final int LIMIT = 1000*100_000 + 1;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int numCity = Integer.parseInt(in.readLine());
		int numEdge = Integer.parseInt(in.readLine());
		
		List<Edge>[] edges = new LinkedList[numCity+1];	//간선 정보 저장, 0번 사용X
		int[] weights = new int[numCity+1];	//start에서 가는데 필요한 비용들 저장
		for(int i = 1; i <= numCity; i++) {
			edges[i] = new LinkedList<>();
			weights[i] = LIMIT;	//처음 거리정보는 LIMIT로 하기
		}
		
		
		
		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			edges[from].add(new Edge(to, weight));	//간선 넣어주기
		}
		//간선 정보 받기 종료
		
		//출발 도착지 받기
		st = new StringTokenizer(in.readLine(), " ");
		int start = Integer.parseInt(st.nextToken());
		int finish = Integer.parseInt(st.nextToken());
		//모든 입력 종료
		
		weights[start] = 0;	//일단 자기자신은 0이다
		
		PriorityQueue<Data> pq = new PriorityQueue<>(); 
		pq.add(new Data(start, 0));	//start에서 시작하며 현재까지 비용은 0이다
		
		Data curr = null;
		int nWeight;
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			if(curr.vertex == finish) {
				//도착했으면
				System.out.println(curr.weight);
				return;
			}
			
			if(curr.weight > weights[curr.vertex]) {
				//만약 더 높은게 나왔으면 -> 무시하자
				continue;
			}
			
			for(Edge connected: edges[curr.vertex]) {
				//현재 정점에 존재하는 간선들에 대해
				nWeight = curr.weight + connected.weight;	//현재 위치에서 가는 비용
				if(nWeight < weights[connected.vertex]) {
					//그전에 간 비용보다 낮은 비용이면
					weights[connected.vertex] = nWeight;	//비용 바꿔주고
					pq.add(new Data(connected.vertex, nWeight));
				}
			}
		}
		
	}
	
	//현재 정점과 현재 정점까지 오는데 필요한 비용
	static class Data implements Comparable<Data>{
		int vertex;
		int weight;
		
		public Data(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
		
		@Override
		public String toString() {
			return String.format("%d : %dw", vertex, weight);
		}
		
		@Override
		public int compareTo(Data other) {
			return this.weight - other.weight;
		}
	}
	
	//간선 정보
	static class Edge{
		int vertex;
		int weight;
		
		public Edge(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
		
		@Override
		public String toString() {
			return String.format("%d : %dw", vertex, weight);
		}
	}
}