## 컴포넌트 스캔과 의존관계 자동주입

- 자동으로 빈을 등록하는 컴포넌트 스캔
- 의존관계를 자동으로 주입하는 @Autowired
- 컴포넌트 스캔은 @ComponentScan붙여주면 된다

```
@Autowired
public MemberServiceImpl(MemberRepository memberRepository){
    this.memberRepository = memberRepository;
}
@Override
public void join(Member member) {
    memberRepository.save(member);

}
```

- @Autowired 는 ac.getBean(MemberRepository.class) 과 같은 기능을 한다!

## @ComponentScan

- @component 붙은 애를 스프링 빈으로 등록
- 스프링 빈의 기본이름은 클래스 이름을 소문자로만 바꿔서 등록

## @Autowired

- 스프링 컨테이너에서 타입이 같은걸 찾아서 주입

## 컴포넌트 스캔의 탐색 위치와 기본 스캔 대상

- @ComponentScan을 단 클래스가 위치한 패키지가 기본 스캔대상이 된다!
- Config클래스를 최상단에 두는 것을 권장!
- @SpringBootApplication이 있는곳에 Config파일을 두는게 관례
  - 불필요한건 exclude로 빼면됨
- 기본 스캔대상
  - @Component
  - @Repository
  - @Controller
  - @Configuration
- @Service에는 @Component가 붙어 있는데?
  - 애노테이션은 상속기능이 없다
  - 특정 애노테이션이 들고 있다는 것은 자바언어가 지원하는게 아니라 스프링이 지원하는 것
- 애노테이션이 있으면 부가기능 수행
  - @Controller : MVC컨트롤러로 인식
  - @Repository : 데이터 계층의 예외를 스프링 예외로 변환
  - @Configuration : 스프링 설정 정보
  - @Service : 특별한 처리 안함. 개발자들이 핵심 비즈니스 로직이 여기 있겠구나 생각

### useDefaultFilters

- 기본으로 켜져 있는데 이걸 끄면 기본 스캔 대상에서 제외 됨!

## 필터

- include filter -> 컴포넌트 스캔에 추가
- exclude filter -> 컴포넌트 스캔에 제외
- FilterType 옵션
  - annotation이 기본값
  - assignable type : 지정한 타입과 자식 타입 인식
  - custom : TypeFIlter라는 인터페이스 구현해서 처리

## 중복 등록과 필터

- 컴포넌트 스캔에서 같은 빈 이름 등록 하면?
  - 자동 빈 등록 vs 자동 빈 등록
    - 스프링이 오류 발생 시킴
  - **자동 빈 등록 vs 수동 빈 등록**
    - 수동 등록 빈이 우선순위 가짐
      - 오버라이딩 해버림!
      - 수동 빈 등록시 로그가 남는다!
