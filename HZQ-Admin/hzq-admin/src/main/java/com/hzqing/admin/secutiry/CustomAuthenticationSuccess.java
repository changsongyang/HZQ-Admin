package com.hzqing.admin.secutiry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzqing.common.constant.Constants;
import com.hzqing.common.jwt.JwtTokenUtil;
import com.hzqing.common.util.AESUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author hzqing
 * @date 2018/10/27 19:24
 */
@Component
public class CustomAuthenticationSuccess extends SavedRequestAwareAuthenticationSuccessHandler {
    // object 对象转json
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param request
     * @param response
     * @param authentication 代表认证成功之后的信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //生成jwt
        Map<String, Object> claims = new HashMap<String, Object>();
        // 获取用户登陆信息
        User user = (User) authentication.getPrincipal();

        // CustomUserDetails user = (CustomUserDetails) principal;
        //Claims包含您想要签署的任何信息
        String iss = AESUtil.AESEncode(Constants.AES_SECRET,"id"+","+user.getUsername()+","+user.getPassword());
        claims.put("iss",iss); //jwt的签发者 保存用户的帐号和密码以及id 使用AES对称加密
        claims.put("sub",user.getUsername()); // JWT所面向的用户 用户的昵称
        claims.put("iat", new Date());
        claims.put("jti", UUID.randomUUID()); //jwt的唯一身份表示
        // 生成token
        String token = JwtTokenUtil.generateToken(claims, Constants.JWT_SECRET, Constants.JWT_EXPIRATION);


        response.addHeader("Authorization","Bearer " + token);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers ","Authorization,Access-Control-Allow-Origin");

        Map res = new HashMap();
        res.put("code",200);
        res.put("Authorization","Bearer " + token);
        res.put("timestamp",System.currentTimeMillis());
        String json = new ObjectMapper().writeValueAsString(res);
        response.setContentType("application/josn");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
