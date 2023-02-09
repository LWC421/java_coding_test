import java.util.Scanner;

public class B2023 {
	static StringBuilder sb = null;
	static int N;
	static int current;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		sb = new StringBuilder("");

		//몇개 까지 해야되는지 확인
		N = sc.nextInt();
		current = 0;

		//1자리수이면 귀찮으니까 바로 찍어주기
		if(N == 1) {
			sb.append(2).append("\n");
			sb.append(3).append("\n");
			sb.append(5).append("\n");
			sb.append(7).append("\n");

			System.out.println(sb);
			return;
		}
		
		perm(0);
		System.out.println(sb.toString());
	}

	//다음 순열 찾기
	public static void perm(int count) {
		//1보다 클 때만 소수 검사 실시
		if(current > 1) {
			for(int i = 2; i <= Math.sqrt(current); i++) {
				if( (current % i) == 0) {	//합성수이면
					return;	//그만
				}
			}
		}
		
		if( count == N) {	//N자리수가 맞으면
			sb.append(current).append("\n");	//소수이면서 N자리수이다
			return;
		}
		
		for(int i = 0; i < 10; i++) { // 0 ~ 9를 붙여나가기
			current = current * 10 + i;	//이번 숫자 조합하기
			
			if(current > 1) {	//소수일 가능성이 있을때만 검사
				perm(count + 1);	//다음 자리수로 탐색
			}
			current = (current - i) / 10;	//원본 숫자로 돌리기
		}
	}
}
