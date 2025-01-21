# 기본 annotion 개념 정리

## 1. Bean

### 정의

Bean은 스프링 컨테이너에 의해 관리되는 객체를 지칭. 애플리케이션의 구성 요소로 사용되며, 객체 간의 의존성을 스프링 컨테이너가 관리

### 주요 특징

- 스프링 빈은 `@Bean` 어노테이션이나 XML 설정 파일을 통해 정의
- 스프링은 애플리케이션 실행 시 빈을 생성하고 의존성을 주입하며, 생명 주기를 관리

### 사용하지 않을 경우

- 객체를 생성하고 관리하는 책임을 개발자가 전적으로 담당해야 함
- 여러 클래스에서 동일 객체를 사용하려면 직접 싱글턴 패턴을 구현해야 함

### 사용했을 때의 장점

- 스프링이 객체의 생성과 관리를 담당하므로 개발자는 비즈니스 로직에만 집중 가능
- 코드 간 결합도를 낮춰 테스트와 유지보수가 용이

### 생성 방법

1. **Java Config 방식**:

   ```java
   @Configuration
   public class AppConfig {
       @Bean
       public MyService myService() {
           return new MyService();
       }
   }
   ```

   - 위 방식은 자바 코드에서 설정을 직접 관리하므로 가독성과 유지보수성이 높아짐

2. **XML 설정 방식**:
   ```xml
   <bean id="myService" class="com.example.MyService" />
   ```
   - XML 방식은 설정 파일이 분리되어 있어 코드와 구성이 명확히 나뉘는 장점이 있음

---

## 2. Configuration

### 정의

`@Configuration`은 스프링에서 설정 클래스임을 나타내는 어노테이션. 이 어노테이션이 붙은 클래스는 빈 정의 및 애플리케이션 설정 정보를 제공

### 주요 특징

- 구성 파일 역할로 Java 기반 설정을 통해 XML 구성 파일을 대체
- `@Bean` 어노테이션과 연계하여 메소드에서 정의한 객체를 스프링 빈으로 등록

### 장점

- 설정 클래스를 자바 코드로 작성하므로 IDE 지원을 받을 수 있어 생산성 향상
- Java Config 방식으로 설정을 작성하면 타입 안전성과 가독성 증가

### 예시

```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyService();
    }

    @Bean
    public MyRepository myRepository() {
        return new MyRepository();
    }
}
```

---

## 3. Dependency Injection (DI)

### 정의

의존성 주입(DI)은 객체 간의 의존 관계를 애플리케이션 코드가 아닌 스프링 컨테이너가 처리하는 디자인 패턴

### 주요 특징

- 객체를 생성하고 관리하는 책임을 스프링 컨테이너에 위임
- 애플리케이션의 결합도를 낮추고, 테스트 및 유지보수가 용이

### 장점

- 의존 관계를 외부에서 주입받아 코드를 더 유연하고 확장 가능하게 만듦
- 객체 간 결합도를 낮춰 단위 테스트 작성 용이

### DI 방식

1. **생성자 주입**:

   ```java
   @Service
   public class MyService {
       private final MyRepository myRepository;

       public MyService(MyRepository myRepository) {
           this.myRepository = myRepository;
       }
   }
   ```

   - 의존 관계를 명확히 정의하므로 테스트와 유지보수에 적합

2. **필드 주입**:

   ```java
   @Service
   public class MyService {
       @Autowired
       private MyRepository myRepository;
   }
   ```

   - 간단한 구현 가능하지만 의존 관계가 숨겨져 명시적이지 않음

3. **Setter 주입**:

   ```java
   @Service
   public class MyService {
       private MyRepository myRepository;

       @Autowired
       public void setMyRepository(MyRepository myRepository) {
           this.myRepository = myRepository;
       }
   }
   ```

   - 선택적 의존성을 주입할 때 유용

---

## 4. @RestController

### 정의

`@RestController`는 스프링 MVC에서 RESTful 웹 서비스를 개발하기 위해 사용하는 어노테이션. `@Controller`와 `@ResponseBody`를 결합한 형태로, JSON이나 XML 형식의 데이터를 반환

### 주요 특징

- 웹 요청을 처리하고 JSON 또는 XML 형태로 응답
- HTTP 요청 메소드(GET, POST 등)를 처리하는 메소드를 선언 가능

### 장점

- 간결한 선언으로 RESTful 서비스를 개발 가능
- 데이터 응답 처리가 간단하고 직관적

### 사용 예시

```java
@RestController
public class MyController {

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello, World!";
    }
}
```

---

## 5. @Bean vs @Component

| **특징**         | **@Bean**                                   | **@Component**                            |
| ---------------- | ------------------------------------------- | ----------------------------------------- |
| **용도**         | 메소드에서 반환한 객체를 스프링 빈으로 등록 | 클래스를 스프링 빈으로 등록               |
| **주 사용 방식** | Java 기반 설정 파일에서 사용                | 클래스에 어노테이션을 직접 붙여 사용      |
| **사용 위치**    | `@Configuration` 클래스 내에서 사용         | 스프링 컴포넌트 스캔 대상 클래스에서 사용 |
| **예시**         | `@Bean public MyService myService() {...}`  | `@Component public class MyService {...}` |

---

## 6. Spring의 주요 어노테이션 요약

| **어노테이션**    | **설명**                                     |
| ----------------- | -------------------------------------------- |
| `@Component`      | 일반적인 스프링 빈을 등록                    |
| `@Service`        | 서비스 레이어의 스프링 빈을 등록             |
| `@Repository`     | 데이터 액세스 레이어(DAO)의 스프링 빈을 등록 |
| `@Controller`     | 웹 요청을 처리하는 컨트롤러로 사용           |
| `@RestController` | RESTful 웹 서비스의 컨트롤러로 사용          |
| `@Autowired`      | 의존성 주입을 위해 사용                      |
| `@Configuration`  | 스프링 설정 클래스를 정의                    |
| `@Bean`           | 메소드의 반환 객체를 스프링 빈으로 등록      |
| `@RequestMapping` | 특정 URL과 요청 메소드를 매핑                |
| `@GetMapping`     | HTTP GET 요청을 매핑                         |
| `@PostMapping`    | HTTP POST 요청을 매핑                        |

---
