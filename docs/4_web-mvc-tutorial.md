## 4. 회원 관리 예제 - 웹 MVC 개발

## 1️⃣ 회원 웹 기능 - 홈 화면 추가

#### 홈 컨트롤러

```java
package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
```

<br/>

#### 회원 관리용 홈

```html
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div class="container">
    <div>
        <h1>Hello Spring</h1>
        <p>회원 기능</p>
        <p>
            <a href="/member/new">회원 가입</a>
            <a href="/member">회원 목록</a>
        </p>
    </div>
</div>
</body>
</html>
```

welcome page (정적 콘텐츠)가 있는데 왜 home.html이 우선 순위가 높을까?

→ 요청이 오면 스프링 컨트롤러가 “관련 컨트롤러가 있는지 먼저 찾고” 없으면 정적 파일을 찾도록 되어 있다.

<br/>

## 2️⃣ 회원 웹 기능 - 등록

#### 회원 등록 폼 컨트롤러
```java
package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "/members/createMemberForm";
    }

    @PostMapping("memberes/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }
}
```
<br/>

#### 회원 등록 폼 HTML (resources/templates/members/createMemberForm)
```html
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>회원 등록</title>
</head>
<body>
    <div class="container">
        <form action="/members/new" method="post">
            <div class="form-group">
                <label for="name">이름</label>
                <input type="text" id="name" name="name" placeholder="이름을 입력하세요.">
            </div>
            <button type="submit">등록</button>
        </form>
    </div>
</body>
</html>
```

|     구분     |                 GET                  |             POST              |
|:----------:|:------------------------------------:|:-----------------------------:|
| 데이터 전송 방식  | URL의 쿼리 스트링을 통해 데이터 전송 (?key=value)  |  요청의 Body(본문)에 데이터를 포함하여 전송   |
|     보안     |         보안에 취약 (URL에 데이터 노출)         |     보안에 강함 (데이터가 본문에 포함)      |
|     캐싱     |        가능 (브라우저, 프록시 서버에서 저장)        |              불가능              |
| 데이터 길이 제한  |       URL 길이 제한 있음 (2048자 이하)        |           길이 제한 없음            |
|  주요 사용 예시  |        데이터 조회 (검색, 리스트 조회 등)         | 데이터 생성, 수정, 삭제 (회원가입, 로그인 등)  |

<br/>

## 3️⃣ 회원 웹 기능 - 조회

#### 회원 컨트롤러에서 조회 기능

```java
// MemberController
@GetMapping("/members")
public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
}
```
<br/>

#### 회원 리스트 HTML

```html
<!doctype html>
<html xmlns:th="http://www.thymeleaf.org">
<body>
    <div class="container">
        <div>
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>이름</th>
                </tr>
                </thead>
                <tbody>
                <tr th:each="member : ${members}">
                    <td th:text="${member.id}"></td>
                    <td th:text="${member.name}"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
```

- 현재 데이터는 메모리에 저장되기 때문에 빌드를 다시 하면 데이터가 전부 사라짐