import java.io.*;
import java.util.*;

public class B10775 {

	static int[] parents;
	static boolean[] used;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int numGate = Integer.parseInt(in.readLine());		//게이트의 개수
		int numAirplane = Integer.parseInt(in.readLine());	//비행기의 개수
		
		int num;
		int[] gateWants = new int[numAirplane];	//비행기들이 원하는 곳들
		for(int i = 0; i < numAirplane; i++) {
			num = Integer.parseInt(in.readLine());	//1 ~ num중에 넣어야 된다
			gateWants[i] = num;
		}
		//입력 종료
		
		parents = new int[numGate + 1];		//어디로 가야 되는지 표시
		used = new boolean[numGate + 1];	//사용중인지
		
		for(int i = 0; i <= numGate; i++) {
			parents[i] = i;	//일단 자기자신이 root다
		}
		
		int count = 0;
		for(int gate: gateWants) {
			//하나하나의 비행기들에 대해
			int curr = gate;
			while(used[curr]) {
				//사용중이면
				curr = find(curr);
			}
			if(curr == 0) {
				//끝까지 와버렸으면
				break;
			}
			used[curr] = true;	//사용중이라고 표시
			union(gate, curr-1);
			count++;
		}
		System.out.println(count);
	}
	
	public static int find(int x) {
		if(parents[x] == x) {
			return x;
		}
		
		return parents[x] = find(parents[x]);
	}
	
	public static void union(int x, int y) {
		x = find(x);
		y = find(y);
		
		parents[x] = y;
	}
}
