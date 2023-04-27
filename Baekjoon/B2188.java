import java.io.*;
import java.util.*;


public class B2188{

	static int numCow;
	static int numHouse;
	
	static LinkedList<Integer>[] favorites;
	
	static int[] cowToHouse;
	static int[] houseToCow;
	static boolean[] visited;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		numCow = Integer.parseInt(st.nextToken());
		numHouse = Integer.parseInt(st.nextToken());
		
		favorites = new LinkedList[numCow+1];
		
		cowToHouse = new int[numCow+1];
		houseToCow = new int[numHouse+1];
		visited = new boolean[numCow+1];
		
		for(int i = 1; i <= numCow; i++) {
			favorites[i] = new LinkedList<Integer>();
			
			st = new StringTokenizer(in.readLine(), " ");
			int numFavorite = Integer.parseInt(st.nextToken());
			
			for(int j = 0; j < numFavorite; j++) {
				favorites[i].add(Integer.parseInt(st.nextToken()));
			}
		}
		
		//입력 종료
		
		int result = 0;
		for(int i = 1; i <= numCow; i++) {
			if(cowToHouse[i] == 0) {
				//각각의 소가 배정된 축사가 없으면
				for(int j = 1; j <= numCow; j++) {
					//일단 각각의 소들은 사용한 적 없다고 표시
					visited[j] = false;
				}
				if(dfs(i)) {
					//매칭해보고 매칭이 되었으면 결과값을 추가하자
					result++;
				}
			}
		}
		
		System.out.println(result);
	}
	
	public static boolean dfs(int x) {
		List<Integer> houses = favorites[x];
		visited[x] = true;	//해당 소는 점유중이다
		
		for(int h: houses) {
			//h : x소가 가고 싶어하는 축사를 의미한다
			if(houseToCow[h] == 0 || !visited[houseToCow[h]] && dfs(houseToCow[h])) {
				//만약 아무도 사용하고 있지 않다면
				// 또는 
				cowToHouse[x] = h;	//x소는 h에 넣고
				houseToCow[h] = x;	//h축사에는 x소가 있다고 표기
				
				return true;
			}
		}
		
		
		//매칭 실패
		return false;
	}
}