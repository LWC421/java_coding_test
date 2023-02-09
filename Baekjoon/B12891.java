import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B12891 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		int S = Integer.parseInt(st.nextToken());	//문자열 길이
		int P = Integer.parseInt(st.nextToken());	//부분문자열의 길이
		
		String dna = in.readLine();

		st = new StringTokenizer(in.readLine(), " ");
		//각 문자에 대한 최소 개수 받아오기
		int A = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		int G = Integer.parseInt(st.nextToken());
		int T = Integer.parseInt(st.nextToken());

		//현재 포함된 문자의 개수 
		int curA = 0;
		int curC = 0;
		int curG = 0;
		int curT = 0;
		
		//현재 몇개나 비밀번호로 적합한지
		int count = 0;

		//현재 문자가 무엇인지
		char cur = '\u0000';
		//P길이 만큼 우선 A, C, G, T가 몇개씩 있는지 확인해보기
		for(int i = 0; i < P; i++) {
			cur = dna.charAt(i);
			if(cur == 'A') curA++;
			else if(cur == 'C') curC++;
			else if(cur == 'G') curG++;
			else if(cur == 'T') curT++;
		}
		
		//비밀번호로 적합하면
		if(curA >= A && curC >= C && curG >= G && curT >= T) {
			count++;
		}
		
		//이전 문자가 무엇인지
		char prev = '\u0000';
		//P부터 마지막 글자까지 확인
		for(int i = P; i < S; i++) {
			prev = dna.charAt(i-P);
			cur = dna.charAt(i);

			//이전거는 빼주기
			if(prev == 'A') curA--;
			else if(prev == 'C') curC--;
			else if(prev == 'G') curG--;
			else if(prev == 'T') curT--;
			
			//이번거는 더해주기
			if(cur == 'A') curA++;
			else if(cur == 'C') curC++;
			else if(cur == 'G') curG++;
			else if(cur == 'T') curT++;
			
			//비밀번호로 적합하면
			if(curA >= A && curC >= C && curG >= G && curT >= T) {
				count++;
			}
		}
		
		System.out.println(count);
	}

}


