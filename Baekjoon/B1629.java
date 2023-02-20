import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1629 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int A, B, C;
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		System.out.println(pow(A%C, B, C));
	}
	
	public static long pow(int A, int B, int C) {
		if(B == 1) return A;
		
		long result = (pow(A, B/2, C)) % C;
		
		return B%2==0 ? (result*result) % C: ((A * result) % C * result) % C;
	}

}
