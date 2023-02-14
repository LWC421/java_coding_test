import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B16236 {
    static int scale = 2;    //���� ����� ũ��
    static int[][] map;
    static int eat = 0;		//���� ���� ���
    static int moved = 0;
    
    //����� Y, X��
    static int curY;
    static int curX;
    
    public static void main(String[] args) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        // N : 2 ~ 20
        int N = Integer.parseInt(in.readLine());
        
        //�� �ʱ�ȭ
        StringTokenizer st = null;
        map = new int[N+2][N+2];
        int num;
        //�� �����ϱ�
        for(int y = 1; y <= N; y++) {
            st = new StringTokenizer(in.readLine());
            for(int x = 1; x <= N; x++) {
                num = Integer.parseInt(st.nextToken());
                map[y][x] = num;
                if(num == 9) {    //�����ġ �޾ƿ���
                    curY = y;
                    curX = x;
                    map[y][x] = 0;
                }
            }
        }
        //�ʿ� ��ġ��
        for(int y = 0; y <= N+1; y++) {
            map[y][0]     = -1;
            map[y][N+1] = -1;
        }
        for(int x = 0; x <= N+1; x++) {
            map[0][x]     = -1;
            map[N+1][x] = -1;
        }
        
        boolean[][] visited = new boolean[N+2][N+2];
        
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) ->  {
        	if(a[2] != b[2]) {	//count�� ���� sort
        		return a[2] - b[2];
        	}
        	else if(a[0] != b[0]) {	//y�࿡ ���� sort
        		return a[0] - b[0];
        	}
        	return a[1] - b[1];
        });
        
        q.add(new int[] {curY, curX, 0});
        
        int y;
        int x;
        int count;
        int[] tmp;
        
        //��, ����, ������, �Ʒ� ������ Ž��
        int[] dy = {-1, 0, 0, 1};
        int[] dx = {0, -1, 1, 0};
        int i;
        
        while( !q.isEmpty() ) {
        	tmp = q.poll();
        	y = 	tmp[0];
        	x = 	tmp[1];
        	count = tmp[2];
        	
        	if(visited[y][x] == true) {	//�湮�� �Ǿ�������
        		continue;
        	}
        	
        	visited[y][x] = true;	//�湮�ߴٰ� ǥ��
        
        	//���� �� ������
        	if(map[y][x] > 0 && map[y][x] < scale) {
        		eat++;
        		map[y][x] = 0;	//���� ����ġ
        		
        		if(eat == scale) {	//�ʿ��� ���̼��� �Ǿ�����
        			eat = 0;	//���� ����� �ʱ�ȭ
        			scale++;	//��� ũ�� Ű���
        		}
        		
       			for(int a = 1; a <= N; a++) {
    				for(int b = 1; b <= N; b++) {
    					visited[a][b] = false;	//�ٽ� �湮 �����ϴٰ� ǥ���ϱ�
    				}
    			}
       			moved += count;	//������ Ƚ�� �ø���
       			
       			//ť�� �ٽ� ����
       			q.clear();
       			count = 0;
        	}
        	
        	
        	
        	//4���� Ž��
        	for(i = 0; i < 4; i++) {
        		if( map[y+dy[i]][x+dx[i]] != -1 && map[y+dy[i]][x+dx[i]] <= scale && visited[y+dy[i]][x+dx[i]] == false) {	//�̵��� �����ϸ�
        			q.add(new int[] { y+dy[i], x+dx[i], count+1 });
        		}
        	}
        }
//        for(int[] row: map) {
//        	System.out.println(Arrays.toString(row));
//        }
        
        System.out.println(moved);
    }
}