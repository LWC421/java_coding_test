import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


class UserSolution{
	// 물건 개수 n, [1 ~ 100_000] -> 항상 m의 배수
	// 벨트 개수 m, [1 ~ 10]
	// 물건 ID, [1 ~ 1_000_000_000]
	// 물건 무게, [1 ~ 1_000_000_000]
	// 제거 및 확인의 id, [1 ~ 1_000_000_000]
	// 벨트 고장 id, [1 ~ m]

	int numBelt;
	Map<Integer, Node> presents = null;			//선물들의 정보를 저장
	int[] beltConnect = null;					//벨트들의 연결정보를 저장(index와 값이 동일할 경우 고장 안난거)
	DoubleList[] belts = null;					//벨트, 실제 선물들이 들어가는 곳


	//공장 설립(100)
	/**
	 * 
	 * @param m 벨트의 개수
	 * @param n 선물의 개수
	 * @param idList 선물들의 id들
	 * @param weightList 선물들의 무게들
	 */
	public void init(int m, int n, int[] idList, int[] weightList) {
		int presentByLine = n/ m;			//한 벨트라인당 선물의 개수
		numBelt = m;						//벨트의 개수 저장
		presents = new HashMap<>();			//선물 정보 저장용 초기화
		beltConnect = new int[numBelt + 1];	//0번은 사용안하므로 +1로 초기화
		belts = new DoubleList[numBelt + 1];//0번은 사용안하므로 +1로 초기화
		for(int i = 1; i <= m; i++) {
			beltConnect[i] = i;			//고장이 안 났으므로 자기자신을 값으로 넣자
			belts[i] = new DoubleList();//더블링크드 리스트 넣어주자
		}
		int index = 0;
		for(int b = 1; b <= numBelt; b++) {
			//하나의 벨트마다 넣자
			DoubleList targetBelt = belts[b];
			for(int i = 0; i < presentByLine; i++) {
				Present present = new Present(idList[index], weightList[index], b);
				presents.put(present.id, targetBelt.addLast(present));	//벨트의 뒤에서부터 넣는다, 선물 정보 저장
				index++;
			}
		}
	}

	//물건 하차(200)
	public int getOff(int wMax) {
		int weightSum = 0;	//빼낸 상자들의 무게합

		for(int b = 1; b <= this.numBelt; b++) {
			if(beltConnect[b] != b) continue;	//고장났으면 작업하지 않는다

			DoubleList targetBelt = belts[b];
			int size = targetBelt.size;
			for(int s = 0; s < size; s++) {
				//최대 사이즈 개수만큼만 하자
				Present target = targetBelt.popFirst();	//앞에서 빼낸거
				if(target.removed) continue;						//삭제되었으면 다음으로

				if(target.weight > wMax) {
					//뺄 때 무게를 넘어갈 경우 -> 맨 뒤로 돌리자
					presents.put(target.id, targetBelt.addLast(target));
				}
				else {
					//무게가 넘어가지 않으면
					weightSum += target.weight;	//더해주고
					target.removed = true;		//하차했으므로 삭제되었다고 하자
				}
				break;
			}
		}

		return weightSum;
	}

	//물건 제거(300)
	public int remove(int rId) {
		if( !presents.containsKey(rId) ) {
			//만약 처음부터 들어온 적이 없는 id면
			return -1;
		}

		Present info = presents.get(rId).present;

		if(info.removed) {
			//이미 삭제되었다면
			return -1;
		}

		//삭제하자
		info.removed = true;

		return rId;
	}

	//물건 확인(400)
	public int find(int fId) {
		if( !presents.containsKey(fId)) {
			//처음부터 들어온 적이 없는 id
			return -1;
		}
		
		Node targetNode = presents.get(fId);		//이 id로 물건을 찾자
		Present info = targetNode.present;
		if(info.removed) {
			//삭제되었다면 -1반환
			return -1;
		}

		int beltNum = info.belt;	//일단 벨트 번호 가져와서

		while(beltConnect[beltNum] != beltNum) {
			//고장나서 다른 벨트의 번호가 적혀있다면
			beltNum = beltConnect[beltNum];
		}
		//끝나고 나면 beltNum에는 최종적으로 그 물건이 있는 belt의 번호가 적힌다

		DoubleList targetBelt = belts[beltNum];
		info.belt = beltNum;	//해당 물건은 여기에 있다고 적고 -> 사실 안적어도 되지만 최적화적인 문제다
		
		if(targetBelt.size > 1) {
			//옮길 필요가 있을때만 옮기자
			if(targetNode.prev != null) {
				//헤드에서 뽑힌거면 아무것도 할 필요없다 -> 헤드가 아닐때만 작업하자
				Node nextTail = targetNode.prev;	//검색된 노드의 그전 노드 가져오기
				targetBelt.head.prev = targetBelt.tail;		//원래의 헤드의 prev를 tail을 가리키도록 하고
				targetBelt.tail.next = targetBelt.head;		//원래의 tail의 next가 head를 가리키도록 하고
				
				targetNode.prev = null;				//검색된 노드의 prev를 null로(얘가 head가 될 예정이므로)
				nextTail.next = null;				//얘가 다음의 tail이므로 next가 없어야 한다
				targetBelt.head = targetNode;		//헤드를 조정
				targetBelt.tail = nextTail;			//꼬리를 조정
			}
		}

		return beltNum;
	}

	//벨트 고장(500)
	public int broken(int bNum) {
		if(beltConnect[bNum] != bNum) {
			//만약 이미 고장났으면
			return -1;
		}

		//고장나지 않은 벨트 오른쪽으로 찾기
		int target = -1;
		for(int i = 0; i < this.numBelt; i++) {
			target = (bNum + i) % this.numBelt + 1;
			if(beltConnect[target] == target) {
				//고장나 있지 않은 경우에만
				break;
			}
		}

		DoubleList targetBelt = belts[target];	//여기에다가
		DoubleList removeBelt = belts[bNum];	//고장날 리스트를 가져와서

		targetBelt.concatList(removeBelt);		//리스트를 붙여주자
		beltConnect[bNum] = target;				//벨트간의 연결관계를 넣어주자

		return bNum;
	}

	//선물 요소
	static class Present{
		int id;
		int weight;
		int belt;
		boolean removed;

		public Present(int id, int weight, int belt) {
			this.id = id;
			this.weight = weight;
			this.belt = belt;
			this.removed = false;
		}

		public Present() {};

		@Override
		public String toString() {
			return String.format("#%d : %dW in%d (%s)", 
					this.id, weight, belt, 
					this.removed ? "removed" : "alive");
		}
	}

	//이중 연결 리스트
	static class DoubleList{
		Node head;
		Node tail;
		int size;

		public DoubleList() {
			this.head = this.tail = null;
			this.size = 0;
		}

		public Node addFirst(Present present) {
			Node newNode = new Node(present);
			if(this.size == 0) {
				//비어 있을 경우
				this.head = newNode;
				this.tail = newNode;
			}
			else {
				//비어 있지 않을 경우
				this.head.prev = newNode;
				newNode.next = this.head;
				this.head = newNode;
			}
			size++;
			
			return newNode;
		}

		public Node addLast(Present present) {
			Node newNode = new Node(present);
			if(this.size == 0) {
				//비어 있을 경우
				this.tail = newNode;
				this.head = newNode;
			}
			else {
				//비어 있지 않을 경우
				this.tail.next = newNode;
				newNode.prev = this.tail;
				this.tail = newNode;
			}
			size++;
			
			return newNode;	//새로운 노드 반환
		}

		public void concatList(DoubleList other) {
			//doublelist를 받아서 뒤에 붙인다
			if( other.size != 0) {
				//다른 리스트가 비어있지 않을 때만
				if(this.size == 0) {
					//만약 내 list가 비어있으면
					this.head = other.head;
					this.tail = other.tail;
					this.size = other.size;		//head, tail, size전부 옮긴다
				}
				else {
					//만약 내 list가 비어있지 않으면
					this.tail.next = other.head;
					other.head.prev = this.tail;	//두개의 list를 연결시키고
					this.tail = other.tail;			//나의 tail을 다른 list의 꼬리로 변경시키고
					this.size += other.size;		//size를 더해주자
				}
			}

		}

		public Present popFirst() {
			//예외처리 안한 코드
			Node first = this.head;
			this.head = first.next;
			if(this.size == 1) {
				//한개였을때 pop했으면 size가 0이 된다
				this.tail = null;	//꼬리도 null로 바꾸기
			}
			else {
				//두개 이상이였을 때
				this.head.prev = null;
			}
			first.next = null;
			size--;
			return first.present;
		}

		public boolean isEmpty() {
			return this.size == 0;
		}
		
		public Node search(int id) {
			Node curr = this.head;
			while(curr != null) {
				//다음이 있으면
				if(curr.present.id == id) {
					//찾았으면
					return curr;
				}
				curr = curr.next;	//다음걸로 탐색
			}
			return null;	//못 찾았으면 null -> 그런데 그럴일 없다
		}
	}

	//리스트에 들어갈 원소들
	static class Node{
		Node prev;
		Node next;
		Present present;

		public Node(Present presnet) {
			this.prev = next = null;
			this.present = presnet;
		}
	}
}

public class CT_santa_gift_factory {

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int numCommand = Integer.parseInt(in.readLine());

		UserSolution userSolution = new UserSolution();
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");

		for(int c = 0; c < numCommand; c++) {
			st = new StringTokenizer(in.readLine());
			int command = Integer.parseInt(st.nextToken());

			switch(command) {
			case 100:{
				//공장 설립
				int n = Integer.parseInt(st.nextToken());
				int m = Integer.parseInt(st.nextToken());
				int[] idList = new int[n];
				int[] weightList = new int[n];
				for(int i = 0; i < n; i++) {
					idList[i] = Integer.parseInt(st.nextToken());
				}
				for(int i = 0; i < n; i++) {
					weightList[i] = Integer.parseInt(st.nextToken());
				}
				userSolution.init(m, n, idList, weightList);
				break;
			}
			case 200:{
				//물건 하차
				int wMax = Integer.parseInt(st.nextToken());
				int result = userSolution.getOff(wMax);
//				System.out.println(result);
								sb.append(result).append("\n");
				//				sb.append("get off : ").append(result).append("\n");
				break;
			}
			case 300:{
				//물건 제거
				int rId = Integer.parseInt(st.nextToken());
				int result = userSolution.remove(rId);
//				System.out.println(result);
				sb.append(result).append("\n");
				//				sb.append("remove : ").append(result).append("\n");
				break;
			}
			case 400:{
				//물건 확인
				int fId = Integer.parseInt(st.nextToken());
				int result = userSolution.find(fId);
//				System.out.println(result);
				sb.append(result).append("\n");
				//				sb.append("find : ").append(result).append("\n");
				break;
			}
			case 500:{
				//벨트 고장
				int bNum = Integer.parseInt(st.nextToken());
				int result = userSolution.broken(bNum);
//				System.out.println(result);
				sb.append(result).append("\n");
				//				sb.append("broken : ").append(result).append("\n");
				break;
			}
			}
		}
//		System.setOut(new PrintStream(new FileOutputStream("actual_output.txt")));
		System.out.println(sb.toString());
	}
}
