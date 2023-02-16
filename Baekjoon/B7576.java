import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B7576 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int max = Integer.MIN_VALUE;
		
		int yLength, xLength;	//������ ũ��
		st = new StringTokenizer(in.readLine());
		xLength = Integer.parseInt(st.nextToken());
		yLength = Integer.parseInt(st.nextToken());
		//[y, x, count]�� ���·� ť�� �ֱ�
		Queue<int[]> q = new ArrayDeque<>();
		
		//1�� ���� �丶��, 0�� ���� ���� �丶��, -1�� �丶�䰡 ������� ���� ĭ
		int[][] map = new int[yLength+2][xLength+2];
		boolean[][] visited = new boolean[yLength+2][xLength+2];
		
		int number;
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 1; x <= xLength; x++) {
				number = Integer.parseInt(st.nextToken());
				map[y][x] = number;
				if(number == 1) {	//�丶���̸�
					q.add(new int[] { y, x, 0});	//������������ �ֱ�
				}
			}
		}
		
		//��ġ��
		for(int y = 0; y <= yLength+1; y++) {
			map[y][0] 		  = -1;	//�丶�䰡 ������� �ʴٰ� ǥ���ϱ�
			map[y][xLength+1] = -1;
		}
		for(int x = 0; x <= xLength+1; x++) {
			map[0][x] 		  = -1;
			map[yLength+1][x] = -1;
		}
		
		//BFS�ϱ�
		int[] tmp;
		int curY;
		int curX;
		int count;
		int nextY;
		int nextX;
		
		//4���� Ž��
		int[] dy = {-1, 0, 0, 1};
		int[] dx = {0, -1, 1, 0};
		while( !q.isEmpty() ) {
			tmp = q.poll();
			curY = tmp[0];
			curX = tmp[1];
			count = tmp[2];
			
			if(visited[curY][curX]) {
				continue;	//�̹� �湮�� ���̸�
			}
			
			max = Math.max(max, count);	//��¥ �������ֱ�
			visited[curY][curX] = true;	//�湮 �߰�
			map[curY][curX] = 1;	//�;��ٰ� ǥ��
			
			for(int i = 0; i < 4; i++) {
				nextY = curY + dy[i];
				nextX = curX + dx[i];
				if( !visited[nextY][nextX] &&	//�湮���� �ʾҰ�
						map[nextY][nextX] == 0) {	//���� ���� �丶���̸�
					q.add(new int[] {nextY, nextX, count+1});	//�湮�ϱ�
				}
			}
		}

		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				if(map[y][x] == 0) {	//���� ���� �丶�䰡 �ִ��� �˻�
					System.out.println("-1");	
					return;
				}
			}
		}
		System.out.println(max);
	}
}
