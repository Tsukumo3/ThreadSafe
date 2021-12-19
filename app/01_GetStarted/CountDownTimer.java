class CountDownTimer extends Thread{

    final int MAX_COUNT = 5;
    final long millis = 10L;
    private int Counter = 0;
    private String TimerName = "";

    CountDownTimer(String aName) {
        this.TimerName = aName;
        return;
    }

    private String GetTimerName() {
        return TimerName;
    }

    @Override
    public void run() {
        while (!isMaximumCount()) {
            tick();      
        }
        beep();
        return;
    }

    private void tick() {
        System.out.println(this);
        Counter += 1;
        return;
    }

    private void beep() {
        System.out.println(this +" === Finish: " + GetTimerName() + " ===");
        return;
    }

    private Boolean isMaximumCount() {
        return Counter == MAX_COUNT ? true : false;
    }

    @Override
	public String toString() {
		StringBuffer aBuffer = new StringBuffer();
		Class<?> aClass = this.getClass();
		aBuffer.append(aClass.getName());
		aBuffer.append("[TimerName=");
		aBuffer.append(TimerName);
		aBuffer.append(",Counter=");
		aBuffer.append(Counter);
		aBuffer.append("]");
		return aBuffer.toString();
	}

    public static void main(String[] args) {
        CountDownTimer timerA = new CountDownTimer("A");
        CountDownTimer timerB = new CountDownTimer("B");
        CountDownTimer timerC = new CountDownTimer("C");
        timerA.start();
        timerB.start();
        timerC.start();
    }
}