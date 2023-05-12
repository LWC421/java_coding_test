import java.io.*;
import java.util.*;

public class B1504 {
	
	static final int LIMIT = 800*1_000 + 1;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(in.readLine(), " ");
		
		int numVertex = Integer.parseInt(st.nextToken());	//정점 개수
		int numEdge = Integer.parseInt(st.nextToken());		//간선 개수
		
		List<Edge>[] edges = new LinkedList[numVertex+1];	//0번 사용 안함
		int[] startTo = new int[numVertex+1];	//start에서 가는 비용들 저장
		int[] mid1To = new int[numVertex+1];	//경유지1에서 가는 비용들 저장
		int[] mid2To = new int[numVertex+1];	//경유지2에서 가는 비용들 저장	
		
		//각각의 정보들 초기화
		for(int i = 1; i <= numVertex; i++) {
			edges[i] = new LinkedList<>();
			startTo[i] = LIMIT;
			mid1To[i] = LIMIT;
			mid2To[i] = LIMIT;
		}
		
		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			//간선 정보들 받기
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			edges[from].add(new Edge(to, weight));
			edges[to].add(new Edge(from, weight));
		}
		
		st = new StringTokenizer(in.readLine(), " ");
		int mid1 = Integer.parseInt(st.nextToken());
		int mid2 = Integer.parseInt(st.nextToken());
		//모든 입력 종료
		
		PriorityQueue<Data> pq = new PriorityQueue<>();
		pq.add(new Data(1, 0));	//1번에서 시작하는데 아직까지는 0이다
		startTo[1] = 0;	//비용 초기화
		
		Data curr = null;
		int nWeight;
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge connected: edges[curr.vertex]) {
				nWeight = curr.weight + connected.weight;	//다음으로 가는 비용 계산
				if(nWeight < startTo[connected.vertex]) {
					//가는 비용이 더 적으면
					startTo[connected.vertex] = nWeight;	//비용 업데이트
					pq.add(new Data(connected.vertex, nWeight));//pq에 넣기
				}
			}
		}

		pq.add(new Data(mid1, 0));	//경유지1에서 가는 비용 구하기
		mid1To[mid1] = 0;	//비용초기화
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge connected: edges[curr.vertex]) {
				nWeight = curr.weight + connected.weight; 	//다음으로 가는 비용 계산
				if(nWeight < mid1To[connected.vertex]) {
					//가는 비용이 더 적으면
					mid1To[connected.vertex] = nWeight;	//비용 업데이트
					pq.add(new Data(connected.vertex, nWeight));
				}
			}
		}
		
		pq.add(new Data(mid2, 0));	//경유지1에서 가는 비용 구하기
		mid2To[mid2] = 0;	//비용초기화
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			
			for(Edge connected: edges[curr.vertex]) {
				nWeight = curr.weight + connected.weight; 	//다음으로 가는 비용 계산
				if(nWeight < mid2To[connected.vertex]) {
					//가는 비용이 더 적으면
					mid2To[connected.vertex] = nWeight;	//비용 업데이트
					pq.add(new Data(connected.vertex, nWeight));
				}
			}
		}
		//모든 비용 구하기 끝
		
		int path1 = 0;	//mid1을 먼저 경유하고 가는 길
		
		if(startTo[mid1] == LIMIT || mid1To[mid2] == LIMIT || mid2To[numVertex] == LIMIT) {
			//만약 연결 안된게 있으면
			path1 = LIMIT;
		}
		else {
			path1 += startTo[mid1];
			path1 += mid1To[mid2];
			path1 += mid2To[numVertex];
		}
		
		
		int path2 = 0;	//mid2를 먼저 경유하고 가는 길
		if(startTo[mid2] == LIMIT || mid2To[mid1] == LIMIT || mid1To[numVertex] == LIMIT) {
			path2 = LIMIT;
		}
		else {
			path2 += startTo[mid2];
			path2 += mid2To[mid1];
			path2 += mid1To[numVertex];
		}
		
		int min = Math.min(path1, path2);
		
		if(min == LIMIT) {
			//만약 가는 길이 없으면
			System.out.println(-1);
			return;
		}
		System.out.println(min);
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