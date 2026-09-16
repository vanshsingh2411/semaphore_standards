// Java implementation of a producer and consumer
// that use semaphores to control synchronization.

import java.util.concurrent.Semaphore ;

class Que {

  int item;
  // Con initialized with 0 permits
  // to ensure put() executes first
  static Semaphore Con = new Semaphore(0);
  static Semaphore Prod = new Semaphore(1);

  // to get an item from buffer
  void get() {
    try {
      // Before consumer can consume an item,
      // it must acquire a permit from Con
      Con.acquire();
    } catch (InterruptedException e) {
      System.out.println("InterruptedException caught");
    }

    // consumer consuming an item
    System.out.println("Consumer consumed item: " + item);

    // After consumer consumes the item,
    // it releases Prod to notify producer
    Prod.release();
  }


  // to put an item in buffer
  
void put(int item) {

  try {
      // Before producer can produce an item,
      // it must acquire a permit from Prod
      Prod.acquire();

    } 
  catch(InterruptedException e) {
      System.out.println("InterruptedException caught");
    }

    // producer producing an item
    this.item = item;

    System.out.println("Producer produced item: " + item);

    Con.release();
  }
}



class Producer implements Runnable {

  Que q;

 Producer(Que q) {
     this.q = q;
     new Thread(this, "Producer").start();
  }

  public void run() {

    for( int i = 0; i < 5; i++)  q.put(i);

  }

}

class Consumer implements Runnable {

  Que q;

  Consumer(Que q) {
      this.q = q;
      new Thread(this, "Consumer").start();
  }

  public void run() {
    for( int i = 0; i < 5; i++ )
    q.get();
  }

}


public class prod_cons_semaphore {

     public static void main( String args[] ) {

     Que q = new Que();

     new Consumer(q);
     new Producer(q);

  }

}
