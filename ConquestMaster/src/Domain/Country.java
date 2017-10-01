package Domain;

import java.awt.Color;

public class Country {
	public String name;
	private int age;



	public Country(String name, int age,Color colo) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.age=age;
		
		
	}
	
	public static void main(String[] args) {
		Country c = new Country("alaska", 123,Color.BLACK);
		System.out.println(c.name+" "+c.age);
	}
//liang
	
}
