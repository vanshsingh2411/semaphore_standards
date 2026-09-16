
import java.util.concurrent.Semaphore;

 public class reader_writer_semaphore {
   
        private static final Semaphore resourceLock = new Semaphore(1);
        private static final Semaphore readerCountLock = new Semaphore(1);   

        private static int readerCount = 0;
        private static int sharedData = 0; 

        static class Reader implements Runnable {

               private final int id;
              
               public Reader( int id ){

                   this.id = id;

               }

               @Override
            public void run() {

                 try {

                // Entry Section

                readerCountLock.acquire();

                readerCount++ ;

               if( readerCount == 1 ) {
                    resourceLock.acquire(); // First reader locks the shared resource
               }

               readerCountLock.release();

               // Critical Section (Reading)
                System.out.println("Reader " + id + " read value: " + sharedData);
                Thread.sleep(100); // Simulate reading time

                readerCountLock.acquire();
                readerCount--;
                if (readerCount == 0) {
                     resourceLock.release(); // Last reader releases the shared resource
                }

                readerCountLock.release();
            }

         catch( InterruptedException e ){
               
               Thread.currentThread().interrupt();
              
            }
    
        }


         }


     static class Writer implements Runnable {
         private final int id;

         public Writer(int id) {
               this.id = id;
         }


         @Override
        public void run(){
               
            
            try {

                 // Entry Section
                resourceLock.acquire(); // Exclusive access for writing

                // Critical Section (Writing)
                sharedData++;
                System.out.println("Writer " + id + " updated value to: " + sharedData);
                Thread.sleep(150); // Simulate writing time

                // Exit Section
                resourceLock.release();

            }

            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
 
        }

   }
 
    
    public static void main( String args[] ) {

          // Start 3 Readers and 2 Writers
        Thread r1 = new Thread(new Reader(1));
        Thread r2 = new Thread(new Reader(2));
        Thread w1 = new Thread(new Writer(1));
        Thread r3 = new Thread(new Reader(3));
        Thread w2 = new Thread(new Writer(2));

        r1.start();
        w1.start();
        r2.start();
        r3.start();
        w2.start();
 
    }

}
