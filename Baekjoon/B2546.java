import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2546 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			in.readLine();		//빈줄 비우기
			st = new StringTokenizer(in.readLine());
			
			int numC = Integer.parseInt(st.nextToken());		//c 수강생
			int numGyeong = Integer.parseInt(st.nextToken());	//경제학 수강생
			
			double[] cScores = new double[numC];
			double avgC = 0;
			double num;
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < numC; i++) {
				num = Double.parseDouble(st.nextToken());
				cScores[i] = num;
				avgC += num;
			}

			st = new StringTokenizer(in.readLine());
			double[] gyeongScores = new double[numGyeong];
			double avgGyeong = 0;
			for(int i = 0; i < numGyeong; i++) {
				num = Double.parseDouble(st.nextToken());
				gyeongScores[i] = num;
				avgGyeong += num;
			}
			
			//평균으로 변환
			avgC /= numC;
			avgGyeong /= numGyeong;
			
			int count = 0;
			for(int i = 0; i < numC; i++) {
				if(cScores[i] < avgC && cScores[i] > avgGyeong) {
					count++;
				}
			}
			sb.append(count).append("\n");
		}
		System.out.println(sb.toString());
	}
}
