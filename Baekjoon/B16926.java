import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B16926 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int N;	//행의 크기
		int M;	//열의 크기
		int R;	//회전 시키는 횟수

		StringTokenizer st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		int numCircle = Math.min(N, M) / 2;
		int current;
		int y;
		int x;
		int z;
		int i;
		int length;
		
		//맵 초기화
		int[][] map = new int[N][M];
		for(y = 0; y < N; y++) {
			st = new StringTokenizer(in.readLine());
			for(x = 0; x < M; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		//
		int[][] circles = new int[numCircle][];
		int count = 0;
		for(i = 0; i < numCircle; i++) {
			count = (N - (2*i) - 1) * 2 + (M - (2*i) - 1) * 2;
			circles[i] = new int[count];
		}

		//바깥쪽부터 돈다

		for(z = 0; z < numCircle; z++) {
			current = 0;
			for(x = z; x < M-z-1; x++) {
				circles[z][current++] = map[z][x];
			}
			for(y = z; y < N-z-1; y++) {
				circles[z][current++] = map[y][M-z-1];
			}
			for(x = M-z-1; x > z; x--) {
				circles[z][current++] = map[N-z-1][x];
			}
			for(y = N-z-1; y > z; y--) {
				circles[z][current++] = map[y][z];
			}
		}

		//바깥쪽부터 돈다
		for(z = 0; z < numCircle; z++) {
			length = circles[z].length;
			current = R % length;
			
			for(x = z; x < M-z-1; x++) {
				map[z][x] = circles[z][current];
				current = (current+1) % length;
			}
			for(y = z; y < N-z-1; y++) {
				map[y][M-z-1] = circles[z][current];
				current = (current+1) % length;
			}
			for(x = M-z-1; x > z; x--) {
				map[N-z-1][x] = circles[z][current];
				current = (current+1) % length;
			}
			for(y = N-z-1; y > z; y--) {
				map[y][z] = circles[z][current];
				current = (current+1) % length;
			}
		}

		for(int[] row: map) {
			for(int r: row) {
				sb.append(r).append(" ");
			}
			sb.append("\n");
		}
		
		System.out.print(sb.toString());
	}
}
