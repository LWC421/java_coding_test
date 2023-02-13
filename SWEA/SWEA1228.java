import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class SWEA1228 {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int T = 10;
		
		int N;		 									//원본 암호문의 길이
		List<String> originals = new LinkedList<>();	//원본 암호문
		int M;											//명령어 개수
		
		int x;											//x의 위치 다음
		int y;											//y개 만큼
		
		
		StringTokenizer st = null;
		StringTokenizer st2 = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			StringBuilder sb = new StringBuilder("");
			N = Integer.parseInt(in.readLine());
			st = new StringTokenizer(in.readLine());
			originals = new LinkedList<String>();
			
			for(int i = 0; i < N; i++) {
				originals.add(st.nextToken());
			}
			
			M = Integer.parseInt(in.readLine());
			
			//명령어 돌기
			st = new StringTokenizer(in.readLine(), "I");
			for(int i = 0; i < M; i++) {
				st2 = new StringTokenizer(st.nextToken(), " ");
				x = Integer.parseInt(st2.nextToken());
				y = Integer.parseInt(st2.nextToken());
				if(x < 10) {	//10보다 작을때만
					for(int j = x; j < x+y && j < 10; j++) {
						originals.add(j, st2.nextToken());
					}
				}
			}
			sb.append("#").append(test_case).append(" ");
			
			for(int i = 0; i < 10; i++) {
				sb.append(originals.get(i)).append(" ");
			}
			sb.append("\n");
			
			System.out.println(sb);
		}
	}
}
