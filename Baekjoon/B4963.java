import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B4963 {
	static int w;				//������ �ʺ�
	static int h;				//������ ����
	
	static boolean[][] map;		//����, false -> �ٴ�(������), true -> ��
	static boolean[][] visited;	//�湮üũ, fasle -> �̹湮, true -> �湮
	
	static boolean target;		//�Է� ������ ��� �� ��
	static int remainVisited;	//���� �湮 Ƚ��
	static Queue<int[]> q;		//BFS�� ���� ť
	static int[] tmp;			//ť���� ���� �� ����� ��
	
	static int curY;	//���� ��ġ
	static int curX;	//���� ��ġ
	static int count;	//�湮 Ƚ�� ǥ��
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		while(true) {
			st = new StringTokenizer(in.readLine(), " ");
			w = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());
			q = new ArrayDeque<>();	//ť �ʱ�ȭ
			count = 0;				//ī��Ʈ �ʱ�ȭ
			
			//�Է��� ���� 
			if(w == 0) {
				break;
			}
			
			//�� �ʱ�ȭ
			map = new boolean[h+2][w+2];
			visited = new boolean[h+2][w+2];
			remainVisited = h*w;	//�ϴ� h*w��ŭ �湮üũ �غ��� �Ѵٰ� ǥ��
			for(int y = 1; y <= h; y++) {
				st = new StringTokenizer(in.readLine());
				//true / false�� �ʱ�ȭ�ϱ�
				for(int x = 1; x <= w; x++) {
					target = st.nextToken().equals("1") ? true : false;
					map[y][x] = target;
					visited[y][x] = !target;			//�ٴ��� ��� �湮�ߴٰ� ǥ���ع�����
					remainVisited -= target ? 0 : 1;	//�ٴ��� ��� �湮�ؾ� �Ǵ°ſ��� 1����
				}
			}
				
			//Ȯ�� �����غ���
			Loop: for(int y = 1; y <= h; y++) {
				for(int x = 1; x <= w; x++) {
					if(visited[y][x]) {	//�̹� �湮�߾�����
						continue;
					}
					bfs(y, x);	//�湮���� �����Ƿ� üũ�ϱ�
					count++;
					
					if(remainVisited == 0) {	//�� üũ������
						break Loop;	//��ȸ ����
					}
				}
			}
			sb.append(count).append("\n");
		}
		System.out.println(sb.toString());
	}
	
	public static void bfs(int curY, int curX) {
		q.add(new int[] { curY, curX });	//�ϴ� ������ġ �ֱ�
		
		while( !q.isEmpty() ) {
			tmp = q.poll();
			curY = tmp[0];
			curX = tmp[1];
			
			if(visited[curY][curX]) {	//�̹� �湮 �� ���̸� �ѱ��
				continue;
			}
			else{ //�湮 ������ ������
				remainVisited--;	//���� �湮 Ƚ�� ���̱�
				visited[curY][curX] = true;	//�湮�ߴٰ� ǥ��
			}
			
			
			//8���� Ž�� ����
			for(int y = -1; y <= 1; y++) {
				for(int x = -1; x <= 1; x++) {
					if(x == 0 && y == 0) continue;	//�ڱ� �ڽ� ��ġ�̸� �Ȱ���
					if( !visited[curY + y][curX + x] &&	//�湮 �� ���� �����鼭
							map[curY + y][curX + x]) {	//�湮�� �����ϸ�(���̸�)
						q.add(new int[] {curY+y, curX+x});	//�ش� ��� �湮�ϱ�
					}
				}
			}
		}
	}
}
