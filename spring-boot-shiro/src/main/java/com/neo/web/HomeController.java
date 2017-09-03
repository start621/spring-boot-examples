package com.neo.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Slf4j
@Controller
public class HomeController {

    private static final String CAPTCHA_SESSION_KEY = "verifyCode";

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @RequestMapping("/captcha")
    public void generateCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute(CAPTCHA_SESSION_KEY, createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    // @RequestMapping("/imgvrifyControllerDefaultKaptcha")
    // public ModelAndView imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
    //     ModelAndView andView = new ModelAndView();
    //     String captchaId = (String) httpServletRequest.getSession().getAttribute(CAPTCHA_SESSION_KEY);
    //     String parameter = httpServletRequest.getParameter(CAPTCHA_SESSION_KEY);
    //     System.out.println("Session  verifyCode "+captchaId+" form verifyCode "+parameter);
    //
    //     if (!captchaId.equals(parameter)) {
    //         andView.addObject("info", "错误的验证码");
    //         andView.setViewName("index");
    //     } else {
    //         andView.addObject("info", "登录成功");
    //         andView.setViewName("succeed");
    //
    //     }
    //     return andView;
    // }

    @RequestMapping({"/","/index"})
    public String index(){
        return"/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
        log.info("login controller stared, {}", this.getClass().getSimpleName());
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        log.info("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                log.error("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                log.error("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                log.error("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                log.error("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }

    @RequestMapping("/userInfoAdd")
    public String userAdd(HttpServletRequest request, Map<String, Object> map) throws Exception {
        log.info("add user controller stared, {}", this.getClass().getSimpleName());

        return "/userInfoAdd";
    }

    @RequestMapping("/userInfoDel")
    public String userDel(HttpServletRequest request, Map<String, Object> map) throws Exception {
        log.info("delete user controller stared, {}", this.getClass().getSimpleName());
        return "/userInfoDel";
    }

    @RequestMapping("/userInfo")
    public String userInfo(HttpServletRequest request, Map<String, Object> map) throws Exception {
        log.info("get all user controller stared, {}", this.getClass().getSimpleName());

        return "/userInfo";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "403";
    }

}