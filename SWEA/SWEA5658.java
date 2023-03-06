import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class SWEA5658 {
	
	static int LSB_NUMBER;
	
	public static void main(String[] args) throws Exception {
		// 0 ~ F의 16진수 적혀있다
		// 시계방향으로 돌린다 -> 숫자가 시계방향으로 한칸 회전
		// 각 변에는 동일한 개수의 숫자가 있다
		// 시계 방향 순으로 높은 자리 숫자에 해당하며 하나의 변을 모두 연결한 것이 하나의 숫자이다
		// 보물 상자에 적힌 숫자로 만들 수 있는 모든 수 중, K번째로 큰 수를 10진수로 만든 것이 비밀번호다
		// N개의 숫자가 입력으로 주어진다, 비밀번호를 찾아라
		// N은 4의 배수, [8 ~ 28]
		
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		int N;				//숫자가 몇개 인지 
		int K;				//몇 번째 수를 찾아야 하는지
		char[] numbers;	//입력된 숫자
		char[][][] combinations;
		TreeSet<Integer> set = null;
		
		String tmp;
		StringTokenizer st = null;
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			LSB_NUMBER = (int)Math.pow(16, (N / 4)-1);	//최상위 값이 얼마인지 저장
			set = new TreeSet<>((a, b) ->  {
				return -Integer.compare(a, b);
			});
			
			numbers = new char[N];
			tmp = in.readLine();
			for(int i = 0; i < N; i++) {
				//한자리의 16진수 수 char형으로 입력받기
				numbers[i] = tmp.charAt(i);
			}
			//입력 종료
			
			
			combinations = combination(numbers, N);
			
			charArrayToInt(combinations, N, set);
			int index = 1;
			int result = -1;
			for(Integer num: set) {
				if(index == K) {
					//K번째 숫자이면
					result = num;
					break;
				}
				index++;
			}
			
			sb.append(String.format("#%d %d\n", test_case, result));
		}
		
		System.out.println(sb.toString());
	}
	
	//숫자들과 N을 입력으로 받는다
	public static char[][][] combination(char[] numbers, int N) {
		int length = N / 4;	//하나의 숫자열은 몇개의 숫자로 구성되는지, ex) N == 12 -> length == 3
		char[][][] numberArr = new char[length][4][length];	//항상 4개의 숫자열이 생기며 각각은 length길이만큼이다
		for(int i = 0; i < length; i++) {
			//0회전부터 length-1회전까지 살펴본다
			int index = i;	//i부터 시작해서
			for(int c = 0; c < 4; c++) {
				//항상 4개의 숫자열이 생성된다
				for(int j = 0; j < length; j++) {
					numberArr[i][c][j] = numbers[index];	//해당 숫자를 가져와서 -> i회전의 c번째 문자열에 j번째 문자로 넣는다는 뜻이다
					index = (index+1) % N;	//Rotate되는거 생각하기
				}
			}
		}
		//0 ~ length-1회전까지의 숫자열들이 생성된다
//		for(char[][] number: numberArr) {
//			for(char[] num: number) {
//				System.out.println(Arrays.toString(num));
//			}
//		}
		
		return numberArr;
	}
	
	public static void charArrayToInt(char[][][] numberArr, int N, TreeSet<Integer> set) {
		int length = N / 4;	//하나의 문자열은 length길이만큼이다
		
		int multValue;
		int resultNumber;
		
		for(int rotate = 0; rotate < length; rotate++) {
			for(int combi = 0; combi < 4; combi++) {
				//하나의 숫자열에 대해
				multValue = LSB_NUMBER;	//가장 높은 자리의 값 넣기
				resultNumber = 0;	//숫자 초기화
				for(char c: numberArr[rotate][combi]) {
					//하나의 숫자열에서 문자를 하나씩 가져와서
					resultNumber += charToInt(c) * multValue;	//16진수인거 계산해주기
					multValue /= 16;	//다음자리수는 16만큼 나눠주면 된다
				}
				set.add(resultNumber);	//문자열로부터 생성된 숫자 넣어주기
			}
		}
	}
	
	public static int charToInt(char c) {
		if(c >= 'A') {
			return c - 'A' + 10;
		}
		else {
			return c - '0';
		}
	}
}
