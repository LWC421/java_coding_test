import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class B1786 {

	public static void main(String[] args) throws Exception{
		// P는 패턴 -> 길이 m
		// T는 텍스트 -> 길이 n
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		char[] text = in.readLine().toCharArray();
		char[] pattern = in.readLine().toCharArray();
		
		int[] pi = getPI(pattern);
//		System.out.println(Arrays.toString(pi));

		int textLength = text.length;
		int patternLength = pattern.length;
		
		int count = 0;	//맞는 갯수 세기
		int cur = 0;
		
		List<Integer> indices = new LinkedList<>();
		
		for(int last = 0; last < textLength; last++) {
//			System.out.printf("%d start(%c)\n", last, text[last]);
//			System.out.println(cur);
			while(cur > 0 && text[last] != pattern[cur]) {
//				System.out.printf("%c <-> %c\n", text[last], pattern[cur]);
				cur = pi[cur-1];
			}
			
//			System.out.println(cur);
			
			if(text[last] == pattern[cur]) {
				if(cur == patternLength-1) {
					indices.add(last - patternLength + 2);
					count++;
					cur = pi[cur];
				} else {
					cur++;
				}
			}
			
//			System.out.println("------------------------");
		}
		
		sb.append(count).append("\n");
		for(int ind: indices) {
			sb.append(ind).append(" ");
		}
		System.out.println(sb.toString());
		
	}
	
	public static int[] getPI(char[] pattern) {
		int length = pattern.length;
		int[] pi = new int[length];
		pi[0] = 0;
		
		int cur = 0;
		for(int last = 1; last < length; last++) {
			while(cur > 0 && pattern[last] != pattern[cur]) {
				cur = pi[cur-1];
			}
			
			if(pattern[last] == pattern[cur]) {
				cur++;
				pi[last] = cur;
			}
		}
		
		
		return pi;
	}
}
