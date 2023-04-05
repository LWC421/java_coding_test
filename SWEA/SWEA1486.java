import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1486 {
	
	static int numPerson;
	static int targetHeight;
	static int minHeight;
	static int[] heights;

	public static void main(String[] args) throws Exception {
		// 높이 B인 선반 있다
		// 이 서점에 있는 N명 점원
		// 각 점원의 키가 주어진다
		// 점원들끼리 합해서 탑을 쌓는다
		
		// 탑은 탑에 참여한 점원들의 키의 높이의 합이다
		// 탑의 높이가 B이상인 경우 중 가장 낮은 탑을 알아내려고 한다
		
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		//사람 수 [1 ~ 20]
		
		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			
			numPerson = Integer.parseInt(st.nextToken());
			targetHeight = Integer.parseInt(st.nextToken());
			
			heights = new int[numPerson];
			
			minHeight = 10000 * 20 + 1;
			
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < numPerson; i++) {
				heights[i] = Integer.parseInt(st.nextToken());
			}
			//입력 종료
			
			
			combi(0, 0);
			sb.append(String.format("#%d %d\n", test_case, minHeight - targetHeight));
		}
		
		System.out.println(sb.toString());
	}
	
	public static void combi(int count, int height) {
		if(height >= targetHeight) {
			minHeight = Math.min(height, minHeight);
			return;
		}
		
		if(count == numPerson) {
			return;
		}
		
		combi(count+1, height + heights[count]);	//선택한 경우
		combi(count+1, height);					//선택 안 한 경우
	}
}
