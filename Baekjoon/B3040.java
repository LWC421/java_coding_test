import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B3040 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int[] numbers = new int[9];
		int number = 0;
		int sum = 0;
		
		for(int i = 0; i < 9; i++) { 
			number = Integer.parseInt(in.readLine());
			numbers[i] = number;
			sum += number;
		}
		
		for(int i = 0; i < 8; i++) {
			for(int j = i+1; j <9; j++) {
				if(i == j) {	//같은 경우에는 굳이 검사 X
					continue;
				}
				
				if(sum - numbers[i] - numbers[j] == 100) {	//찾았다
					for(int t = 0; t < 9; t++) {
						if(t == i || t == j) {
							continue;
						}
						System.out.println(numbers[t]);
					}
					return;	//종료
				}
			}
		}
	}
}
