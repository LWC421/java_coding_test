import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA1868 {
	static int length;			//���� ũ�⸦ ��Ÿ����
	static int[][] map;			//���� ��Ÿ����, -1 : ���ڰ� ���°� / 0 : �� / 1 : ���� �ִ� ��
	static boolean[][] visited;	//�湮 �ߴ��� ǥ���ϱ�
	static int remainVisited;	//���� �湮�ؾ� �ϴ� ��
	static int count;			//�� �� ������ �ϴ��� Ȯ�ο�

	static Queue<int[]> q;			//BFS�� �� ���
	static PriorityQueue<int[]> pq;	//�� ��ȸ�ϱ� ���� ���
	static int[] current;			//ť���� ������ �ޱ�
	static int curY, curX;			//���� ��ġ
	static int nextY, nextX;		//���� ��ġ

	static String[] tmp;	//�Է� ������ ���

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");

		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			//-------------�Է� �ޱ�--------------
			length = Integer.parseInt(in.readLine());
			map = new int[length+2][length+2];
			visited = new boolean[length+2][length+2];
			for(int i = 0; i < length+2; i++) {
				//visited�� ��ġ��
				visited[i][0] 		 = true;
				visited[i][length+1] = true;
				visited[0][i] 		 = true;
				visited[length+1][i] = true;
			}

			remainVisited = length * length;	//�ϴ� length*length�� �ʱ�ȭ
			count = 0;	//ī��Ʈ �ʱ�ȭ
			q = new ArrayDeque<>();		//ť �ʱ�ȭ
			pq = new PriorityQueue<>((a, b) -> {
				return a[2] - b[2];	//�ֺ� ���� ���� ������ ����
			});	//�켱���� ť�� �ʱ�ȭ

			//�� �ʱ�ȭ�ϱ�
			for(int y = 1; y <= length; y++) {
				st = new StringTokenizer(in.readLine());
				tmp = st.nextToken().split("");
				for(int x = 1; x <= length; x++) {
					if(tmp[x-1].equals(".")) {	
						//���ڰ� ������
						map[y][x] = -1;
					}
					else {
						//���ڰ� �ִ�
						map[y][x] = 1;	
						visited[y][x] = true;	//���ڰ� �ִ� ���� �� �ʿ� ����
						remainVisited--;		//�湮�ؾ� �Ǵ� ���� Ƚ���� ���δ�
					}
				}
			}
			//-------------�Է� ����--------------
			for(int y = 1; y <= length; y++) {
				for(int x = 1; x <= length; x++) {
					int numBomb = 0;	//�ֺ��� ���� � �ִ���

					for(int dy = -1; dy <= 1; dy++) {
						for(int dx = -1; dx <= 1; dx++) {
							nextY = y + dy;
							nextX = x + dx;
							if(map[nextY][nextX] == 1) {
								numBomb++;
							}
						}
					}
					pq.add(new int[] {y, x, numBomb});
				}
			}
			while( !pq.isEmpty() ) {
				current = pq.poll();
				int y = current[0];
				int x = current[1];
				
				if(visited[y][x]) continue;	//Ȯ�� �غ� �ʿ� ������ �н�
				
				bfs(y, x);
				count++;
				if(remainVisited == 0) {
					break;
				}
			}
			sb.append("#").append(test_case).append(" ").append(count).append("\n");
		}

		System.out.print(sb.toString());
	}

	//����ã�� �ϱ�
	public static void bfs(int y, int x) {
		q.add(new int[] {y, x});

		//����ã�� ����
		while( !q.isEmpty() ) {
			current = q.poll();
			curY = current[0];
			curX = current[1];

			if(visited[curY][curX]) {
				//�̹� �湮 �ߴ� ���� ���
				continue;
			}
			remainVisited--;	//�湮 �ؾ� �Ǵ� �� Ƚ�� ���̱�
			visited[curY][curX] = true;

			boolean flag = true;
			Loop: for(int dy = -1; dy <= 1; dy++) {
				for(int dx = -1; dx <= 1; dx++) {
					if(dy == 0 && dx == 0) continue;	//���� ��ġ�� ���
					nextY = curY + dy;
					nextX = curX + dx;

					if(map[nextY][nextX] == 1) {	//���ڰ� �߰� �� ���!
						flag = false;	//��� ���⼭ �׸� �ؾ� �ȴ�
						break Loop;
					}
				}				
			}
			if(flag) {	//�� Ž���ص� �Ǹ� -> 8������ ���ڰ� ������ �̶�� ��
				for(int dy = -1; dy <= 1; dy++) {
					for(int dx = -1; dx <= 1; dx++) {
						if(dy == 0 && dx == 0) continue;	//���� ��ġ�� ���
						nextY = curY + dy;
						nextX = curX + dx;

						if( !visited[nextY][nextX]) {//�湮 �ߴ� ���� ������
							q.add(new int[] { nextY, nextX });
						}
					}				
				}
			}
		}
	}
}
