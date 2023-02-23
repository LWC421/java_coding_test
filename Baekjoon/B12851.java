import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;


public class B12851 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int result = 0;						//경로가 몇 개인지
		int shortCut = Integer.MAX_VALUE;	//최단 경로의 길이
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int subin = Integer.parseInt(st.nextToken());		//수빈
		int dongsang = Integer.parseInt(st.nextToken());	//동생
		int max = Math.max(subin, dongsang);				//둘중 큰값
		
		int[] visited = new int[max+2];	//방문체크
		Arrays.fill(visited, Integer.MAX_VALUE);
		
		
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {subin, 0});
		
		int[] tmp;
		int current;
		int count;
		while( !q.isEmpty() ) {
			tmp = q.poll();
			current = tmp[0];
			count = tmp[1];
			
			if(current == dongsang) {
				//동생위치에 왔으면
				shortCut = count;	//최단경로로 설정
				result++;			//최단경로 개수 늘리기
				
				while( !q.isEmpty() ) {
					//남은 것들에 대해서도 확인 해보기
					tmp = q.poll();
					current = tmp[0];
					count = tmp[1];
					
					if(count == shortCut && current == dongsang) {
						//최단경로인 애들만 확인
						result++;
					}
				}
				
				System.out.printf("%d\n%d", shortCut, result);
				
				return;
			}
			
			if( current <= max && visited[current+1] >= count + 1) {
				// +1에 대해
				q.add(new int[] {current+1, count+1});
				visited[current+1] = count+1;
			}
			if( current > 0 && visited[current-1] >= count + 1) {
				// -1에 대해
				q.add(new int[] {current-1, count+1});
				visited[current-1] = count+1;
			}
			if( current*2 <= max + 1 && visited[current*2] >= count + 1) {
				// *2에 대해
				q.add(new int[] {current*2, count+1});
				visited[current*2] = count+1;
			}
		}
		
		
		System.out.printf("%d\n%d", shortCut, result);
	}
}
