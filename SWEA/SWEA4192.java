import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA4192 {
	
	//4���� Ž��
	static final int[] dy = {0, 1, 0, -1};
	static final int[] dx = {1, 0, -1, 0};

	public static void main(String[] args) throws Exception{
		// N*N ������
		// 0: ��ĭ, 1: ��ֹ�
		// ������ġ [A, B]
		// ������ġ [C, D]
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		int T = Integer.parseInt(in.readLine());
		
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//�׽�Ʈ ���̽� ����
			int length = Integer.parseInt(in.readLine());	//N, ���� ũ��
			
			boolean[][] map = new boolean[length+2][length+2];
			for(int y = 1; y <= length; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 1; x <= length; x++) {
					map[y][x] = Integer.parseInt(st.nextToken()) == 0 ? true : false;	//�� �� ������ true�� ǥ��
				}
			}
			st = new StringTokenizer(in.readLine());
			Coord start = new Coord(Integer.parseInt(st.nextToken()) + 1, Integer.parseInt(st.nextToken()) + 1);
			st = new StringTokenizer(in.readLine());
			Coord end = new Coord(Integer.parseInt(st.nextToken()) + 1, Integer.parseInt(st.nextToken()) + 1);
			//�Է� ����
			
			boolean[][] visited = new boolean[length+2][length+2];
			Queue<Coord> q = new ArrayDeque<>();
			q.add(start);	//�������� �ֱ�
			visited[start.y][start.x] = true;	//�������� ǥ��
			
			Coord curr = null;		//ť���� ������ ���
			boolean solved = false;	//��������
			int time = 0;			//�ð� üũ
			int nY, nX;
			Loop: while( !q.isEmpty() ) {
				int size = q.size();		//size������ŭ�� ����
				for(int s = 0; s < size; s++) {
					curr = q.poll();
					if(curr.y == end.y && curr.x == end.x) {
						//�����ߴ�!
						solved = true;
						break Loop;
					}
					for(int d = 0; d < 4; d++) {
						nY = curr.y + dy[d];
						nX = curr.x + dx[d];
						if( !map[nY][nX] ) continue;	//������ �� �Ǿ� ������
						if(visited[nY][nX]) continue;	//�̹� �湮�� ������
						visited[nY][nX] = true;			//�湮 �������� �ֱ�
						q.add(new Coord(nY, nX));		//���� ���� �ֱ�
					}
				}
				time++;
			}
			
			
			sb.append(String.format("#%d %d\n", test_case, solved ? time : -1));
		}
		
		System.out.println(sb.toString());
	}
	
	static class Coord{
		int y;
		int x;
		
		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
}
