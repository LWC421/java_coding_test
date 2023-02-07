import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class SWEA1210 {
	public static int[][] map = new int[100][100];
	public static int curY;
	public static int curX;
	
	public static void main(String[] args) throws IOException {
		
		
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		for(int test_case = 1; test_case <= 10; test_case++) {
			curY = 99;	//현재 위치 맨 밑으로 초기화
			
			in.readLine();	//첫번째 버리기
			
			for(int i = 0; i < 100; i++) {	//맵 생성하기
				map[i] = Stream.of(in.readLine().split(" ")).mapToInt(s -> Integer.parseInt(s)).toArray();
			}
			
			for(int i = 0; i < 100; i++) {
				if(map[99][i] == 2) {	//현재 x좌표 찾기
					curX = i;
					break;
				}
			}
			
			for(curY = 99; curY >= 0; curY--) {
				boolean moved = false;
				while(curX >0 && map[curY][curX-1] == 1) {	//왼쪽으로 가야 되는 경우이면
					curX--;
					moved = true;
				}
				if( !moved ) {	//왼쪽으로 갔으면 오른쪽으로는 가면 안된다
					while(curX < 99 && map[curY][curX+1] == 1) {	//오른쪽으로 가야 되는 경우이면
						curX++;
					}
				}
			}
			
			System.out.printf("#%d %d\n", test_case, curX);
			
		}
	}
}
