import java.io.*;
import java.util.*;

public class Main{

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int TC = Integer.parseInt(in.readLine());

        int numNumber;
        String[] numbers;
        StringBuilder sb = new StringBuilder("");

        for(int test_case = 0; test_case < TC; test_case++){
            numNumber = Integer.parseInt(in.readLine());
            numbers = new String[numNumber];

            for(int i = 0; i < numNumber; i++){
                //폰번호 하나씩 저장하기
                numbers[i] = in.readLine();
            }
            //하나의 테스트케이스 입력 종료

            Trie root = new Trie();

            //하나의 문자들을 돌면서
            Trie curr = new Trie();
            for(String number: numbers){
                curr = root;
                for(char c: number.toCharArray()){
                    curr = curr.findOrAdd(c);
                }
                curr.end = true;    //여기서 끝이났다고 하기
            }

            boolean find = false;

            loop: for(String number: numbers){
                curr = root;
                for(char c : number.substring(0, number.length()-1).toCharArray()){
                    //마지막 글자 전까지만 보자
                    curr = curr.find(c);
                    if(curr.end){
                        //다른 글자의 prefix가 나의 중간글자까지에 있으면
                        sb.append("NO").append("\n");
                        find = true;
                        break loop;
                    }
                }
            }

            if(!find){
                //중간에 잘못 된 것이 없었을 때만
                sb.append("YES").append("\n");
            }
        }
        System.out.println(sb.toString());
    }

    static class Trie{
        boolean end;
        Map<Character, Trie> children;

        public Trie(){
            this.end = false;
            this.children = new HashMap<>();
        }

        public Trie findOrAdd(char c){
            Trie child = this.children.get(c);
            if(child == null){
                //만약 해당 노드가 없으면
                this.children.put(c, new Trie());
                child = this.children.get(c);
            }

            return child;
        }

        public Trie find(char c){
            return this.children.get(c);
        }
    }
}