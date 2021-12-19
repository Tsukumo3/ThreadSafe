# 01 GetStarted - Thread


## 01-1 まずはスレッドを動かしてみる

* javaではThreadを２通りで動かせる
    * class MyClass extends Thread
    * class MyClass implements Runnable

### extends Thread
* クラスをThreadのサブクラスであると宣言する
* このサブクラスは、Threadクラスのrunメソッドをオーバーライドしなければならない。
* これによりサブクラスのインスタンスが割り当てられ起動できる
* スレッドを実行するときはstart()メソッドを実行する

```
public MyClass Thread extends Thread{
    @Override
    public void run(){
    }

    public static void main(String[] args){
        MyClass aMyClass = new MyClass();
        aMyClass.start();
    }
}
```

### implements Runnable
* Runnableインタフェースを実装するクラスを宣言する
* そしてそのクラス内でrunメソッドを実装する
* これによりクラスのインスタンスが割り当てられ、Threadの作成時に引数として渡され開始される。

```
class MyClass implements Runnable {
    public void run() {
    }

    public static void main(String[] args){
        MyClass aMyClass = new MyClass();
        new Thread(aMyClass).start();
    }
}
```

* クラスのフィールドやコンストラクタについては公式Docを確認してください

## CountDownTimer

* 複数スレッドを動かしてカウントダウンするタイマーを作成してみる

### 仕様
* 初期値0から5の回数をカウントして終了するタイマー
* 3つのタイマーA,B,Cを作成して実行する

### 設計
1. main 
    1. タイマーを３つ作成
    1. それぞれ実行
1. CountDownTimer
    1. Tick
        1. カウント回数が5回を超えていなければカウント回数を+1する
    2. Beep
        1. カウント回数が5回になったとき終了する

### 実装概要
* 一部省略して記載する
* extends Threadを利用して作成した。

```
class CountDownTimer extends Thread{

    @Override
    public void run() {
        while (!isMaximumCount()) { // カウント回数が5回でなければ
            tick();                 // カウント回数を+1する
        }
        beep();                     // カウント回数が5回を超えた
        return;                     // 終了
    }

    public static void main(String[] args)
    {
        CountDownTimer timerA = new CountDownTimer("A");
        CountDownTimer timerB = new CountDownTimer("B");
        CountDownTimer timerC = new CountDownTimer("C");
        timerA.start();
        timerB.start();
        timerC.start();
    }
}
```

### 実行結果
```
CountDownTimer[TimerName=B,Counter=0]
CountDownTimer[TimerName=B,Counter=1]
CountDownTimer[TimerName=C,Counter=0]
CountDownTimer[TimerName=A,Counter=0]
CountDownTimer[TimerName=A,Counter=1]
CountDownTimer[TimerName=C,Counter=1]
CountDownTimer[TimerName=B,Counter=2]
CountDownTimer[TimerName=A,Counter=2]
CountDownTimer[TimerName=C,Counter=2]
CountDownTimer[TimerName=B,Counter=3]
CountDownTimer[TimerName=C,Counter=3]
CountDownTimer[TimerName=C,Counter=4]
CountDownTimer[TimerName=A,Counter=3]
CountDownTimer[TimerName=B,Counter=4]
CountDownTimer[TimerName=B,Counter=5] === Finish: B ===
CountDownTimer[TimerName=C,Counter=5] === Finish: C ===
CountDownTimer[TimerName=A,Counter=4]
CountDownTimer[TimerName=A,Counter=5] === Finish: A ===
```

## 参考文献
* [Thread (Java Platform SE 8 )](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Thread.html)