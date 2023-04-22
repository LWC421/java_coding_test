import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class B1761 {
	
	static final int MAX_POW_DEPTH = 16;	// 2^16을 해야 40_000보다 크다
	
	static List<Edge>edges[];				//간선 정보 저장용
	static int[] depths;					//깊이 저장용
	static int[][] parents;					//부모 노드 저장용
	static int[][] distances;				//부모노드까지 갈때의 비용 정보 저장
	static boolean[] visited;				//DFS 방문 체크용
	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int numVertex = Integer.parseInt(in.readLine());
		
		edges = new LinkedList[numVertex+1];
		for(int i = 1; i <= numVertex; i++) {
			edges[i] = new LinkedList<>();
		}
		
		depths = new int[numVertex+1];
		parents = new int[numVertex+1][MAX_POW_DEPTH+1];
		distances = new int[numVertex+1][MAX_POW_DEPTH+1];
		visited = new boolean[numVertex+1];
		
		
		
		int v1, v2;
		int weight;	//입력 받기용
		for(int i = 1; i < numVertex; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			v1 = Integer.parseInt(st.nextToken());
			v2 = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			edges[v1].add(new Edge(v2, weight));
			edges[v2].add(new Edge(v1, weight));
		}
		//트리정보 입력 정보
		
		depths[1] = 1;			//1번 정점의 depth는 1이다
		parents[1][0] = 1;		//1번 정점의 1depth부모는 1번이다
		distances[1][0] = 0;	//1번 정점에서 1번정점으로 가는 비용은 0이다
		visited[1] = true;		//1번 정점은 방문했다고 미리 표시
		dfs(1, 1);		//1번 정점부터해서 DFS로 depth 및 1depth부모와의 거리들을 저장해주자

		//Log(N)으로 조상과의 거리 및 조상들을 초기화하자
		for(int d = 1; d <= MAX_POW_DEPTH; d++) {
			for(int v = 1; v <= numVertex; v++) {
				int parent = parents[v][d-1];
				parents[v][d] = parents[parent][d-1];
				distances[v][d] = distances[v][d-1] + distances[parent][d-1];
			}
		}

//		for(int[] row: distances) {
//			System.out.println(Arrays.toString(row));
//		}
		
//		System.out.println(Arrays.toString(depths));
		
		int numQuery = Integer.parseInt(in.readLine());
		for(int i = 0; i < numQuery; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			v1 = Integer.parseInt(st.nextToken());
			v2 = Integer.parseInt(st.nextToken());
			sb.append(LCA(v1, v2)).append('\n');
		}
		
		
		System.out.println(sb.toString());
	}
	
	public static void dfs(int curr, int depth) {
		for(Edge child: edges[curr]) {
			if(visited[child.to]) continue;	//이미 방문된 정점이면 skip
			visited[child.to] = true;			//방문 예정으로 표시
			depths[child.to] = depth+1;
			parents[child.to][0] = curr;
			distances[child.to][0] = child.weight; 
			dfs(child.to, depth+1);
		}
	}
	
	
	public static int LCA(int v1, int v2) {
		
		//더 낮은 depth가 v1이 되도록 조정한다
		if(depths[v1] > depths[v2]) {
			int tmp = v1;
			v1 = v2;
			v2 = tmp;
		}
		
		int result = 0;	//거리 저장
		
		//같은 높이로 맞추는 과정 진행
		int t = MAX_POW_DEPTH;
		int currDiff;
		while( (currDiff = depths[v2] - depths[v1]) != 0) {
			int tPow = (1 << t);
			if( (currDiff & tPow) != 0) {
				result += distances[v2][t];
				v2 = parents[v2][t];
			}
			t--;
		}
		
		//같은 높이로 조정하고 난 후 같으면 바로 return하자
		if(v1 == v2) {
			return result;
		}
		
		for(int d = MAX_POW_DEPTH; d >= 0; d--) {
			if(parents[v1][d] != parents[v2][d]) {
				result += distances[v1][d];
				result += distances[v2][d];
				v1 = parents[v1][d];
				v2 = parents[v2][d];
			}
		}
		
		result += distances[v1][0];
		result += distances[v2][0];
		
		return result;
	}

	static class Edge{
		int to;
		int weight;
		
		public Edge(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}
}
