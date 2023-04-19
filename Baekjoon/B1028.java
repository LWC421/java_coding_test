import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1028{

	//다이아 모양으로 탐색
	final static int[] dy = {1, 1, -1, -1};
	final static int[] dx = {1, -1, -1, 1};
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
		StringTokenizer st = new StringTokenizer(in.readLine());
		int yLength = Integer.parseInt(st.nextToken());
		int xLength = Integer.parseInt(st.nextToken());

		//diaMap은 다이아이면 true로 표시하자
		boolean[][] diaMap = new boolean[yLength][xLength];
		
		for(int y = 0; y < yLength; y++) {
			char[] row = in.readLine().toCharArray();
			for(int x = 0; x < xLength; x++) {
				//다이아이면 true로 표시하자
				diaMap[y][x] = row[x] == '1' ? true : false;
			}
		}
		//입력 종료
		
		int tmp = Math.min(yLength, xLength);
		tmp += tmp % 2;	//홀수이면 짝수로 변경하기
		
		int maxLength = tmp /= 2;	//다이아몬드의 최대길이를 정해놓자
		

		for(int length = maxLength; length > 0; length--) {
			//길이를 하나씩 줄여가면서 찾아보자
			// 다이아몬드의 상단을 검사할 것이다
			int endY = yLength - ((length-1) * 2) - 1;
			int startX = length-1;
			int endX = xLength - length;
//			System.out.printf("%d : %d, %d ~ %d\n", length, endY, startX, endX);
			
			for(int y = 0; y <= endY; y++) {
				//y축으로 내려가면서
				XLoop: for(int x = startX; x <= endX; x++) {
					//x축으로 오른쪽으로 간다
					
					int nY = y;
					int nX = x;	//여기 위치부터 시작해서
					if(!diaMap[nY][nX]) continue XLoop; 
					for(int d = 0; d < 4; d++) {
						//다이아의 한 변씩
						for(int l = 1; l < length; l++) {
							//한칸씩 가자
//							System.out.printf("[%d, %d]\n", nY, nX);
							if(!diaMap[nY][nX]) continue XLoop; 
							nY += dy[d];
							nX += dx[d];
						}
					}
					//4변 검사를 끝내고나면 여기로 온다
					System.out.println(length);
					return;
				}
			}
		}
		
		//1이상의 다이아가 없으면 0을 출력하자
		System.out.println(0);
	}
}