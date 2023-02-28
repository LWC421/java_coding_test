import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SWEA3124 {
	
	static int[] parents;	//union-find에 필요

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		int numVertex;	//정점의 개수
		int numEdge;	//간선의 개수
		int[][] edges;	//간선 정보 저장, [from, to, weight]
		
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			numVertex = Integer.parseInt(st.nextToken());
			numEdge = Integer.parseInt(st.nextToken());
			
			edges = new int[numEdge][3];	//간선 초기화
			parents = new int[numVertex+1];	//집합 정보 초기화, 1번부터 시작이니까 +1
			
			for(int i = 0; i < numEdge; i++) {
				//간선 정보 받기
				st = new StringTokenizer(in.readLine());
				edges[i][0] = Integer.parseInt(st.nextToken());
				edges[i][1] = Integer.parseInt(st.nextToken());
				edges[i][2] = Integer.parseInt(st.nextToken());
			}
			
			//가중치에 따라 정렬
			Arrays.sort(edges, (a, b) -> {
				return a[2] - b[2];
			});
			
			//서로소 집합 정보 초기화
			for(int i = 0; i < numVertex; i++) {
				parents[i] = i;
			}
			
			//Kruskal하기
			long weightSum = 0;
			int count = 1;

			int from, to, weight;
			for(int i = 0; i < numEdge; i++) {
				from = edges[i][0];
				to = edges[i][1];
				weight = edges[i][2];
				
				if(union(from, to)) {
					//안 합쳐져 있었을 때만
					weightSum += weight;
					count++;
					
					if(count == numVertex) {
						//MST가 완성이 될 경우
						break;
					}
				}
			}
			
			sb.append(String.format("#%d %d\n", test_case, weightSum));
		}
		
		System.out.println(sb.toString());
	}
	
	//대표자 찾기
	public static int find(int x) {
		if(x == parents[x]) return x;
		return parents[x] = find(parents[x]);
	}

	//합치기
	public static boolean union(int x, int y) {
		x = find(x);
		y = find(y);
		
		if(x == y) return false;	//합쳐지지 않았다
		
		parents[x] = y;	//합치고
		return true;	//제대로 합쳐졌다고 반환
	}
}
