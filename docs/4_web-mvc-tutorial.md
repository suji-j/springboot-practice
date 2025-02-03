## 4. 회원 관리 예제 - 웹 MVC 개발

#### [1] 회원 웹 기능 - 홈 화면 추가

홈 컨트롤러

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

회원 관리용 홈

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