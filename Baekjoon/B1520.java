import java.io.*;
import java.util.*;

public class B1520 {
	
	//����Ʈ ����
	final static int LIMIT = 1_000_000_000 + 1;
	
	//4���� Ž����
	final static int[] dy = {1, 0, -1, 0};
	final static int[] dx = {0, 1, 0, -1};
	
	//���̵� ����
	static int[][] map;
	
	static int[][] dpTable;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int yLength = Integer.parseInt(st.nextToken());
		int xLength = Integer.parseInt(st.nextToken());
		
		dpTable = new int[yLength+2][xLength+2];
		
		map = new int[yLength+2][xLength+2];
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine(), " ");
			for(int x = 1; x <= xLength; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
				dpTable[y][x] = LIMIT;	//�ϴ� LIMIT�� ����
			}
		}
		//�Է� ����
		
		//�� ġ��
		for(int x = 0, last = xLength+1; x <= last; x++) {
			map[0][x] = LIMIT;
			map[yLength+1][x] = LIMIT;
		}
		for(int y = 0, last = yLength+1; y <= last; y++) {
			map[y][0] = LIMIT;
			map[y][xLength+1] = LIMIT;
		}
		//��ġ�� ����
		
		
		int nY, nX, nHeight;
		int currHeight;
		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				currHeight = map[y][x];
				int count = 0;
				for(int d = 0; d < 4; d++) {
					nY = y + dy[d];
					nX = x + dx[d];
					nHeight = map[nY][nX];
					
					if(nHeight == LIMIT) continue;	//�� ���̸�
					if(currHeight > nHeight) {
						//���⼭ �� �� �ִ� ���� ������ ī��Ʈ
						count++;
					}
				}
				if(count == 0) {
					//���� �� �������� �������� �� ������
					dpTable[y][x] = 0;
				}
			}
		}
		dpTable[1][1] = 1;	//ó�� ������ 1�� ����
		
//		for(int[] row: dpTable) {
//			System.out.println(Arrays.toString(row));
//		}
//		
		dpTable[yLength][xLength] = LIMIT;	//������ ������ �ٽ� �ʱ�ȭ
		int result = dp(yLength, xLength);
		
		System.out.println(result);
	}
	
	static int dp(int y, int x) {
//		System.out.printf("[%d, %d]\n", y, x);
		if(dpTable[y][x] != LIMIT) {
			//����� ���� ������
			return dpTable[y][x];
		}
		if(dpTable[y][x] == 0) {
			return 0;
		}
		
		dpTable[y][x] = 0;
		
		int currHeight = map[y][x];
		int nY, nX, nHeight;
		
		for(int d = 0; d < 4; d++) {
			nY = y + dy[d];
			nX = x + dx[d];
			nHeight = map[nY][nX];
			
			if(nHeight == LIMIT) {
				//�� ���̸�
				continue;
			}
			
			if(nHeight > currHeight) {
				//������������ �������� ���Ƿ� 
				//�� �������� ������ �����Ѵ�
				dpTable[y][x] += dp(nY, nX);
			}
		}
		
		return dpTable[y][x];
	}

	
	static class Coord{
		int y;
		int x;
		
		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}
		
		@Override
		public int hashCode() {
			return y * 501 + x;
		}
		
		@Override
		public boolean equals(Object o) {
			Coord other = (Coord)o;
			return this.y == other.y && this.x == other.x;
		}
		
		@Override
		public String toString() {
			return String.format("[%d, %d]", y, x);
		}
	}
}
