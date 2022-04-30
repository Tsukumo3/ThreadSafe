# 03 - Producer Counsumer Problem - 生産者/消費者問題

## 共有動作
- 共有動作
    - 複数のプロセスが共通の動作を持つ時、これらの動作は共有されている
    - 共通動作はそれを共有するプロセスに実行される
- 悪いスケジューリング
    - 次の例を参考にしてほしい。 

## Producer-Consumer-Problem - 生産者/消費者 問題

```
    @startuml
    [*] --> Producer
    [*] --> Consumer
    Producer --> Producer : make
    Buffer --> Consumer : use
    Producer --> Buffer : ready
    Consumer --> Consumer
    @enduml
```

- 上記の場合、次の問題のある動作が惣菜する
    - Producerは、バッファに空きができるまでデータを入れることができない
    - Consumerは、バッファが空の間はデータを取り出すことができない
- こうした問題に対応しなければならない


```
    @startuml
    [*] --> Producer
    [*] --> Consumer
    Producer --> Producer : make
    Buffer --> Consumer : use
    Producer --> Buffer : ready
    Consumer --> Consumer
    @enduml
```

## 同期
- ConsumerはProducerが`ready`をしないと`use`を実行しない
    - 部品がBufferにないときに`use`させない
    - 読み出しで**ロック**をする
- ProducerはConsumerが`used`をしないと`make`を実行しない
    - 部品がBufferにあるときに`make`させない
    - 書き込みで**ロック**する
- 同期
    - 複数のスレッド/プロセスが待ち合わせを行いタイミングをあわせて動作すること

    ```
    @startuml
    [*] --> Producer_Consumer

    state Producer_Consumer {
    [*] --> A
    A -down-> B : make
    B -right-> C : ready
    C -up-> D : use
    D -left-> A : used
    }
    @enduml
    ```
## クライアント/サーバー

```
@startuml
Client -> Server:
note left: call
note right: request

Server -> Server: service

Client <-- Server: 
note left: wait
note right: replay

Client -> Client: continue
@enduml
```

## 実装 - Producer-Consumer-Problem

- Bufferでは共有オブジェクトを管理している
    - Producer: Bufferに値(1)を設定する
    - Consumer: Bufferに値(0)を設定する
- このBufferの管理部分を同期(syncronized)する

```
public synchronized int getItem(){
    return this.item;
}

public synchronized void setItem(int delivery){
    this.item = delivery;
}
```

- Consumer, Producer共にアクセス制御の機構を持たせている

```
if(!commonBuffer.isEmpty()){
    System.out.println("Producer: FETAL ERROR: Buffer is not Empty");
}else{
    System.out.println("Producer: ready - count:" + Counter);
    commonBuffer.setItem(obj);
    Counter++;
}
```

```
$ java Producer_Consumer_Problem
Producer: start
Consumer: start
Consumer: FETAL ERROR: Buffer is empty
Consumer: FETAL ERROR: Buffer is empty
Consumer: FETAL ERROR: Buffer is empty
Producer: ready - count:0
Consumer: use - count:0
Consumer: FETAL ERROR: Buffer is empty
Producer: ready - count:1
Consumer: use - count:1
Producer: FETAL ERROR: Buffer is not Empty
Consumer: FETAL ERROR: Buffer is empty
Producer: ready - count:2
Consumer: use - count:2
```

- 実行的なかった時のログを出力させなければ正常に動作しているように見える
```
$ java Producer_Consumer_Problem
Producer: start
Consumer: start
Producer: ready - count:0
Consumer: use - count:0
Producer: ready - count:1
Consumer: use - count:1
Producer: ready - count:2
Consumer: use - count:2
```

### 参考文献
[https://docs.oracle.com/cd/E19455-01/806-2732/sync-31/index.html#:~:text=%E3%80%8C%E7%94%9F%E7%94%A3%E8%80%85%20%2F%20%E6%B6%88%E8%B2%BB%E8%80%85%E3%80%8D%E5%95%8F%E9%A1%8C%E3%81%AF%E3%80%81%E4%B8%A6%E8%A1%8C%E3%83%97%E3%83%AD%E3%82%B0%E3%83%A9%E3%83%9F%E3%83%B3%E3%82%B0,%E6%B6%88%E8%B2%BB%E8%80%85%E3%81%A8%E5%91%BC%E3%81%B3%E3%81%BE%E3%81%99%E3%80%82 docs.oracle.com - 「生産者 / 消費者」問題]
