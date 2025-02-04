## 팩토리 메서드

- AppConfig같이 외부에서 메서드 호출해서 객체가 생성되는 방식
- 객체 생성하는 책임을 팩토리라는 별도의 메서드나 클래스에서 처리
- 객체 생성 로직을 서브클래스에서 구현하도록 위임
- GoF디자인 패턴 중 하나

## 싱글톤 컨테이너

- 객체 인스턴스가 jvm에서 한개만 있어야 하는!
- 대부분의 스프링 애플리케이션은 웹 애플리케이션
  - 웹 애플리케이션은 보통 여러 고객이 동시에 요청한다!

```
package com.example.springexercise;

import com.example.springexercise.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = "+memberService1);
        System.out.println("memberService2 = "+memberService2);
        Assertions.assertThat(memberService1).isNotEqualTo(memberService2);
    }
}
```

- DI 컨테이너가 두개가 다르다는 것을 알 수 있다!

## 싱글톤 패턴

- 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴

```
package com.example.springexercise;

public class SingletonService {
    // static 영역에 1개만 올라감
    private static final SingletonService instance = new SingletonService();
    // getInstance() 매서드를 통해서만 조회!
    public static SingletonService getInstance(){
        return  instance;
    }
    private SingletonService(){

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }

}
```

```
package com.example.springexercise;

import com.example.springexercise.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = "+memberService1);
        System.out.println("memberService2 = "+memberService2);
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();
        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }
}
```

- 싱글톤 패턴을 쓰면 두개가 같다!
  - 즉 새로운 객체가 생기지 않는다!

## 싱글톤의 문제

- 구현이 복잡
- 구체 클래스에 의존하기에 DIP,OCP위반!
- 테스트 하기 어려움!

## 스프링은 싱글톤의 단점을 제거하고, 싱글톤으로 관리한다!

- 싱글톤 객체 생성하고 관리하는 것을 싱글톤 레지스트리
- 고객의 요청이 올때마다 객체를 만드는 것이 아닌 이미 만들어놓은 객체를 호출하기 때문에 효율적이다!
- 요청할때마다 새로운 객체를 만드는 방식도 있지만 대부분 싱글톤을 쓴다고 기억하자!

## 싱글톤 방식의 주의점

- 특정 클라이언트에 의존적인 필드가 있으면 안된다!
- stateless해야함
  - 클라이언트에 의존적이면 안된다!

## @configuration과 싱글톤

- @configuration은 싱글톤을 위해 존재한다!
- configuration과 바이트코드?

```
@Test
void configurationDeep(){
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    AppConfig bean = ac.getBean(AppConfig.class);
    System.out.println("bean : "+bean.getClass());
}

```

"bean : class com.example.springexercise.AppConfig$SpringCGLIB$$0"
-> CGLIB가 뭐지?

- 순수한 클래스라면 class com.example.springexercise.AppConfig 여야함!
  - 즉, 순수한 클래스가 아님!
  - 바이트코드 조작한 라이브러리를 사용해서 다른걸 만든것!
  - 이미 스프링 컨테이너에 스프링 빈이 존재하면 -> 이 스프링빈을 리턴
  - 그게 아니면 스프링 컨테이너에 등록 후 반환
- @configuration 안쓰고 @Bean만 쓰면?
  - 싱글톤이 보장되지 않는다!
  -
