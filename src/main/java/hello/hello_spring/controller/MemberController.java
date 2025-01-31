package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired // 스프링 컨테이너에 있는 memberService에 있는걸 갖다 쓰게 해줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
