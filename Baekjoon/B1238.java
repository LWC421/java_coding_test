import java.io.*;
import java.util.*;

public class B1238 {

	static final int LIMIT = 1000 * 100 + 1;
	static List<Edge>[] edges;
	static int[][] weights;		//[from][to]까지 가는 비용을 저장

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(in.readLine(), " ");

		int numVertex = Integer.parseInt(st.nextToken());	//마을의 개수
		int numEdge = Integer.parseInt(st.nextToken());		//도로의 개수
		int finish = Integer.parseInt(st.nextToken());		//도착점

		edges = new LinkedList[numVertex+1];	//0번은 없다
		weights = new int[numVertex+1][numVertex+1];
		
		for(int y = 1; y <= numVertex; y++) {
			edges[y] = new LinkedList<>();
			for(int x = 1; x <= numVertex; x++) {
				// 거리비용 최대비용으로 초기화
				weights[y][x] = LIMIT;
			}
		}

		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			//도로 정보 받기
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());

			edges[from].add(new Edge(to, weight));	//간선 넣기
		}
		//모든 입력 종료

		PriorityQueue<Data> pq = new PriorityQueue<>();
		Data curr = null;
		int nWeight;
		for(int start = 1; start <= numVertex; start++) {
			//start부터 시작해서 다익스트라 하기
			pq.add(new Data(start, 0));	//start에서 시작하는 거를 하나 잡고
			weights[start][start] = 0;	//자기자신으로 가는 비용은 0이다
			
			while( !pq.isEmpty() ) {
				curr = pq.poll();
				if(curr.vertex == finish) {
					//도착지에 도착 했으면
					pq.clear();
					break;
				}
				
				for(Edge connected: edges[curr.vertex]) {
					//현재와 연결된 간선에 대해
					nWeight = curr.weight + connected.weight;	//다음으로 가는 비용 계산
					if(nWeight < weights[start][connected.vertex]) {
						//이 간선타고 가는게 이득이면
						 weights[start][connected.vertex] = nWeight;
						 pq.add(new Data(connected.vertex, nWeight));
					}
				}
			}
		}
		
		//역방향도 한번 계산해주자
		pq.add(new Data(finish, 0));
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge connected: edges[curr.vertex]) {
				//현재와 연결된 간선에 대해
				nWeight = curr.weight + connected.weight;	//다음으로 가는 비용 계산
				if(nWeight < weights[finish][connected.vertex]) {
					//이 간선타고 가는게 이득이면
					 weights[finish][connected.vertex] = nWeight;
					 pq.add(new Data(connected.vertex, nWeight));
				}
			}
		}
		
		
		int result = 0;
		for(int i = 1; i <= numVertex; i++) {
			int targetWeight =  weights[i][finish] + weights[finish][i];
			result = Math.max(result, targetWeight);
		}
		
		System.out.println(result);
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

		@Override
		public String toString() {
			return String.format("%d : %dw", vertex, weight);
		}
	}

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
