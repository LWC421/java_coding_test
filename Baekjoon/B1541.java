import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class B1541 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = null;
		
		char[] input = in.readLine().toCharArray();
		List<String> inputs = new ArrayList<>();
		
		int left, right;
		left = 0;
		for(int i = 0; i < input.length; i++) {
			if(input[i] == '-' || input[i] == '+') {
				right = i;
				sb = new StringBuilder("");
				for(int j = left; j < right; j++) {
					sb.append(input[j]);
				}
				
				inputs.add(sb.toString());	//숫자 넣기
				inputs.add(String.valueOf(input[right]));	//연산자 넣기
				left = right+1;
			}
		}
		//마지막 숫자는 안 넣어지므로 따로 넣기
		sb = new StringBuilder("");
		for(int i = left; i < input.length; i++) {
			sb.append(input[i]);
		}
		inputs.add(sb.toString());
		
		//입력 전처리 끝
		

		//마이너스인 곳 인덱스 넣기
		List<Integer> minusIndex = new ArrayList<>();
		for(int i = 0, limit = inputs.size(); i < limit; i++) {
			if(inputs.get(i).equals("-")) {
				minusIndex.add(i);
			}
		}
		
		List<Integer> numbers = new ArrayList<>();
		left = 0;
		for(int index: minusIndex) {
			right = index;
			
			//플러스인 곳들 다 더해주기
			int number = 0;
			for(int i = left; i < right; i++) {
				if(inputs.get(i).equals("+")) {
					continue;	//연산자면 무시
				}
				number += Integer.parseInt(inputs.get(i));
			}
			numbers.add(number);
			
			left = right+1;
		}
		
		//덜된곳 더해주기
		int number = 0;
		for(int i = left, limit = inputs.size(); i < limit; i++) {
			if(inputs.get(i).equals("+")) {
				continue;	//연산자 무시
			}
			number += Integer.parseInt(inputs.get(i));
		}
		numbers.add(number);
		
		//+연산자 계산된 결과값들 전부다 마이너스하기
		int result = numbers.get(0);
		for(int i = 1, limit= numbers.size(); i < limit; i++) {
			result -= numbers.get(i);
		}
		
		System.out.println(result);
	}
}
