import java.io.*;
import java.util.*;

public class B1655 {
	
	public static void main(String[] args) throws Exception{

//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");

		PriorityQueue<Integer> smalls = new PriorityQueue<>((a, b) -> {
			return b-a;
		}) ;
		
		PriorityQueue<Integer> bigs = new PriorityQueue<>((a, b) -> {
			return a-b;
		}) ;
		
		int numNumber = Integer.parseInt(in.readLine());
		int[] inputs = new int[numNumber];
		
		for(int i = 0; i < numNumber; i++) {
			inputs[i] = Integer.parseInt(in.readLine());
		}
		//입력 종료
		if(numNumber == 1) {
			System.out.println(inputs[0]);
			return;
		}
		
		smalls.add(inputs[0]);	//처음꺼는 작은거에 넣고 시작
		sb.append(inputs[0]).append('\n');
		
		
		if(inputs[0] <= inputs[1]) {
			bigs.add(inputs[1]);
			sb.append(inputs[0]).append('\n');
		} else {
			bigs.add(smalls.poll());
			smalls.add(inputs[1]);
			sb.append(inputs[1]).append('\n');
		}
		
		for(int i = 2; i < numNumber; i++) {
			if(bigs.peek() >= inputs[i]) {
				//큰 값쪽의 가장 작은 값보다, 작은 수가 들어오면 
				smalls.add(inputs[i]);	//일단 작은쪽에 넣고
			} else {
				bigs.add(inputs[i]);
			}
			
			if(smalls.size() - bigs.size() > 1) {
				//작은쪽 값들이 더 많으면
				bigs.add(smalls.poll());
			}
			if(bigs.size() - smalls.size() > 0) {
				//큰쪽 값들이 더 많으면
				smalls.add(bigs.poll());
			}
			
			sb.append(smalls.peek()).append('\n');
		}
		System.out.println(sb.toString());
	}
}
