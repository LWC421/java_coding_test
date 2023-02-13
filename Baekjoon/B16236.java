import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B16236 {
    static int scale = 2;    //현재 상어의 크기
    static int[][] map;
    static int eat = 0;		//현재 먹은 고기
    static int moved = 0;
    
    //상어의 Y, X값
    static int curY;
    static int curX;
    
    public static void main(String[] args) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        // N : 2 ~ 20
        int N = Integer.parseInt(in.readLine());
        
        //맵 초기화
        StringTokenizer st = null;
        map = new int[N+2][N+2];
        int num;
        //맵 생성하기
        for(int y = 1; y <= N; y++) {
            st = new StringTokenizer(in.readLine());
            for(int x = 1; x <= N; x++) {
                num = Integer.parseInt(st.nextToken());
                map[y][x] = num;
                if(num == 9) {    //상어위치 받아오기
                    curY = y;
                    curX = x;
                    map[y][x] = 0;
                }
            }
        }
        //맵에 벽치기
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
        	if(a[2] != b[2]) {	//count에 따라 sort
        		return a[2] - b[2];
        	}
        	else if(a[0] != b[0]) {	//y축에 따라 sort
        		return a[0] - b[0];
        	}
        	return a[1] - b[1];
        });
        
        q.add(new int[] {curY, curX, 0});
        
        int y;
        int x;
        int count;
        int[] tmp;
        
        //위, 왼쪽, 오른쪽, 아래 순으로 탐색
        int[] dy = {-1, 0, 0, 1};
        int[] dx = {0, -1, 1, 0};
        int i;
        
        while( !q.isEmpty() ) {
        	tmp = q.poll();
        	y = 	tmp[0];
        	x = 	tmp[1];
        	count = tmp[2];
        	
        	if(visited[y][x] == true) {	//방문이 되어있으면
        		continue;
        	}
        	
        	visited[y][x] = true;	//방문했다고 표시
        
        	//먹을 수 있으면
        	if(map[y][x] > 0 && map[y][x] < scale) {
        		eat++;
        		map[y][x] = 0;	//현재 내위치
        		
        		if(eat == scale) {	//필요한 먹이수가 되었으면
        			eat = 0;	//먹은 물고기 초기화
        			scale++;	//상어 크기 키우기
        		}
        		
       			for(int a = 1; a <= N; a++) {
    				for(int b = 1; b <= N; b++) {
    					visited[a][b] = false;	//다시 방문 가능하다고 표시하기
    				}
    			}
       			moved += count;	//움직인 횟수 늘리기
       			
       			//큐도 다시 시작
       			q.clear();
       			count = 0;
        	}
        	
        	
        	
        	//4방위 탐색
        	for(i = 0; i < 4; i++) {
        		if( map[y+dy[i]][x+dx[i]] != -1 && map[y+dy[i]][x+dx[i]] <= scale && visited[y+dy[i]][x+dx[i]] == false) {	//이동이 가능하면
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