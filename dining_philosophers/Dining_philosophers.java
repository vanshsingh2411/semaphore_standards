
import java.util.concurrent.locks.ReentrantLock;

public class Dining_philosophers{
         
    static final int N = 5;
    static ReentrantLock[] forks = new ReentrantLock[N];
 
    
    public static void main(String[] args) {
       
        for (int i = 0; i < N; i++) {
            forks[i] = new ReentrantLock();
        }

        for( int i = 0; i < N ; i++ ){
            final int id = i ;
            new Thread( ()->philosopherLife(id) ).start();
        }

  }


  static void philosopherLife(int id) {
     
        int left = id;
        int right = (id + 1) % N;


        // pick smaller number first so nobody deadlocks
          int first = Math.min( left,right );
          int second = Math.max(left, right);

          while ( true ) {

              think(id);
              forks[first].lock();
              forks[second].lock();
              eat(id);
              forks[second].unlock();
              forks[first].unlock();
           
         }

      }

   static void think(int id) {

         System.out.println("Philosopher " + id + " is waiting ");
         sleep();

   }

   static void eat( int id ) {
    
        System.out.println("Philosopher " + id + " is eating ");
        sleep();

    }

  static void sleep() {

        try {
            Thread.sleep((long) (Math.random() * 1000));
        } 
        
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

   }

}