import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class B11438 {
	
	final static int MAX_POW_DEPTH = 18;
	
	static int numVertex;			//노드의 개수
	static List<Integer>[] edges;	//간선정보 저장
	
	static boolean[] visited;		//첫 dfs에서 방문체크용
	
	static int[] depths;			//깊이정보 저장
	static int[][] parents;			//부모 정보들 저장

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		numVertex= Integer.parseInt(in.readLine());
		
		edges = new List[numVertex+1];
		visited = new boolean[numVertex+1];
		
		depths = new int[numVertex+1];
		parents = new int[numVertex+1][MAX_POW_DEPTH];
		
		
		for(int i = 1; i <= numVertex; i++) {
			edges[i] = new LinkedList<>();
		}
		
		
		int from, to;
		for(int i = 1; i < numVertex; i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			edges[from].add(to);
			edges[to].add(from);	//양방향으로 연결해야한다
		}
		
		visited[1] = true;		//1번 노드는 방문했다고 표시하자
		dfs(1, 1, 1);			//1번 정점부터 시작, 처음 depth를 1로 설정
		
		//부모요소 저장하기
		for(int d = 1; d < MAX_POW_DEPTH; d++) {
			for(int n = 2; n <= numVertex; n++) {
				int parent = parents[n][d-1];
				parents[n][d] = parents[parent][d-1];
			}
		}

			
		
		int numQuery = Integer.parseInt(in.readLine());
		for(int i = 0; i < numQuery; i++) {
			//쿼리의 개수만큼
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			
			sb.append(LCA(from, to)).append('\n');
		}
		
		System.out.println(sb.toString());
	}
	
	public static int LCA(int a, int b) {
		int depthA = depths[a];	//depthA가 더 작은 값이 오도록 하자
		int depthB = depths[b];
		
		//만약 depthA가 더 컸다면 A가 depth가 더 작아지도록 조정하자
		if(depthA > depthB) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		int depthDiff;
		int t = MAX_POW_DEPTH;
		//두개가 같은 높이가 될때까지 진행
		while( (depthDiff = depths[b] - depths[a]) != 0) {
			int pow = 1 << t;
			if( (depthDiff & pow) != 0) {
				//만약 해당 bit가 있으면
				b = parents[b][t];	//부모로 이동시키기
			}
			t--;					//더 낮은 bit로 이동
		}

		if(a == b) {
			//만약 높이를 맞추고 난 후 같은 vertex인 경우
			return a;		//a, b가 동일 vertex라는 뜻이다
		}
		
		for(int d = MAX_POW_DEPTH-1; d >= 0; d--) {
			if(parents[b][d] != parents[a][d]) {
				//2^d의 부모가 다르다 -> 여기까지는 이동해도 된다
				b = parents[b][d];
				a = parents[a][d];
			}
		}
		
		//최종적으로는 1depth만큼 차이나게 같은 부모의 밑 vertex까지 이동했다
		return parents[a][0];
	}
	
	public static void dfs(int parent, int curr, int depth) {
		depths[curr] = depth;						//자기자신의 깊이 기록
		parents[curr][0] = parent;					//바로 위 부모가 누구인지 기록
		
		for(int child: edges[curr]) {
			if(visited[child]) continue;		//방문한 곳은 가지말자
			visited[child] = true;
			dfs(curr, child, depth+1);			//자식노드들의 depth도 계산하자
		}
	}
}
