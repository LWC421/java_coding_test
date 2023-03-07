import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class B15961 {
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int numDish = Integer.parseInt(st.nextToken());		//접시의 개수
		int numSushi = Integer.parseInt(st.nextToken());	//초밥의 가짓수
		int length = Integer.parseInt(st.nextToken());		//연속해서 먹는 개수
		int coupoun = Integer.parseInt(st.nextToken());		//쿠폰 번호
		
		int result = 0;	//결과값
		
		int[] dishes = new int[numDish];
		
		//접시 입력받기
		for(int i = 0; i < numDish; i++) {
			dishes[i] = Integer.parseInt(in.readLine());
		}
		//입력 종료
		
		Map<Integer, Integer> map = new HashMap<>();
		
		map.put(coupoun, 1);	//쿠폰으로 먹는건 그냥 추가해놓기
		
		for(int i = 0; i < length; i++) {
			map.computeIfPresent(dishes[i], (k, v) -> v+1);			//있을 경우 값 늘리기
			map.computeIfAbsent(dishes[i], v -> new Integer(1));	//없으면 1 넣기
		}

		result = Math.max(result, map.size());	//몇 종류나 되는지 확인
		
		//remove번째에 있는건 삭제
		//add번째에 있는건 넣을 예정
		int remove = 0;
		int add = length;
		
		while(add < numDish) {
			map.computeIfPresent(dishes[add], (k, v) -> v+1);	//있을 경우 +1 늘려주기
			map.computeIfAbsent(dishes[add], v -> new Integer(1));	//없으면 넣어주기
			
			map.computeIfPresent(dishes[remove], (k, v) -> {	//빠지는애는 빼주기
				if(v == 1) return null;	//1이였으면 삭제해야한다
				else {
					return v-1;	//숫자 하나 줄이기
				}
			});
			
			result = Math.max(result, map.size());	//최대값 갱신
//			System.out.println(map);
			
			add++;	//늘리기
			remove++;
		}
		
		//한바퀴 돌앗으면 add는 0번째에 있다
		add = 0;
		
		//회전초밥이므로 필요한 길이만큼 회전해야한다
		for(int i = 0; i < length; i++) {
			map.computeIfPresent(dishes[add], (k, v) -> v+1);	//있을 경우 +1 늘려주기
			map.computeIfAbsent(dishes[add], v -> new Integer(1));	//없으면 넣어주기
			
			map.computeIfPresent(dishes[remove], (k, v) -> {	//빠지는애는 빼주기
				if(v == 1) return null;	//1이였으면 삭제해야한다
				else {
					return v-1;	//숫자 하나 줄이기
				}
			});
			
			result = Math.max(result, map.size());	//최대값 갱신
//			System.out.println(map);
			
			add++;	//늘리기
			remove++;
		}


		
		System.out.println(result);
	}

}
