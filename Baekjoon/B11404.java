import java.io.*;
import java.util.*;

public class B11404 {
	
	static final int LIMIT = 100_000 * 100 + 1;

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder();

		int numCity = Integer.parseInt(in.readLine());		//도시의 개수
		
		int[][] distances = new int[numCity+1][numCity+1];	//0번은 사용 안함
		for(int y = 1; y <= numCity; y++) {
			for(int x = 1; x <= numCity; x++) {
				distances[y][x] = LIMIT;		//거리 정보 최대값으로 초기화
			}
		}
		for(int i = 1; i <= numCity; i++) {
			distances[i][i] = 0;	//자기자신으로 가는 비용은 0이다
		}
		
		int numEdge = Integer.parseInt(in.readLine());	//도로의 개수
		int from, to, weight;
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			weight = Integer.parseInt(st.nextToken());
			
			distances[from][to] = Math.min(weight, distances[from][to]);	//from -> to : weight라는 뜻이다
		}
		//입력 종료
		
		for(int mid = 1; mid <= numCity; mid++) {
			for(int start = 1; start <= numCity; start++) {
				for(int end = 1; end <= numCity; end++) {
					int nWeight = distances[start][mid] + distances[mid][end];
					if(nWeight < distances[start][end]) {
						//경유해서 가는게 이득이면
						distances[start][end] = nWeight;
					}
				}
			}
		}
		
		for(int y = 1; y <= numCity; y++) {
			for(int x = 1; x <= numCity; x++) {
				sb.append(distances[y][x] == LIMIT ? 0 : distances[y][x]).append(' ');
			}
			sb.append('\n');
		}
		System.out.println(sb.toString());
	}
}
