import java.io.*;
import java.util.*;


public class B1726 {
	// ���� ���� �ִ� �������� 1 ~ 3ĭ�� ����
	// ���ʶǴ� ���������� 90�� ȸ���Ѵ�
	
	//���� 
	// 0 : �� �� �ִ�
	// 1 : �κ��� �� �� ����
	
	static final int LIMIT = 100 * 100 * 5 + 1;
	
	static int yLength;
	static int xLength;
	
	//�� �� �� �� ���� ->
	//�� �� �� ��
	final static int[] dy = {0, 1, 0, -1};
	final static int[] dx = {1, 0, -1, 0};
	
	static int[][] map;		//��
	static int[][][] visited;	//�湮 üũ��
	
	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		yLength = Integer.parseInt(st.nextToken());	//������ ����
		xLength = Integer.parseInt(st.nextToken());	//������ ����
		
		map = new int[yLength+2][xLength+2];
		visited = new int[yLength+2][xLength+2][4];
		
		//�� �Է�
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine(), " ");
			for(int x = 1; x <= xLength; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		//�� �Է� ����
		
		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				for(int d = 0; d < 4; d++) {
					visited[y][x][d] = LIMIT;
				}
			}
		}
		
		int y, x, dir;
		st = new StringTokenizer(in.readLine());
		y = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		dir = Integer.parseInt(st.nextToken());
		if(dir == 1) dir = 0;		//��
		else if(dir == 2) dir = 2;	//��
		else if(dir == 3) dir = 1;	//��
		else if(dir == 4) dir = 3;	//��
				
		Robot start = new Robot(y, x, dir);	//���������� ���� ���� ����
		
		st = new StringTokenizer(in.readLine());
		y = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		dir = Integer.parseInt(st.nextToken());
		if(dir == 1) dir = 0;		//��
		else if(dir == 2) dir = 2;	//��
		else if(dir == 3) dir = 1;	//��
		else if(dir == 4) dir = 3;	//��
		Robot finish = new Robot(y, x, dir);	//���������� ���� ���� ����


		PriorityQueue<Data> pq = new PriorityQueue<>();
		pq.add(new Data(start.y, start.x, start.dir, 0));	//start���� ����, 0���� ���
		
		int result = LIMIT;
		//��� �ݺ�
		int nY, nX, nCount;
		Data curr = null;
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			if(curr.count > result) {
				//result���� ũ����
				break;
			}
			
			if(curr.y == finish.y && curr.x == finish.x) {
				//���� �������̸�
				int minRotate = Math.min(Math.abs(curr.dir - finish.dir), Math.abs(curr.dir+4 - finish.dir));
				minRotate = Math.min(minRotate, Math.abs(curr.dir - (finish.dir + 4)));
				result = Math.min(curr.count + minRotate, result);
				continue;
			}
			
			Loop: for(int d = 0; d < 4; d++) {
				//4������ ����
				for(int dis = 1; dis <= 3; dis++){
					//1 ~ 3ĭ�� ���� ��������
					nY = curr.y + (dy[d] * dis);
					nX = curr.x + (dx[d] * dis);
					if(nY <= 0 || nY > yLength) continue;
					if(nX <= 0 || nX > xLength) continue;	//�� �۳����� �� üũ

					int minRotate = Math.min(Math.abs((curr.dir) + 4 - d), Math.abs(curr.dir - d));
					minRotate = Math.min(minRotate, Math.abs(curr.dir - (d + 4)));
					nCount = curr.count + (minRotate + 1);	//�ش� ĭ���� ���� �� �ʿ��� ��� Ƚ��
					
					if(nCount >= visited[nY][nX][d]) continue;	//���� �ش� ĭ���� ���� �� �̵��� �ƴϸ�
					for(int dd = 1; dd <= dis; dd++) {
						if(map[curr.y + (dy[d] * dd)][curr.x + (dx[d] * dd)] == 1) continue Loop;		//���� ���
					}
					
					pq.add(new Data(nY, nX, d, nCount));	//�ش� ĭ�� �������� ���� üũ
					visited[nY][nX][d] = nCount;	//�ش� ĭ�� �ش� ������� ���ٰ� ǥ��
				}
			}
		}
		System.out.println(result);
	}
	
	static class Data implements Comparable<Data>{
		int y;
		int x;
		int dir;
		int count;
		
		public Data(int y, int x, int dir, int count) {
			this.y = y;
			this.x = x;
			this.dir = dir;
			this.count = count;
		}
		
		@Override
		public int compareTo(Data other) {
			return this.count - other.count;
		}
		
		@Override
		public String toString() {
			String direction = "";
			if(dir == 0) direction = "��";
			else if(dir == 1) direction = "��";
			else if(dir == 2) direction = "��";
			else if(dir == 3) direction = "��";
			return String.format("[%d, %d] : %s, %dcount", y, x, direction, count);
		}
	}
	
	static class Robot{
		int y;
		int x;
		int dir;	//0 -> ���� ...
		
		public Robot(int y, int x, int dir) {
			this.y = y;
			this.x = x;
			this.dir = dir;
		}
		
		@Override
		public String toString() {
			return String.format("[%d, %d] : %d", y, x, dir);
		}
	}
}
