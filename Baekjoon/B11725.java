import java.io.*;
import java.util.*;

public class B11725 {
	
	static List<Integer>[] edges;	//간선 정보 저장
	static int[] parents;			//부모 노드 정보 저장
	static boolean[] visited;		//방문 체크용

	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int numNode = Integer.parseInt(in.readLine());	//노드의 개수
		
		edges = new LinkedList[numNode+1];	//간선 정보 저장
		parents = new int[numNode+1];		//부모 노드 저장
		visited = new boolean[numNode+1];	//방문 체크용

		for(int i = 1; i <= numNode; i++) {
			edges[i] = new LinkedList<>();
		}
		
		int from, to;
		for(int i = 1; i < numNode; i++) {
			//연결 노드 받기
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			edges[from].add(to);
			edges[to].add(from);
		}
		//모든 입력 종료
		
		Queue<Integer> q = new ArrayDeque<>();
		q.add(1);		//1번 노드에서 시작
		int curr;
		while( !q.isEmpty() ) {
			curr = q.poll();
			
			for(int connected: edges[curr]) {
				//해당 노드에서 연결된 정점들에 대해
				if(visited[connected]) continue;	//이미 방문한거면 pass
				parents[connected] = curr;	//부모 노드 적어주고
				q.add(connected);
				visited[connected] = true;	//방문 예정으로 적기
			}
		}
		
		for(int i = 2; i <= numNode; i++) {
			sb.append(parents[i]).append('\n');
		}
		
		System.out.println(sb.toString());
	}	
}
