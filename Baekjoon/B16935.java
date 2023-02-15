import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B16935 {
	static int yLength;		//������ N�� �����ϴ�
	static int xLength;		//������ M�� �����ϴ�
	static int numCommand;	//������ R�� �����ϴ�
	
	static int maxLength; 	//y, x�� �� ��

	static int[][] map;		//���ڰ� ��� ���̴�
	static int[][] copy;	//������ �迭�̴�

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;

		st = new StringTokenizer(in.readLine(), " ");
		yLength = Integer.parseInt(st.nextToken());
		xLength = Integer.parseInt(st.nextToken());
		numCommand = Integer.parseInt(st.nextToken());
		maxLength = Math.max(yLength, xLength);

		//�� �ʱ�ȭ ����
		map = new int[maxLength][maxLength];
		copy = new int[maxLength][maxLength];

		for(int y = 0; y < yLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 0; x < xLength; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		//��ɾ� ����
		st = new StringTokenizer(in.readLine());
		for(int i = 0; i < numCommand; i++) {
			switch(Integer.parseInt(st.nextToken())) {
			case 1:	//�迭 ���� ����
				upDown();
				break;
			case 2:	//�迭 �¿� ����
				leftRight();
				break;
			case 3:	//�ð���� 90�� ȸ��
				rotateMinus();
				break;
			case 4:	//�ݽð���� 90�� ȸ��
				rotatePlus();
				break;
			case 5:	//4���� �� �ð���� 90�� ȸ��
				quadRotatePlus();
				break;
			case 6:	//4���� �� �ݽð���� 90�� ȸ��
				quadRotateMinus();
				break;
			}
		}		
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				sb.append(map[y][x]).append(" ");
			}
			sb.append("\n");
		}

		System.out.println(sb.toString());
	}

	//a�� [y, x]��  b�� [y, x]�� �����Ѵ�
	public static void swap(int ay, int ax, int by, int bx) {
		int tmp = map[ay][ax];
		map[ay][ax] = map[by][bx];
		map[by][bx] = tmp;
	}
	
	//yLength�� xLength�� ��ȯ
	public static void swapYX() {
		int tmp = yLength;
		yLength = xLength;
		xLength = tmp;
	}

	//copy�迭�� map�� ����
	public static void copy() {
		//���� �迭 �����س���
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				copy[y][x] = map[y][x];
			}
		}
	}
	
	//map�� 0���� �ʱ�ȭ
	public static void init() {
		for(int y = 0; y < maxLength; y++) {
			for(int x = 0; x < maxLength; x++) {
				map[y][x] = 0;
			}
		}
	}

	//1�� Ŀ�ǵ� -> �迭 ���� ����
	public static void upDown() {
		int halfY = yLength / 2;
		for(int y = 0; y < halfY; y++) {
			for(int x = 0; x < xLength; x++) {
				swap(y, x, yLength-1-y, x);
			}
		}
	}

	//2�� Ŀ�ǵ�
	public static void leftRight() {
		int halfX = xLength / 2;
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < halfX; x++) {
				swap(y, x, y, xLength - x - 1);
			}
		}
	}

	//3�� Ŀ�ǵ�
	public static void rotatePlus() {
		copy();
		init();
		
		swapYX();

		//������
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				map[y][x] = copy[x][yLength-1-y];
			}
		}
		
		
	}

	//4�� Ŀ�ǵ�
	public static void rotateMinus() {
		copy();
		init();
		init();
		
		swapYX();

		//������
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				map[y][x] = copy[xLength-1-x][y];
			}
		}
	}

	//5�� Ŀ�ǵ� -> 4�����ؼ� �ð���� 90�� ȸ��
	public static void quadRotatePlus() {
		copy();
		
		int halfX = xLength / 2;
		int halfY = yLength / 2;
		
		//4����� ������ŭ ���鼭
		for(int y = 0; y < halfY; y++) {
			for(int x = 0; x < halfX; x++) {
				map[y][x] 				= copy[y+halfY][x];			//�»�� ó���ϱ�
				map[y][x+halfX] 		= copy[y][x];				//���� ó���ϱ�
				map[y+halfY][x] 		= copy[y+halfY][x+halfX];	//���ϴ� ó���ϱ�
				map[y+halfY][x+halfX] 	= copy[y][x+halfX];			//���ϴ� ó���ϱ�
			}
		}
	}

	//6�� Ŀ�ǵ�
	public static void quadRotateMinus() {
		copy();
		int halfX = xLength / 2;
		int halfY = yLength / 2;
		
		
		//4����� ������ŭ ���鼭
		for(int y = 0; y < halfY; y++) {
			for(int x = 0; x < halfX; x++) {
				map[y][x] 				= copy[y][x+halfX];			//�»�� ó���ϱ�
				map[y][x+halfX] 		= copy[y+halfY][x+halfX];	//���� ó���ϱ�
				map[y+halfY][x] 		= copy[y][x];				//���ϴ� ó���ϱ�
				map[y+halfY][x+halfX] 	= copy[y+halfY][x];			//���ϴ� ó���ϱ�
			}
		}
	}

}
