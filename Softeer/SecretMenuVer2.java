import java.util.*;
import java.io.*;

//두 가지 방법이 있다 수열로 푸는 방법과 String으로 푸는 방법 
//String version
public class Main
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        String first = sc.nextLine();
        String second = sc.nextLine();
        String third = sc.nextLine();

        if(third.contains(second)){
            System.out.println("secret");
        }else{
            System.out.println("normal");
        }

    }
}
