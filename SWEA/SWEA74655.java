import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA74655 {
	
	static int[] parents;	//부모 관리
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringBuilder sb = new StringBuilder("");
		
		//테스트 케이스 개수
		int T = Integer.parseInt(in.readLine());
		StringTokenizer st = null;
		
		
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			int numVertex = Integer.parseInt(st.nextToken());
			int numCommand = Integer.parseInt(st.nextToken());
			
			parents = new int[numVertex+1];
			for(int i = 1; i <= numVertex; i++) {
				parents[i] = i;
			}
			
			int command;	//0 -> 합치기, 1-> 포함여부 확인
			int vertex1;
			int vertex2;	//정점 2개 입력 받기
			sb.append(String.format("#%d ", test_case));
			for(int i = 0; i < numCommand; i++) {
				//커맨드 개수만큼 실행
				st = new StringTokenizer(in.readLine());
				command = Integer.parseInt(st.nextToken());
				vertex1 = Integer.parseInt(st.nextToken());
				vertex2 = Integer.parseInt(st.nextToken());
				
				if(command == 0) {
					//합치기 연산
					union(vertex1, vertex2);
				}
				else {
					//확인 연산
					if(find(vertex1) == find(vertex2)) {
						sb.append("1");	//같은 집합이면
					}
					else {
						sb.append("0");	//다른 집합이면
					}
				}
			}
			
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public static int find(int x) {
		if(x == parents[x]) return x;
		return parents[x] = find(parents[x]);
	}
	
	public static void union(int x, int y) {
		x = find(x);
		y = find(y);
		parents[x] = y;
	}
}
