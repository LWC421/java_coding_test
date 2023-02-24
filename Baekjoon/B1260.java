import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class B1260 {
	
	static int numVertex;			//N, 정점의 개수
	static int numEdge;				//M, 간선의 개수
	static int startVertex;			//시작 정점 번호
	static boolean visited[];		//방문 체크
	
	static List<Integer>[] matrix;	//정점 연결정보 잇기
	static StringBuilder sb = new StringBuilder("");
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		//그래프 관련 입력 받기
		numVertex = Integer.parseInt(st.nextToken());
		numEdge = Integer.parseInt(st.nextToken());
		startVertex = Integer.parseInt(st.nextToken());
		
		visited = new boolean[numVertex+1];	//방문체크 초기화
		matrix = new ArrayList[numVertex+1];	//연결 정보 초기화
		for(int i = 0; i <= numVertex; i++) {
			matrix[i] = new ArrayList<>();
		}
		
		//간선 정보 받기
		int from, to;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine());
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			matrix[from].add(to);
			matrix[to].add(from);
		}
		
		//입력 끝
		
		//정렬하기
		for(int i = 0; i <= numVertex; i++) {
			matrix[i].sort((a, b) -> {
				return Integer.compare(a, b);
			});	//숫자기준 오름차순으로 정렬
		}
		
		//탐색하기
		visited[startVertex] = true;	//시작 정점은 이미 방문한거다
		dfs(startVertex);
		sb.append("\n");	//줄바꿈넣기
		
		Arrays.fill(visited, false);	//방문체크 배열 초기화
		visited[startVertex] = true;	//시작 정점은 이미 방문한거다
		bfs(startVertex);
		
		System.out.println(sb.toString());
	}
	
	public static void dfs(int current) {
		//현재 정점 번호 넣고
		sb.append(String.format("%d ", current));
		
		List<Integer> list = matrix[current];
		
		for(int to: list) {
			if( !visited[to] ) {
				//방문 하지 않았다면
				visited[to] = true;
				dfs(to);
			}
		}
	}
	
	public static void bfs(int current) {
		Queue<Integer> q = new ArrayDeque<>();
		q.add(current);
		
		List<Integer> list;
		int from;
		while( !q.isEmpty()) {
			from = q.poll();
			//현재 정점 번호 넣기
			sb.append(String.format("%d ", from));
			list = matrix[from];

			for(int to: list) {
				if( !visited[to] ) {
					q.add(to);
					visited[to] = true;
				}
			}
		}
	}
}
