import java.util.*;
import java.io.*;

class Person{
	int age;
	String name;
	
	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	public String toString() {
		return "| " + this.age + "  " + this.name + " |";
	}
	
	public int getAge() {
		return this.age;
	}
}


public class Main {
	
	static int N;
	static List<Person> list_person = new ArrayList<>();
	static List<Person> ref_person = new ArrayList<>();

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = Integer.parseInt(sc.nextLine());
		//sc.nextLine(); // nextLine은 도대체 어떻게 읽길래 한 줄을 띄어 쓰게 되는걸까?	
		
		for(int i=0; i<N; i++) {
			String person_info = sc.nextLine();
			String[] input_line= person_info.split(" ");
			
			Person person = new Person(Integer.parseInt(input_line[0]), input_line[1]);
			list_person.add(person);

		}
		
		//나이 순으로 정렬할 경우, 나이가 같을 경우 순서가 바뀌지 않는다는 점을 착안해보자.
		Collections.sort(list_person, Comparator.comparing(Person::getAge));
		
		//답 출력
		for(int i=0; i<list_person.size(); i++) {
			Person person = list_person.get(i);
			System.out.println(person.age + " " + person.name);
		}

		
	}
	
}
