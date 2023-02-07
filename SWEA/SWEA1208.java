import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class SWEA1208 {
	public static int numDump;
	public static int[] boxes;
	public static int sumBox;
	public static boolean isOdd;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		for(int test_case = 1; test_case <= 10; test_case++) {
			numDump = Integer.parseInt(in.readLine());
			boxes = Stream.of(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			sumBox = 0;
			
			for(int b: boxes) {
				sumBox += b;
			}
			
			isOdd = sumBox % 2 == 0 ? false : true;
			
			dump(numDump);
			
			int maxBox = 0;
			int minBox = 100;
			
			for(int i = 0; i < 100; i++) {
				if(boxes[i] > maxBox) {
					maxBox = boxes[i];
				}
				if(minBox > boxes[i]) {
					minBox = boxes[i];
				}
			}
			
			System.out.printf("#%d %d\n", test_case, maxBox - minBox);
		}
	}
	
	public static void dump(int numDump) {
		if(numDump == 0) {
			return;	//Ƚ���� ���� ��� return
		}
		
		int first = boxes[0];
		boolean flatten = true;
		
		for(int i = 1; i < 100; i++) {
			if( !isOdd && Math.abs(boxes[i] - first) > 0) {
				flatten = false;
				break;	//��źȭ �Ϸ� �ȵȰŴ�
			}
			else if( isOdd && Math.abs(boxes[i] - first) > 1) {
				flatten = false;
				break;	//��źȭ �Ϸ� �ȵȰŴ�
			}
		}
		
		if(flatten) {
			return;	//��źȭ �Ϸ�Ǿ����Ƿ� return;
		}
		
		//���� ���� ���� ���� �� ã��
		int maxIndex = 0;
		int maxBox = 1;
		int minIndex = 0;
		int minBox = 100;
		
		for(int i = 0; i < 100; i++) {
			if(boxes[i] > maxBox) {
				maxBox = boxes[i];
				maxIndex = i;
			}
			if(minBox > boxes[i]) {
				minBox = boxes[i];
				minIndex = i;
			}
		}
		
		//������ �ű�� �κ�
		boxes[maxIndex]--;
		boxes[minIndex]++;
		
		dump(numDump-1);
	}
}
