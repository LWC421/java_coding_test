import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SWEA4012 {
	static int N;	    	//식재료의 개수
	static int limit;		//식재료 절반으로 나눈거
	static int[] select;	//선택된 재료
	static int[] select2;	//반대쪽 재료
	
	static int[][] map;		//시너지 맵
	static int min;			//두개 맛의 차이
	
	public static void main(String[] args) throws Exception{
		//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");

		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			min = Integer.MAX_VALUE;
			
			N = Integer.parseInt(in.readLine());
			limit = N/2;
			select = new int[limit];
			select2 = new int[limit];
			
			map = new int[N][N];	//맵 초기화
			for(int y = 0; y < N; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 0; x < N; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			//입력 받기 종료
			
			//상 삼각행렬로 변환 -> 시너지 2개 미리 합해놓기
			for(int y = 0; y < N; y++) {
				for(int x = y+1; x < N; x++) {
					map[y][x] += map[x][y];
				}
			}
			
			combi(0, 0);
			
			//출력 넣기
			sb.append("#").append(test_case).append(" ").append(min).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public static void combi(int count, int current) {
		if(count == limit) {
			int sum = 0;
			for(int i = 0; i < limit-1; i++) {
				for(int j = i+1; j < limit; j++) {
					sum += map[select[i]][select[j]];
				}
			}
			
			
			//반대쪽 요리의 재료는 선택 안된 것들이다
			int index = 0;
			Loop: for(int i = 0; i < N; i++) {	//i를 선택할려 하는데
				for(int j : select) {	//선택되어 있는 재료들 확인
					if(i == j) {
						continue Loop;	//해당 재료 선택안하기
					}
				}
				select2[index++] = i;	//선택 안되어 있으므로 선택
			}
			
			int sum2 = 0;
			for(int i = 0; i < limit-1; i++) {
				for(int j = i+1; j < limit; j++) {
					sum2 += map[select2[i]][select2[j]];
				}
			}
			
			
			min = Math.min(min, Math.abs(sum - sum2));
			return;
		}
		
		for(int i = current; i < N; i++) {
			select[count] = i;
			combi(count+1, i+1);
		}
	}
}
