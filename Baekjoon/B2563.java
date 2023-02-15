import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2563 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int numPaper = Integer.parseInt(in.readLine());
		
		int y, x;	//색종이를 붙인 곳
		//색종이의 크기는 10 * 10으로 고정
		
		int[][] map = new int[100][100];
		
		for(int i = 0; i < numPaper; i++) {
			st = new StringTokenizer(in.readLine());
			y = Integer.parseInt(st.nextToken());
			x = Integer.parseInt(st.nextToken());
			
			for(int dy = 0; dy < 10; dy++) {
				for(int dx = 0; dx < 10; dx++) {
					map[y+dy][x+dx] = 1;
				}
			}
		}
		
		int sum = 0;
		for(int dy = 0; dy < 100; dy++) {
			for(int dx = 0; dx < 100; dx++) {
				sum += map[dy][dx];
			}
		}
		System.out.println(sum);
	}
}
