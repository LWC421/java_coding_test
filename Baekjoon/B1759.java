import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class B1759 {
	
	static int cryptLength;		//암호문 길이
	static int candiLength;		//후보의 개수
	static char[] candis;		//후보 알파벳들
	static List<String> crypts;	//가능한 암호문들

	public static void main(String[] args) throws Exception{
		//암호는 서로 다른 L개의 알파벳 소문자
		//최소 한개 모음, 최소 두개의 자음
		//암호는 오름차순이다
		//C가지 알파벳을 조교들이 암호로 사용했을 것이다
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		cryptLength = Integer.parseInt(st.nextToken());
		candiLength = Integer.parseInt(st.nextToken());
		
		candis = new char[candiLength];
		crypts = new ArrayList<>();
		
		//가능한 알파벳 입력 받기
		st = new StringTokenizer(in.readLine(), " ");
		for(int i = 0; i < candiLength; i++) {
			candis[i] = st.nextToken().charAt(0);
		}
		
		//모든 입력 종료
		
		Arrays.sort(candis);	//입력받은 알파벳 정렬
		
		combi(0, 0, "");

		StringBuilder sb = new StringBuilder("");
		for(String s: crypts) {
			sb.append(String.format("%s\n", s));
		}
		System.out.println(sb.toString());
	}
	
	//조합을 생성
	public static void combi(int count, int start, String str) {
		if(count == cryptLength) {
			//조합 성공하면
			
			int numMoeum = 0;	//모음 개수
			int numZaeum = 0;	//자음 개수
			
			char ch;
			for(int i = 0; i < cryptLength; i++) {
				ch = str.charAt(i);
				if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
					//모음일 경우
					numMoeum++;
				}
				else {
					numZaeum++;
				}
			}
			
			if(numMoeum >= 1) {
				//최소 한 개의 모음
				if(numZaeum >= 2) {
					//최소 두 개의 자음
					crypts.add(str);	//암호문으로 가능하다
				}
			}
			
			return;
		}
		
		for(int i = start; i < candiLength; i++) {
			combi(count+1, i+1, str + candis[i]);	//문자 붙여서 다음으로
		}
	}
}
