import java.io.*;
import java.util.*;

public class Main{

    static int length;
    static int[] numbers;
    static List<Section> sections;
    static int minMove;

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("input.txt"));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String input = in.readLine();

        length = input.length();
        numbers = new int[length];
        char[] inputs = input.toCharArray();
        sections = new LinkedList<>();
        minMove = 51 * 26 + 1;

        for(int i = 0; i < length; i++){
            numbers[i] = (int)(inputs[i] - 'a');
        }

        int left, right;
        for(int alpha = 0; alpha < 26; alpha++){
            left = right = -1;      //일단 초기화

            for(int i = 0; i < length; i++){
                if(numbers[i] == alpha){
                  if(left == -1) left = i;
                  else right = i;
                }
            }

            if(right != -1) sections.add(new Section(left, right));
            else if(left != -1) sections.add(new Section(left, left));
        }

        dfs(0, 0, 0);

        System.out.println(minMove + length);
    }

    public static void dfs(int move, int cursor, int index){
        if(index == sections.size()){
            //만약 모든것들 고려했으면
            minMove = Math.min(minMove, move);
            return;
        }

        Section section = sections.get(index);

        move += section.right - section.left;   //해당 구간내에서 이만큼 움직여야 한다

        //왼쪽에서 오른쪽으로 가는 경우
        dfs(move + Math.abs(cursor - section.left), section.right, index+1);
        //오른쪽에서 왼쪽으로 가는 경우
        dfs(move + Math.abs(cursor - section.right), section.left, index+1);
    }

    static class Section{
        int left, right;

        public Section(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString(){
            return String.format("[%d, %d]", left, right);
        }
    }
}