## [1] 스프링 웹 개발 기초

- 정적 콘텐츠 : 서버에서 하는것없이 보여주는 페이지
- MVC와 템플릿 엔진 : 컨트롤러, 모델, 뷰 / 정적은 파일을 브라우저에 그대로 전달해줌
- API : json 데이터 포맷으로 클라이언트에게 데이터 전달

<br/>

### 1. 정적 콘텐츠

- 스프링부트는 정적콘텐츠를 제공함

```html
# resource/static/hello-static.html
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
정적 콘텐츠 입니다.
</body>
</html>
```
<br/>

### 2. MVC와 템플릿 엔진

- MVC : Model, View, Controller
- 컨트롤러랑 뷰를 분리한다.

```java
// HelloController.java
@GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
```

- 접속 : http://localhost:8080/hello-mvc?name=spring!
- name == spring! → ${name} == name

<br/>

### 3. API

- @ResponseBody 문자 반환

```java
// HelloController.java
@GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //"hello spring"
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;
        
        // 프로퍼티 접근 방식     
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
```

- 접속 : http://localhost:8080/hello-api?name=spring
- `@ResponseBody`를 사용하고, 객체를 반환하면 객체가 JSON으로 변환

- `@ResponseBody` 를 사용
    - HTTP의 BODY에 문자 내용을 직접 변환
    - `viewResolver` 대신에 `HttpMessageConverter` 가 동작
    - 기본 문자처리 : `StringHttpMessageConverter`
    - 기본 객체처리 : `MappingJackson2HttpMessageConverter`
    - byte 처리 등등 기타 여러 `HttpMessageConverter` 가 기본으로 등록되어 있음