import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class B2178 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		//N�� ���� ����
		//M�� ���� ����
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[][] map = new int[N][M];
		boolean[][] visited = new boolean[N][M];
		
		//�� �����ϱ�
		for(int y = 0; y < N; y++) {
			String s = in.readLine();
			for(int x = 0; x < M; x++) {
				map[y][x] = Integer.parseInt(String.valueOf(s.charAt(x)));
			}
		}
		
		// [y, x, count]�� ���� 2���� ������ �迭�� ť�� ����� ��
		Queue<Data> queue = new LinkedList<>();
		queue.add(new Data(0, 0, 1));
		
		
		int y = 0;
		int x = 0;
		int count = 0;
		Data current = null;
		while( !queue.isEmpty() ) {	//������� ������ ���
			
			//ť���� ������ ����
			current = queue.poll();
			y     = current.y;
			x     = current.x;
			count = current.count;
			
			if(visited[y][x] == true) {	//�ٸ� ������ �̹� �湮������
				continue;
			}
			
			visited[y][x] = true;	//�湮 �ߴٰ� ǥ��
			
			if(y == N-1 && x == M-1) {	//������ ���
				System.out.println(count);	//�� ������ ���� ��� ������
				return;
			}
			
			//���� �� �� ������
			if(y > 0 && map[y-1][x] == 1 && !visited[y-1][x]) {
				queue.add(new Data(y-1, x, count+1));
			}
			//�Ʒ��� �� �� ������
			if(y < N-1 && map[y+1][x] == 1 && !visited[y+1][x]) {
				queue.add(new Data(y+1, x, count+1));
			}
			//�������� �� �� ������
			if(x > 0 && map[y][x-1] == 1 && !visited[y][x-1]) {
				queue.add(new Data(y, x-1, count+1));
			}
			//���������� �� �� ������
			if(x < M-1 && map[y][x+1] == 1 && !visited[y][x+1]) {
				queue.add(new Data(y, x+1, count+1));
			}
		}
	}
}

class Data{
	int y;
	int x;
	int count;
	Data(int y, int x, int count){
		this.y = y;
		this.x = x;
		this.count = count;
	}
}
