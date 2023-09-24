import java.util.*;

class Solution {
    public int solution(String[] words) {
        int answer = 0;
        
        Trie root = new Trie();
        Trie curr = null;
        
        for(String word: words){
            curr = root;
            
            for(char c: word.toCharArray()){
                curr = curr.get(c);
                curr.count = curr.count+1;
            }
        }
        
        Loop: for(String word: words){
            curr = root;
            
            int length = 0;
            for(char c: word.toCharArray()){
                length++;
                curr = curr.get(c);
                if(curr.count == 1){
                    break;
                }
            }
            answer += length;
        }
        
        return answer;
    }
}

class Trie{
    int count;
    Map<Character, Trie> node;
    
    Trie(){
        this.count = 0;
        this.node = new HashMap<>();
    }
    
    Trie get(char c){
        Trie next = this.node.get(c);
        if(next != null) return next;
            
        this.node.put(c, new Trie());
        next = this.node.get(c);
        return next;
    }
}