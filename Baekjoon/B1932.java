import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1932 {

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		int length = Integer.parseInt(in.readLine());	//맵의 길이
		int[][] map = new int[length][length];			//맵 정보

		//맵 입력 받기
		for(int i = 0; i < length; i++) {
			st = new StringTokenizer(in.readLine());
			for(int j = 0; j <= i; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int curY = 1;
		while(curY < length) {
			//값을 누적하면서 내려가기
			
			//맨왼쪽은 항상 위에서 왼쪽으로 가는 경우밖에 없다
			map[curY][0] += map[curY-1][0];
			
			for(int curX = 1; curX < curY; curX++) {
				//중간인 경우 -> 둘중 선택해서 더하기
				map[curY][curX] += Math.max(map[curY-1][curX], map[curY-1][curX-1]);
			}
			
			//맨 오른쪽은 항상 위에서 오른쪽으로 가는 경우밖에 없다
			map[curY][curY] += map[curY-1][curY-1];
			
			curY++;
		}

		//맨 밑에 돌면서 확인하기
		int max = 0;	//최대값 저장
		int y = length -1;
		for(int x = 0; x < length; x++) {
			max = Math.max(map[y][x], max);
		}
		
		System.out.println(max);
	}
}
