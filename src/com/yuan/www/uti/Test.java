package com.yuan.www.uti;

import java.sql.Connection;

public class Test {

	public static void main(String[] args) {
		MyThead myThead = new MyThead();
		MyThread2 myThread2 =new MyThread2();
		Thread t1 = new Thread(myThead);
		Thread t2 = new Thread(myThead);
		
		
		for (int i = 0; i < 100; i++) {
			 new Thread(myThead).start();;
			 new Thread(myThead).start();;
			 new Thread(myThead).start();;
			 new Thread(myThread2).start();
		}
		;

	
	}

}

class MyThead implements Runnable {

	@Override
	public void run() {
		DataSource dataSource = DataSource.getInstance();
		Connection connection = dataSource.getConnection();
		System.out.println(Thread.currentThread().getName() + ":" + connection);
		dataSource.close(connection);
	}

}
class MyThread2 implements Runnable{

	@Override
	public void run() {
		
		DataSource dataSource = DataSource.getInstance();
		Connection connection = dataSource.getConnection();
		System.out.println(Thread.currentThread().getName() + ":" + connection);
		for(;true;){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}