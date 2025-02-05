## 6. AOP

## 1️⃣ AOP가 필요한 상황

- 모든 메소드의 호출 시간을 측정하고 싶다면?
- 공통 관심 사항 (cross-cutting concern) vs 핵심 관심 사항 (core concern)

<br/>

#### 문제
- 회원 가입, 회원 조회가 핵심 관심 사항이지, 시간을 측정하는 기능은 핵심 관심 사항이 아니다.
- 시간을 측정하는 로직은 공통 관심 사항이다.
- 시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 유지보수가 어렵다.
- 시간을 측정하는 로직은 별도의 공통 로직으로 만들기 매우 어렵다.
- 시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.

<br/>

## 2️⃣ AOP : Aspect Oriented Programming

- 공통 관심 사항 (cross-cutting concern) vs 핵심 관심 사항 (core concern) 분리

**시간 측정 AOP 등록**

```java
package hello.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {
    @Around("execution(* hello.hello_spring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
```
- `@Component`는 SpringConfig에 `@Bean`으로 등록해도 된다.

<br/>

#### 해결

- 회원 가입, 회원 조회 등 핵심 관심 사항과 시간을 측정하는 공통 관심 사항을 분리한다.
- 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
- 핵심 관심 사항을 깔끔하게 유지할 수 있다.
- 변경이 필요하면 이 로직만 변경하면 된다.
- 원하는 적용 대상을 선택할 수 있다.

<br/>

## 3️⃣ 스프링의 AOP 동작 방식 설명

- **AOP 적용 전 의존 관계** : `memberControlle`r → `memberService`

- **AOP 적용 후 의존 관계** : `memberController` → `프록시 memberService` —(joinPoint.proceed())—> `실제 memberService`