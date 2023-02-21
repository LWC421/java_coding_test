import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class B11724 {
	static int numEdge, numVertex;
	static int[] connect;

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		numEdge = Integer.parseInt(st.nextToken());
		numVertex = Integer.parseInt(st.nextToken());
		
		//연결요소 맵
		connect = new int[numEdge+1];
		for(int i = 1; i <= numEdge; i++) {
			connect[i] = i;
		}
	
		//그래프 입력 받기
		int source, target;
		for(int i = 0; i < numVertex; i++) {
			st = new StringTokenizer(in.readLine());
			source = Integer.parseInt(st.nextToken());
			target = Integer.parseInt(st.nextToken());
			union(source, target);
		}
		
		//연결이 덜 된 경우 존재하므로 재연결
		for(int i = 1; i <= numEdge; i++) {
			if(connect[i] != i) {	//누군가와 연결되어 있으면
				union(parent(i), i);
			}
		}

		//0번 빼고 연결 요소 넣기
		Set<Integer> set = new HashSet<>();
		for(int i = 1; i <= numEdge; i++) {
			set.add(connect[i]);
		}
		
//		System.out.println(Arrays.toString(connect));
		System.out.println(set.size());
	}
	
	//부모 노드 찾기
	public static int parent(int edge) {
		if(connect[edge] == edge) return edge;
		return connect[edge] = parent(connect[edge]);
	}

	//부모 노드를 합치기
	public static void union(int edge1, int edge2) {
		edge1 = parent(edge1);
		edge2 = parent(edge2);
		
		if(edge1 < edge2) {
		//작은쪽으로 합치기
			connect[edge2] = edge1;
		}
		else {
			connect[edge1] = edge2;
		}
	}
}
