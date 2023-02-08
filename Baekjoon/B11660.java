import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B11660 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		StringBuilder sb = new StringBuilder("");
		
		int N = Integer.parseInt(st.nextToken());	//ǥ�� ũ��
		int M = Integer.parseInt(st.nextToken());	//�� ���ϴ� Ƚ��
		
		//�� �����ϱ�
		long[][] map = new long[N+1][N+1];	//N+1ũ��� �ؾ� ���ϴ�
		for(int y = 1; y <= N; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 1; x <= N; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		
		//���� ������ ���ϱ�
		//�� ������ �׳� ���ϱ�
		for(int x = 2; x <= N; x++) {
			map[1][x] = map[1][x] + map[1][x-1];
		}
		//�� �����ٵ� �׳� ���ϱ�
		for(int y = 2; y <= N; y++) {
			map[y][1] = map[y][1] + map[y-1][1];
		}
		//�� ����, ���� ������ �κ� ���ϱ�
		for(int y = 2; y <= N; y++) {
			for(int x = 2; x <= N; x++) {
				map[y][x] = map[y-1][x] + map[y][x-1] + map[y][x] - map[y-1][x-1];	//������ ���� ���� �׸��� �ڱ��ڽ��� ���� ��, ���������� �ϳ� ����
			}
		}
		
		//M�� ���� x1, y1, x2, y2��ǥ�� �־�����
		int x1;
		int y1;
		int x2;
		int y2;
		long sum = 0;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(in.readLine());
			sum = 0;
			x1 = Integer.parseInt(st.nextToken());
			y1 = Integer.parseInt(st.nextToken());
			x2 = Integer.parseInt(st.nextToken());
			y2 = Integer.parseInt(st.nextToken());
			
//			sum = map[y2][x2] - map[y1-1][x2] - map[y2][x1-1] + map[y1-1][x1-1];
			sum = map[x2][y2] - map[x1-1][y2] - map[x2][y1-1] + map[x1-1][y1-1];
			
			sb.append(sum).append("\n");
		}
		
//		for(long[] row: map) {
//			System.out.println(Arrays.toString(row));
//		}
		
		System.out.println(sb.toString());
	}
	
}
