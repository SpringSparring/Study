## 프로세스의 메모리 구성

- 코드 : 실행할 프로그램의 코드 저장
- 데이터 : 전역변수, 정적변수 저장
- 힙 : 동적으로 할당되는 메모리 영역
- 스택 : 매서드 호출 시 생성되는 지역변수와 반환주소 저장되는 영역

## 스레드

- 프로세스는 하나 이상의 스레드를 반드시 포함한다.
- 한 프로세스 내에 여러 스레드가 존재할 수 있다.
- 스레드는 프로세스보다 단순하기에 생성 및 관리가 단순하고 가볍다.

### 메모리 구성

- 공유 메모리 : 같은 프로세스의 코드,데이터,힙은 프로세스 안의 모든 스레드가 공유
- 개별 스택 : 각 스레드는 자신의 스택을 가짐.
- 초록색은 공유, 주황색은 각자 쓰는것  
  ![image](https://github.com/user-attachments/assets/12b70808-a504-489f-893d-a47dd7f67869)
- 코드가 위에서 아래로 실행되는 이 흐름이 뭘까?
  - 프로세스의 코드를 실행하는 흐름을 스레드 라고 한다!

### 스레드의 종류

- 단일 스레드 : 한 프로세스 내에 하나의 스레드만 존재
- 멀티 스레드 : 한 프로세스 내에 여러 스레드가 존재

### 멀티 스레드가 필요한 이유?

- 워드 프로그램으로 문서 편집 && 문서 자동으로 저장 && 맞춤법 검사
  - 스레드 1 : 문서 편집
  - 스레드 2 : 문서 자동 저장
  - 스레드 3 : 맞춤법 검사
- 유튜브 영상보는 동안 && 댓글달기
  - 스레드 1 : 영상 재생
  - 스레드 2 : 댓글

## 단일 코어 스케쥴링

- 운영체제가 어떻게 스레드를 스케쥴링하지?
- os는 내부에 스케쥴링 큐를 가짐, 각각의 스레드는 스케쥴링 큐에서 대기.
  ![image](https://github.com/user-attachments/assets/e45f62dd-09b4-4423-8979-f07f686076e9)
- 큐에서 스레드를 꺼내고 CPU를 통해 실행하고, 다시 스케쥴링 큐에 넣기 반복

## 멀티 코어 스케쥴링

- CPU 코어가 2개 이상이면 한번에 더 많은 스레드를 물리적으로 동시에 실행 가능
- 병렬로 스레드A,스레드B가 실행된다

## 자바 메모리 구조

- 메서드 영역
  - 프로그램을 실행하는데 필요한 공통 데이터 관리
  - 클래스 정보 : 클래스의 실해코드, 필드, 메서드와 생성자
  - static 영역 : static 변수들 보관
  - 런타임 상수 풀 : 공통 리터럴 상수 보관
- 스택 영역
  - 자바 실행 시 하나의 실행 스택 생성. 각 스택 프레임은 지역 변수, 중간 연산 결과, 메서드 호출 정보등 포함
    - 스택 프레임 : 스택 영역에 쌓이는 네모 박스가 하나의 스택 프레임
    - 메서드 호출할 때마다 스택 프레임이 생기고, 메서드 종료시 스택 프레임 제거
  - ✅ 정리 : 스레드 수 만큼 실행 스택이 생성됨!
- 힙 영역
  - 객체와 배열이 생성되는 영역
  - 가비지 컬렉션이 이루어지는 중요한 영역
  - 더이상 참조되지 않는 객체는 가비지 컬렉션에 의해 제거

## 자바에서 스레드를 어떻게 만들지?

- Thread 상속, Runnable 인터페이스 구현하기!

### Thread 상속

- 자바는 스레드도 객체로 다룬다.
- Thread.currentThread()호출시 해당 코드 실행하는 스레드 객체 조회
- Thread.currentThread().getName() : 실행 중인 스레드의 이름 조회

```
public class HelloThread extends Thread{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": run()");
    }
}
```

```
public class Main extends  Thread{
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+" : main() start");
        HelloThread helloThread = new HelloThread();
        //run은 절대 호출하면 안된다
        System.out.println(Thread.currentThread().getName()+" : start 호출 전");
        helloThread.start();
        System.out.println(Thread.currentThread().getName()+" : start 호출 후");
        System.out.println(Thread.currentThread().getName()+" : main() end");
    }
}
```

[실행결과]
main : main() start
main : start 호출 전
main : start 호출 후
main : main() end
Thread-0: run()

- main메서드는 main이라는 이름의 스레드가 실행. 프로세스가 실행하려면 메서드가 최소한 하나는 있어야함 -> 그래야 코드를 실행할 수 있다.
- start()메서드 호출 시 자바는 스레드를 위한 별도의 스택공간 할당
  - Thread-0의 정체는 무엇일까?
  - 스레드의 이름을 주지 않으면 자바는 스레드에 Thread-0 Thread-1 이렇게 임의로 할당.
  - Thread-0 스레드가 사용할 전용 스택 공간 마련!
  - Thread-0 스레드는 run()메서드의 스택 프레임을 스택에 올리면서 run()메서드 실행!
