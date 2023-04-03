import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SWEA4193 {

	//4���� Ž��
	static final int[] dy = {0, 1, 0, -1};
	static final int[] dx = {1, 0, -1, 0};

	public static void main(String[] args) throws Exception{
		// N*N ������ [2 ~ 15]
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

			int[][] map = new int[length+2][length+2];
			for(int y = 1; y <= length; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 1; x <= length; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());	
				}
			}
			st = new StringTokenizer(in.readLine());
			Data start = new Data(Integer.parseInt(st.nextToken()) + 1, Integer.parseInt(st.nextToken()) + 1, 0);
			
			st = new StringTokenizer(in.readLine());
			int finishY, finishX;		//���� �ޱ�
			finishY = Integer.parseInt(st.nextToken()) + 1;
			finishX = Integer.parseInt(st.nextToken()) + 1;
			//�Է� ����
			
			//�湮üũ
			boolean[][] visited = new boolean[length+2][length+2];
			for(int i = 0, limit=length+1; i <= limit; i++) {
				map[0][i] = 1;
				map[limit][i] = 1;
				map[i][0] = 1;
				map[i][limit] = 1;
				
				visited[0][i] = true;
				visited[limit][i] = true;
				visited[i][0] = true;
				visited[i][limit] = true;
			}
			
			PriorityQueue<Data> pq = new PriorityQueue<>();
			pq.add(start);	//�������� �ֱ�
			
			Data curr = null;			//ť���� ������ ����ϱ� ��
			int nY, nX;
			int nTime;
			boolean solved = false;		//�����ߴٴ� �Ҹ�
			int lastTime = 0;
			while( !pq.isEmpty() ) {
				curr = pq.poll();
				
				if(curr.y == finishY && curr.x == finishX) {
					//�����ߴ�
					solved = true;
					lastTime = curr.time;	//�̽ð��� ����
					break;	//�׸� Ž������
				}
				
				if( visited[curr.y][curr.x] ) {
					//�ٸ� ������ �̹� �湮������
					continue;
				}
				
				visited[curr.y][curr.x] = true;	//������ �������� ���� �湮 üũ
				
				for(int d = 0; d < 4; d++) {
					nY = curr.y + dy[d];
					nX = curr.x + dx[d];
					
					if(map[nY][nX] == 1) continue;		//���̸�
					if(visited[nY][nX] ) continue;		//�̹� �湮 ������
					
					if(map[nY][nX] == 2) {
						//�㸮�����̸�
						nTime = 3 - (curr.time % 3);
						pq.add(new Data(nY, nX, curr.time  + nTime));		//�� �ð��� �����ϰԵȴ�
					} else {
						//�׳� �ٴ��̸�
						pq.add(new Data(nY, nX, curr.time+1));	//�ܼ��� +1�ð��� �����ϰԵȴ�
					}
				}
			}

			sb.append(String.format("#%d %d\n", test_case, solved ? lastTime : -1));
		}

		System.out.println(sb.toString());
	}

	static class Data implements Comparable<Data>{
		int y;
		int x;
		int time;

		public Data(int y, int x, int time) {
			this.y = y;
			this.x = x;
			this.time = time;
		}
		
		public int compareTo(Data other) {
			return this.time - other.time;
		}
	}
}
