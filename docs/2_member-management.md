## 2. 회원 관리 예제 - 백엔드 개발

#### 비즈니스 요구사항 정리

데이터 : 회원ID, 이름 <br/>
기능 : 회원 등록, 회원 조회 <br/>
아직 데이터 저장소가 선정되지 않음 (가상의 시나리오) → DB 선정 X

<br/>

#### 일반적인 웹 애플리케이션 계층 구조
- 컨트롤러 : 웹 MVC의 컨트롤러 역할
- 서비스 : 핵심 비즈니스 로직 구현
- 리포지토리 : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
- 도메인 : 비즈니스 도메인 객체 (ex. 회원, 주문, 쿠폰 등 주로 데이터베이스에 저장하고 관리됨)

<br/>

#### 클래스 의존 관계
- 아직 데이터 저장소가 선정되지 않아서, 우선 인터페이스로 구현 클래스를 변경할 수 있도록 설계
- 데이터 저장소는 RDB, NoSQL 등 다양한 저장소를 고민 중인 상황으로 가정
- 개발을 진행하기 위해서 초기 개발 단계에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용

<br/>

### 회원 리포지토리 메모리 구현체 테스트

‘src/test/java’ 하위 폴더에 생성한다.

- 하나의 테스트가 끝날 때마다 데이터를 초기화시켜줘야 한다.
```java
// MemoryMemberRepository.java
public void clearStore() {
    store.clear();
}

// MemoryMemberRepositoryTest.java
@AfterEach
public void afterEach() {
    repository.clearStore();
}

```

<br/>

### DI (Dependency Injection)

의존 관계 주입

- 객체가 의존하는 또 다른 객체를 외부에서 선언하고 이를 주입받아 사용하는 것이다.
- 의존성 ↓, 재사용성 ↑, 가독성 ↑, 테스트 편리 ↑
- 생성자 이용

```java
// MemberService.java
private final MemberRepository memberRepository;
public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
}

// MemberServiceTest.java
MemberService memberService;
MemoryMemberRepository memberRepository;

@BeforeEach
public void beforeEach() {
    memberRepository = new MemoryMemberRepository();
    memberService = new MemberService(memberRepository);
} 
```
