import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class B1948 {
	
	static int count;				//오는 도로의 수 저장
	static boolean[] visited;		//카운트 하는거 방문 체크용
	static Prev[] prevs;
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int numVertex = Integer.parseInt(in.readLine());
		int numEdge = Integer.parseInt(in.readLine());
		
		int[] inDegrees = new int[numVertex+1];					//진입차수 계산
		List<Edge>[] edges = new LinkedList[numVertex+1];		//각 지점에서 연결된 간선들의 정보
		prevs = new Prev[numVertex+1];							//현재 지점에 올 수 있는 정점들 저장
		count = 0;
		visited = new boolean[numVertex+1];
		
		for(int i = 1; i <= numVertex; i++) {
			edges[i] = new LinkedList<>();
			prevs[i] = new Prev();
		}
		
		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			edges[from].add(new Edge(to, weight));	//간선 정보 저장
			inDegrees[to]++;	//진입 차수 증가
		}
		
		st = new StringTokenizer(in.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());		//시작 도시, 도착 도시 받아오기
		
		Stack<Integer> moveables = new Stack<>();
		moveables.add(start);		//start지점에서 시작하자
		
		int curr;
		while( inDegrees[end] != 0) {
			//모두 도착할 때 까지
			curr = moveables.pop();	//진출할 수 있는 정점을 하나 가져와서
			List<Edge> connected = edges[curr];
			
			for(Edge next: connected) {
				inDegrees[next.vertex]--;	//진입차수 줄이기
				if(inDegrees[next.vertex] == 0) {
					//진입차수가 0이 되어서 갈 수 있으면
					moveables.add(next.vertex);
				}
				
				//현재 정점에서 연결된 정점들을 가져와서
				int nWeight = prevs[curr].weight + next.weight;
				if(prevs[next.vertex].weight < nWeight) {
					//만약 현재 정점에서 다음 정점으로 가는 것이 더 weight가 크면
					prevs[next.vertex].prevs.clear();			//여기로 가는 정보들은 지워버리고
					prevs[next.vertex].prevs.add(curr);			//curr에서 왔다고 넣어주자
					prevs[next.vertex].weight = nWeight;		//가중치 갱신
				} 
				else if(prevs[next.vertex].weight == nWeight) {
					//weight가 동일하면 -> 그냥 넣어주자
					prevs[next.vertex].prevs.add(curr);
				}
			}
		}
		
		//거리 정보 출력
		sb.append(prevs[end].weight).append('\n');
		calcEdge(end);	//여기서부터 뒤로 가면서 사용한 도로의 개수를 측정하자
		sb.append(count).append('\n');
		
		System.out.println(sb.toString());
	}
	
	static void calcEdge(int curr) {
		//사용한 도로들 개수 세기
		visited[curr] = true;	//방문했다고 넣기
		count += prevs[curr].prevs.size();	//사용한 도로들 개수 넣기
		for(int prev : prevs[curr].prevs) {
			if(visited[prev]) continue;	//이미 방문한 곳이면 가지 않는다
			calcEdge(prev);
		}
	}
	
	static class Prev{
		List<Integer> prevs;
		int weight;
		
		public Prev() {
			this.prevs = new LinkedList<>();
			this.weight = 0;
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
