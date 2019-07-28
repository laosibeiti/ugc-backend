package top.justdj.job.config.shiro;


import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import top.justdj.common.exception.TokenException;
import top.justdj.job.util.ResponseUtil;
import top.justdj.common.util.Result;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  {
        try {
            executeLogin(request, response);
            return true;
        }catch (TokenException e){
            log.error("没有检测到token 或者 token解析不到数据 ");
            try {
                //token无效
                ResponseUtil.responseReturn((HttpServletResponse) response,200, Result.noToken());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            try {
                //token过期
                ResponseUtil.responseReturn((HttpServletResponse) response,200,Result.tokenExpire());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    /**
     *executeLogin
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods","*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","*");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("vary","*");
        httpServletResponse.setStatus(200);
//        log.info("普通过滤器");
        //过滤掉 options 请求
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
       
        String jwt = httpServletRequest.getHeader("Authorization");
        if (jwt==null) {
            jwt = httpServletRequest.getHeader("token");
        }
        if (jwt == null) {
            throw new TokenException();
        }
        Claims claims = JwtHelper.parseJWT(jwt);
        if (claims == null || claims.get("user") == null) {
            throw new TokenException();
        }
        UsernamePasswordToken token=new UsernamePasswordToken((String) claims.get("email"),(String) claims.get("password"));
        getSubject(request,response).login(token);
        return true;
    }

}