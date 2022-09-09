//stack 
import java.util.*;
import java.util.stream.*;
import java.util.Map.*;

class Solution {
    
    boolean solution(String s) {
        boolean answer = true;
        
        Stack<Character> stack = new Stack<Character>();

        for(int i=0; i< s.length(); i++) {
            if(s.charAt(i) == ')'){
                if(stack.size()==0){
                    return false;
                }else{
                    stack.pop();
                }
            }else{
                stack.push(s.charAt(i));
            }
        }
        
        if(stack.size() !=0){ //stack size !=0 -> isEmpty()로 접근할수 있다.
            return false;
        }

        return answer;
    }
}
