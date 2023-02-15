import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SWEA1861 {
	
	static int N;						//���� ũ��
	static int[][] map;					//���ڰ� ���� ���, [N+2][N+2]ũ��� �ʱ�ȭ �Ѵ�
	static int number;					//���� �Է� ���� �� ��� �� ��
	static boolean[][] visited; 		//�湮�ߴ��� Ȯ�� ��
	static PriorityQueue<int[]> q;		//������ Ȯ���غ����� ���� [y, x, number]�� ����
	static int[] tmp;					//ť���� ���� �� �ӽ÷� ������ ��
	
	static int y, x;			//���� ��ġ
	static int nextY, nextX;	//���� ��ġ
	static int count;			//�� ���� ���� ������
	
	static int[] dy = new int[] {-1, 0, 0, 1};		//��, ��, ��, �� �湮��
	static int[] dx = new int[] {0, -1, 1, 0};		//��, ��, ��, �� �湮��
	
	static int max;			//���� ���� ���� ����
	static int startNumber;	//���� ��
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			q = new PriorityQueue<>((a, b) ->  {
				return a[2] - b[2];	//�Է� ���� ���ڸ� �������� �����ϱ�
			});
			max = 0;
			startNumber = Integer.MAX_VALUE;
			
			//�Է� �ޱ�
			N = Integer.parseInt(in.readLine());
			map = new int[N+2][N+2];
			for(int y = 1; y <= N; y++) {
				st = new StringTokenizer(in.readLine(), " ");
				for(int x = 1; x <= N; x++) {
					number = Integer.parseInt(st.nextToken());
					map[y][x] = number;
					q.add(new int[] {y, x, number});	//ť�� �ֱ�
				}
			}
			visited = new boolean[N+2][N+2];
			//�Է� �ޱ� ����
			
			//Ȯ�� �غ� �͵��� ���������� -> ���ڰ� ���� ������ Ȯ���غ���
			while( !q.isEmpty() ) {
				//������
				tmp = q.poll();
				y = tmp[0];
				x = tmp[1];
				number = tmp[2];
				
				if(visited[y][x]) {	//�̹� ���� ���̸�
					continue;	//���� �˻� ���Ѵ�
				}
				count = 1;	//���� ����
				
				Loop: while(true) {
					visited[y][x] = true;	//�湮�ߴٰ� üũ
					
					//4���� Ž��
					for(int i = 0; i < 4; i++) {
						nextY = y + dy[i];
						nextX = x + dx[i];
						if( !visited[nextY][nextX] && 	//�湮 �ߴ� ���� �ƴϸ鼭
							(map[nextY][nextX] - map[y][x]) == 1) {	//���� �� ���� 1��ŭ ũ����
							y = nextY;	//��ġ �̵�
							x = nextX;
							count++;	//ī��Ʈ ����
							continue Loop;	//���� 4���� Ž�� ���� -> ���� ��ҷ� �̵�
						}
					}
					if(count > max) {	//���ο� ���� ������ ���� ã�� ����
						max = count;
						startNumber = number;
					}
					
					break;	//4���� Ž���� �ϰ� break�� �� �Ǿ��� ���� -> �� ���� ���ٴ� ���̴�
				}
				
			}
			
			sb.append("#").append(test_case).append(" ").append(startNumber).append(" ").append(max).append("\n");
			
		}
		System.out.println(sb.toString());
	}
}
