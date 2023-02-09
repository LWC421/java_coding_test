import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B11723 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int M = Integer.parseInt(in.readLine());	//연산해야하는 횟수

		int result = 0;	//결과 저장 

		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;

		String command = null;
		int number = 0;	//입력받는 수

		for(int i = 0; i < M ; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			
			command = st.nextToken();
			
			switch (command.charAt(0)) {	//첫 번째 글자가
			case 'r':{ //remove
				number = Integer.parseInt(st.nextToken()) - 1;
				result = result & (Integer.MAX_VALUE ^ 1 << number);
				break;
			}	
			case 'c':{//check
				number = Integer.parseInt(st.nextToken()) - 1;
				sb.append( ((result & (1 << number)) != 0) ? 1 : 0).append("\n");
				break;
			}	
			case 't':{	//toggle
				number = Integer.parseInt(st.nextToken()) - 1;
				result = result ^ (1 << number);
				break;
			}
			case 'e':{	//empty
				result = 0;
				break;
			}
			case 'a':{	//add 또는 all인 경우이다
				if(command.charAt(1) == 'd') {	//add명령어이다
					number = Integer.parseInt(st.nextToken()) - 1;
					result = result | (1 << number);
				}
				else {	//all명령어이다
					result = Integer.MAX_VALUE;
				}
				break;
			}
			}
		}
		
		System.out.println(sb.toString());
	}
}
