package cn.cl.love.manager.controller;

import cn.cl.love.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/27 - 21:20
 */
@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public String test() {
        System.out.println("test");
        testService.insert();
        return "success";
    }
}
