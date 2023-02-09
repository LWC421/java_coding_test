import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA2001 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));

		int T = Integer.parseInt(in.readLine());
		int N;				//맵의 크기
		int M;				//파리채의 크기
		int[][] map = null;	//맵
		int max;
		int target;
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");

		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine(), " ");
			N = Integer.parseInt(st.nextToken()) + 1;	//편하게 하기위해 +1
			M = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			max = 0;
			
			//맵 형성 및 구간합
			for(int y = 1; y < N; y++) {
				st = new StringTokenizer(in.readLine(), " ");
				for(int x = 1; x < N; x++) {
					map[y][x] = Integer.parseInt(st.nextToken()) +  map[y][x-1] + map[y-1][x] - map[y-1][x-1];
					if(y >= M && x >= M) {	//M크기에 대한 구간합 구해보기
						target = map[y][x] - (map[y][x-M] + map[y-M][x] - map[y-M][x-M]);
						max = Math.max(max, target);	//최대값 갱신
					}
				}
			}
			
			sb.append("#").append(test_case).append(" ").append(max).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
