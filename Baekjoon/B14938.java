import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B14938 {
	
	static int range; 			//m, 예은이의 수색 범위
	static int maxItem = 0;		//최대 아이템 개수
	static int currentItem = 0;	//현재 주운 아이템 개수
	static int[] items;			//각 정점의 아이템의 개수
	static boolean[] selected;		//아이템 주웠는지 안 주웠는지
	static Node[] edges;		//간선 정보 저장

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
		int numVertex;	//n, [1 ~ 100]
		int numEdge;	//r, 길의 개수
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		numVertex = Integer.parseInt(st.nextToken());
		range = Integer.parseInt(st.nextToken());
		numEdge = Integer.parseInt(st.nextToken());
		
		items = new int[numVertex+1];
		selected = new boolean[numVertex+1];	//아이템 주웠는지 여부
		
		//아이템 가중치 받아오기
		st = new StringTokenizer(in.readLine());
		for(int i = 1; i <= numVertex; i++) {
			items[i] = Integer.parseInt(st.nextToken());
		}
		
		//간선 정보 받아오기
		int vertex1, vertex2;	//어디에서 
		int weight;				//길의 길이
		
		//간선 정보를 간선리스트로 저장
		edges = new Node[numVertex+1];
		
		//길 정보 받아오기
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine());
			vertex1 = Integer.parseInt(st.nextToken());
			vertex2 = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			//간선리스트로 사용
			edges[vertex1] = new Node(vertex2, weight, edges[vertex1]);
			edges[vertex2] = new Node(vertex1, weight, edges[vertex2]);
		}
		
		int itemCount = 0;
		//1번부터 시작해서 dfs해보고  시작을 2번, 3번...으로 해서 dfs 해보기
		for(int i = 1; i <= numVertex; i++) {
			currentItem = 0;
			boolean[] visited = new boolean[numVertex+1];
			visited[i] = true;	//이미 방문했다고 표시
			Arrays.fill(selected, false); //아무것도 선택하지 않았다고 하기
			dfs(i, 0, visited); //현재 시작지점을 기준으로 다 돌아보기
			
			itemCount = 0;
			for(int j = 1; j <= numVertex; j++) {
				if(selected[j]) {
					//주울 수 있는 아이템이면 줍기
					itemCount += items[j];
				}
			}
			maxItem = Math.max(maxItem, itemCount);
		}
		
		System.out.println(maxItem);
	}
	
	//현재 정점, 길의 길이, 아이템 개수, 방문체크 배열
	public static void dfs(int current, int loadWeight, boolean[] visited) {
		selected[current] = true;	//현재꺼는 갈 수 있다고 표시
		
		Node n = edges[current];
		int nextWeight;
		while(n != null) {
			nextWeight = loadWeight + n.weight;
			if( !visited[n.vertex] && nextWeight <= range ) {
				//방문안했고 수색범위내에 있을때만
				visited[n.vertex] = true;	//방문 예정으로
				dfs(n.vertex, nextWeight, visited);
				visited[n.vertex] = false;	//방문 안했던걸로 돌리기
			}
			n = n.next;
		}
		
	}
	
	static class Node{
		int vertex;
		int weight;
		Node next;
		
		public Node(int vertex, int weight, Node next) {
			this.vertex = vertex;
			this.weight = weight;
			this.next = next;
		}
	}
}
