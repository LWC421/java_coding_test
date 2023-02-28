import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SWEA3124_v2 {
	
	static int[] parents;	//union-find에 필요

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		int numVertex;			//정점의 개수
		int numEdge;			//간선의 개수
		int[] weights;			//현재 볼 수 있는 정점들을 연결하는  비용
		boolean[] connected;	//연결이 되어있는지 확인용
		int connectCount;		//몇개나 연결되었는지 확인
		ArrayList<Edge> edges[];//간선 정보 저장
		
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			numVertex = Integer.parseInt(st.nextToken());
			numEdge = Integer.parseInt(st.nextToken());
			
			edges = new ArrayList[numVertex+1];	//간선 정보 저장, 1번부터 시작이므로 +1
			for(int i = 1; i <= numVertex; i++) {
				edges[i] = new ArrayList<>();
			}
			//각 정점에 대한 가중치 초기화
			weights = new int[numVertex+1];
			//연결 정보 초기화
			connected = new boolean[numVertex + 1];
			for(int i = 1; i <= numVertex; i++) {
				weights[i] = 1_000_000 + 1;	//최대값 +1로 초기화
				connected[i] = false;	//연결 안되어 있다고 하기
			}
			connectCount = 0;	//처음에는 연결된거 없다고 하기
			
			//간선 정보 입력
			int from, to, weight;
			for(int i = 0; i < numEdge; i++) {
				//간선 정보 받기
				st = new StringTokenizer(in.readLine());
				from = Integer.parseInt(st.nextToken());
				to = Integer.parseInt(st.nextToken());
				weight = Integer.parseInt(st.nextToken());
				
				//간선 정보 저장
				edges[from].add(new Edge(to, weight));
				edges[to].add(new Edge(from, weight));
			}
			//입력 종료
			
//			//가중치 기준으로 정렬
//			for(int i = 1; i <= numVertex; i++) {
//				Collections.sort(edges[i]);
//			}
			
			//1번에서 시작하기
			connected[1] = true;
			connectCount++;	//한개 연결되었다고 하기
			
			PriorityQueue<Edge> pq = new PriorityQueue<>();
			for(Edge e: edges[1]) {
				//1번에 연결된 것들을 보면서
				pq.add(new Edge(e.vertex, e.weight));	//우선순위 큐에 넣기
			}
			
			long weightSum = 0;
			Edge current;	//현재 보고 있는 거
			while(connectCount < numVertex) {
				//아직 연결이 덜 되었으면
				current = pq.poll();
				to = current.vertex;
				weight = current.weight;
				
				if(connected[to]) {
					//이미 연결이 되어 있을 경우
					continue;
				}
				
				//연결이 안 되어 있을 경우
				connected[to] = true;
				weightSum += weight;	//가중치 더하기
				connectCount++;			//연결 개수도 증가시키기
				for(Edge e: edges[to]) {
					pq.add(new Edge(e.vertex, e.weight));	//여기도 볼 수 있다고 하기
				}
			}
			
			sb.append(String.format("#%d %d\n", test_case, weightSum));
		}
		
		System.out.println(sb.toString());
	}
	
	static class Edge implements Comparable<Edge>{
		int vertex;	//어디랑 연결 되어있는지
		int weight;	//가중치
		
		public Edge(int vertex, int weight) {
			this.vertex = vertex;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.weight, o.weight);
		}
	}
}
