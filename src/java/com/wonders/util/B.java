package com.wonders.util;

public class B extends A{
	public B() {
	
		// TODO Auto-generated constructor stub
	}

	public void test(){
		super.test();
		System.out.print("B");
	}
	
	public static void main(String[] args){
		A a = new B();
		
		a.test();
	}
}
