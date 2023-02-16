import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA6808 {
	static int[] cards = new int[9];				//인영이가 내는 카드의 정보
	static int[] remain = new int[9];				//인영이가 선택하고 남은 카드
	static int[] select = new int[9];				//현재 내 카드 정보
	static boolean[] isSelected = new boolean[9];	//선택 정보
	
	static int win;		//내가 몇 번 이겼는지
	static int loose;	//내가 몇 번 졌는지
	
	public static void main(String[] args) throws Exception{
		BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in= new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			win = 0;
			loose = 0;
			
			//입력 받기
			st = new StringTokenizer(in.readLine(), " ");
			for(int i = 0; i < 9; i++) {
				cards[i] = Integer.parseInt(st.nextToken());
			}
			//입력 종료
			
			//인영이가 선택하지 않은 카드 보기
			int index = 0;
			Loop : for(int i = 1; i <= 18; i++) {
				for(int j = 0; j < 9; j++) {
					if(i == cards[j]) continue Loop;	//해당 카드는 인영이가 선택한거다
				}
				remain[index++] = i;	//이 카드는 인영이가 선택하지 않은 거다
			}
			
			perm(0);
			
			sb.append("#").append(test_case).append(" ").append(loose).append(" ").append(win).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public static void perm(int count) {
		if(count == 9) {
			//다 선택한 경우
			int myScore = 0;	//내 점수
			int yourScore = 0;	//니 점수
			
			int index = 0;
			for(int s: select) {	//선택 정보 불러오기
				if(remain[s] > cards[index]) {	//내가 선택한게 더 크면
					myScore += (remain[s] + cards[index++]);
				}
				else {
					yourScore += (remain[s] + cards[index++]);
				}
			}
			
			if(myScore > yourScore) {
				win++;
			}
			else if(myScore < yourScore) {
				loose++;
			}
			
			return;
		}
		
		for(int i = 0; i < 9; i++) {
			//카드 선택하기
			if(isSelected[i]) continue;	//선택된 카드면 스킵
			select[count] = i;	//해당 i번째 카드 선택했다고 하기
			isSelected[i] = true;
			perm(count+1);
			isSelected[i] = false;
		}
	}
}
