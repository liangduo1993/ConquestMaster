package Domain;

public class Country {
	public String name;
	private int age;
	private String colo;

	public Country(String name, int age) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.age=age;
		
	}
	
	public static void main(String[] args) {
		Country c = new Country("alaska", 123);
		System.out.println(c.name+" "+c.age);
	}
	
}
