import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class B1958 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str1 = in.readLine();
		String str2 = in.readLine();
		String str3 = in.readLine();

		int length1 = str1.length() + 1;
		int length2 = str2.length() + 1;
		int length3 = str3.length() + 1;
	
		int[][][] map = new int[length1+1][length2+1][length3+1];
		
		char c1, c2, c3;
		for(int s1 = 1; s1 < length1; s1++) {
			c1 = str1.charAt(s1-1);
			for(int s2 = 1; s2 < length2; s2++) {
				c2 = str2.charAt(s2-1);
				for(int s3 = 1; s3 < length3; s3++) {
					c3 = str3.charAt(s3-1);
					if(c1 == c2 && c1 == c3) {
						//같을 경우
						map[s1][s2][s3] = map[s1-1][s2-1][s3-1] + 1;
					}
					else {
						//다를 경우
						map[s1][s2][s3] = Math.max( Math.max(map[s1-1][s2][s3], map[s1][s2-1][s3]), map[s1][s2][s3-1]);
					}
				}	
			}
		}
		
		System.out.println(map[str1.length()][str2.length()][str3.length()]);
	}
}
