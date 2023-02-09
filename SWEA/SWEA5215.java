import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA5215 {
	static int N;			//재료의 수
	static int L;			//제한 칼로리
	static int[][] zeryo;	//[점수, 칼로리]로 이루어진 배열
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		StringTokenizer st = null;
		
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken()) + 1;	//재료의 개수 -> 편하게 하기 위해 +1 하였다
			L = Integer.parseInt(st.nextToken()) + 1;	//제한 칼로리 -> 편하게 하기 위해 +1 하였다
			
			//재료 정보 초기화
			//편하게 하기위해 0번은 안쓴다
			zeryo = new int[N][2];
			for(int i = 1; i < N; i++) {
				st = new StringTokenizer(in.readLine());
				zeryo[i][0] = Integer.parseInt(st.nextToken());
				zeryo[i][1] = Integer.parseInt(st.nextToken());
			}
			
			//행은 각 재료에 대해 
			//열은 제한 칼로리에 대해
			//행, 열의 해당하는 값은 그때의 최대 점수를 나타낸다
			int[][] map = new int[N][L];			
			
			//현재 선택된 재료의 점수 및 칼로리
			int curScore = 0;
			int curCal = 0;
			
			
			for(int y = 1; y < N; y++) {	//재료에 대해
				for(int x = 1; x < L; x++) {	//칼로리에 대해
					curScore = zeryo[y][0];
					curCal = zeryo[y][1];
					
					if( (x-curCal) > 0 && (map[y-1][x-curCal] + curScore) > map[y-1][x]) {	//이전 값에서 재료를 추가하는 게 더 높을 경우
						int currentMax = map[y-1][x-curCal] + curScore;
						map[y][x] = currentMax;	//점수 갱신
					}
					else {	//해당 재료를 추가하지 않는다
						map[y][x] = map[y-1][x];
					}
				}
			}
			
			sb.append("#").append(test_case).append(" ").append(map[N-1][L-1]).append("\n");
		}
		
		System.out.println(sb);
	}
}
