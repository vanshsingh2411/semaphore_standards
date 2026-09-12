
import java.util.* ;

public class prod_cons_Using_threads {
    
  public static  void main( String args[] ){
        
     
     Queue<Integer> buffer = new LinkedList<>();
     int maxsize = 7;

     Producer1 producer = new Producer1(buffer , maxsize);

     Consumer1 consumer = new Consumer1(buffer , maxsize);

     Thread p = new Thread(producer);
     Thread c = new Thread(consumer);

     p.start();
     c.start();

  }

}



class Producer1 extends Thread{

    private Queue<Integer> queue;
    private int maxsize;

    public Producer1(Queue<Integer> queue , int maxsize){
           
       this.queue = queue;
       this.maxsize = maxsize;
    
    }


    @Override
    public void run(){
          
          while(true){

            synchronized(queue){

                  while (queue.size() == maxsize) {

                    try{
                          System.out.println("queue full producer waiting");

                          queue.wait();
                    }

                    catch(Exception e){
                          System.out.println(e.getMessage());
                    }
                    
                  }

                  Random random = new Random();

                  int i = random.nextInt();

                  System.out.println("producing value " + i);

                  queue.add(i);
                  queue.notify();
            }
  
          }
     }
  
   }


   class Consumer1 extends  Thread{

         private Queue<Integer> queue;
         private int maxsize;

         
         public Consumer1(Queue<Integer> queue ,int maxsize ){
                
               this.queue = queue;
               this.maxsize = maxsize;

         }

         @Override 
   public void run(){
              
     while( true ){

        synchronized(queue){
                       
            while(queue.isEmpty() ){
                            
                try {
                          System.out.println("queue empty consumer waiting");
                          queue.wait();
                     }

                      catch(Exception e){

                         System.out.println( e.getMessage() );

                      }

                  }

                    System.out.println( "consuming value " + queue.remove() );
                    queue.notify();

                 }
            }
         }
   }