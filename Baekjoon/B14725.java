import java.io.*;
import java.util.*;

public class B14725{
	
	static StringBuilder sb;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		sb = new StringBuilder("");
		
		int numAnt = Integer.parseInt(in.readLine());	//개미들의 개수
		List<String>[] ants = new LinkedList[numAnt];
		
		int numDepth;
		String target = null;
		for(int i = 0; i < numAnt; i++) {
			ants[i] = new LinkedList<>();
			st = new StringTokenizer(in.readLine(), " ");	//개미 정보 받기
			numDepth = Integer.parseInt(st.nextToken());	//개미가 지나간 굴의 개수
			for(int j = 0; j < numDepth; j++) {
				//개미가 지나온 굴 만큼 처리해주자
				ants[i].add(st.nextToken());
			}
		}
		//모든 입력 종료
		Trie root = new Trie();	//새로운 트라이를 생성
		Trie curr = null;	//현재 보고 있는 서브트라이
		String food, lastFood;
		for(int ant = 0; ant < numAnt; ant++) {
			curr = root;	//일단 루트부터 시작해서
			int limit = limit=ants[ant].size()-1;
			for(int i = 0; i < limit; i++) {
				//마지막 음식 전 까지만
				food = ants[ant].get(i);	//해당 음식을 가져와서
				if( !curr.child.containsKey(food) ) {
					//가지고 있지 않으면
					curr.child.put(food, new Trie());	//서브 트라이 넣기
				}
				curr = curr.child.get(food);	//서브트리로 내려가기
			}
			//마지막 글자 따로 처리
			lastFood = ants[ant].get(limit);	//마지막 음식 가져오기
			//가지고 있지 않으면
			curr.child.put(lastFood, new Trie());	//서브 트라이 넣기
			curr = curr.child.get(lastFood);	//마지막 음식으로 내려가고
			curr.isEnd = true;	//마지막이라고 표시하기
		}
		//트라이 처리 완료
		
		search(root, 0);	//root부터 depth0으로 넣어서 검색시작
		System.out.println(sb.toString());
	}
	
	static void search(Trie trie, int depth) {
		Set<Map.Entry<String, Trie>> sets = trie.child.entrySet();
		for(Map.Entry<String, Trie> sub: sets) {
			StringBuilder builder = new StringBuilder("");
			for(int i = 0; i < depth; i++) {
				builder.append("--");	//depth만큼 넣어주기
			}
			builder.append(sub.getKey()).append('\n');	//key값을 넣어주고
			sb.append(builder.toString());
			search(sub.getValue(), depth+1);	//서브트리로 가자
		}
	}
	
	static class Trie{
		boolean isEnd;
		TreeMap<String, Trie> child;	//순서 보장을 위해 TreeMap으로 설정
		
		public Trie() {
			this.isEnd = false;
			this.child = new TreeMap<>();
		}
	}
}