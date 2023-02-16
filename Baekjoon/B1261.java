import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B1261 {
	static int yLength;
	static int xLength;
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(in.readLine());
		xLength = Integer.parseInt(st.nextToken());
		yLength = Integer.parseInt(st.nextToken());
		
		//�� ����
		int[][] map = new int[yLength+2][xLength+2];
		//�湮 ����
		//�ش� ��ġ���� ��  ���� ���� �վ�� �ϴ��� ����
		int[][] count = new int[yLength+2][xLength+2];
		
		String s = null;
		for(int y = 1; y <= yLength; y++) {
			s = in.readLine();
			for(int x = 1; x <= xLength; x++) {
				map[y][x] = Integer.parseInt(String.valueOf(s.charAt(x-1)));
				count[y][x] = Integer.MAX_VALUE;	//ī��Ʈ���� �ϴ� -1�� ����
			}
		}
		
		//������ ������ ǥ��
		for(int y = 0; y <= yLength+1; y++) {
			map[y][0] = -1;	
			map[y][xLength+1] = -1;
		}
		for(int x = 0; x <= xLength+1; x++) {
			map[0][x] = -1;	
			map[yLength+1][x] = -1;
		}
		
		//ť�� ���, [y, x, ���� ��]
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {1, 1, 0});
		
		//ť ����� ���� ���� ����
		int[] tmp;
		int curY;
		int curX;
		int curCount;
		
		int nextY;
		int nextX;
		
		int[] dy = {-1, 0, 0, 1};
		int[] dx = {0, -1, 1, 0};
		
		//BFS�ϱ�
		while( !q.isEmpty() ) {
			tmp = q.poll();
			curY 	 = tmp[0];
			curX 	 = tmp[1];
			curCount = tmp[2];
			
			//���� ī��Ʈ���� ���� �Դ� ���� ī��Ʈ�� ũ�ų� ������ -> ���� �� �ʿ� ����
			if( count[curY][curX] <= curCount ) {
				continue;
			}
			
			count[curY][curX] = curCount;	//���� ����� ī��Ʈ ����
			for(int i = 0; i < 4; i++) {
				nextY = curY + dy[i];
				nextX = curX + dx[i];
				
				if(map[nextY][nextX] == -1) {
					//�� ���� ���
					continue;
				}
				else if(map[nextY][nextX] == 0) {
					//�շ� �ִ� ���� ���
					q.add(new int[] { nextY, nextX, curCount });
				}
				else {
					//���� �ִ� ��� -> �հ� �����Ѵ�
					q.add(new int[] { nextY, nextX, curCount+1 });
				}
			}
		}
		
		System.out.println(count[yLength][xLength]);
	}
}
