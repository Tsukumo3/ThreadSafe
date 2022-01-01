class Producer implements Runnable {

    // Common Pool Resources
    Buffer commonBuffer;
    int Counter;
    int MAX_COUNT;

    public Producer(Buffer aBuffer, int num){
        commonBuffer = aBuffer;
        MAX_COUNT = num;
        Counter = 0;
        return;
    }

    public void run() {
        System.out.println("Producer: start");
        while (!isMaximumCount()){
            try {
                ready(make());
                Thread.sleep(5); 
            } catch (InterruptedException e) {
            }
        }
        return;
    }

    private int make(){
        return 1;
    }

    private void ready(int obj){
        if(!commonBuffer.isEmpty()){
            System.out.println("Producer: FETAL ERROR: Buffer is not Empty");
        }else{
            System.out.println("Producer: ready - count:" + Counter);
            commonBuffer.setItem(obj);
            Counter++;
        }
    }

    private Boolean isMaximumCount() {
        return Counter == MAX_COUNT ? true : false;
    }
}

class Consumer implements Runnable {

    // Common Pool Resources
    Buffer commonBuffer;
    int Counter=0;
    int MAX_COUNT;

    public Consumer(Buffer aBuffer, int num){
        commonBuffer = aBuffer;
        MAX_COUNT = num;
        return;
    }
    public void run() {
        System.out.println("Consumer: start");
        while (!isMaximumCount()){
            try {
                use();
                used();
                Thread.sleep(5); 
            } catch (InterruptedException e) {
            }
        }
        return;
    }

    private void use(){
        if(commonBuffer.isEmpty()){
            System.out.println("Consumer: FETAL ERROR: Buffer is empty");
        }else{
            System.out.println("Consumer: use - count:" + Counter);
            commonBuffer.setItem(0);
            Counter++;
        }
    }

    private void used(){

    }

    private Boolean isMaximumCount() {
        return Counter == MAX_COUNT ? true : false;
    }

}

class Buffer{
    private int item;

    public Buffer(){
        item = 0;
    }

    public Boolean isEmpty(){
        return item == 0 ? true : false;
    }

    public int getItem(){
        return this.item;
    }

    public void setItem(int delivery){
        this.item = delivery;
    }
}

class Producer_Consumer_Problem {  
    public static void main(String[] args){
        int MAX = 3;
        Buffer aBuffer = new Buffer();
        Consumer aConsumer = new Consumer(aBuffer, MAX);
        Producer aProducer = new Producer(aBuffer, MAX);
        new Thread(aConsumer).start();
        new Thread(aProducer).start();
    }
}