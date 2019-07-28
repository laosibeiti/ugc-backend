/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.4
  Time: 15:09
  Info:
*/

package top.justdj.job.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.4
 * Time: 15:09
 */

@Slf4j
public class CorsFilter implements Filter {
	
	final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) res;
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Methods","*");
		httpServletResponse.setHeader("Access-Control-Allow-Headers","*");
		httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setStatus(200);
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			return ;
		}else {
			chain.doFilter(req, res);
		}
	}
	
	
	@Override
	public void init(FilterConfig filterConfig) {}
	
	
	@Override
	public void destroy() {}
}
