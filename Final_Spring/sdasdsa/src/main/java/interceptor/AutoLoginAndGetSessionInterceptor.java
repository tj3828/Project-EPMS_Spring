package interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.hb.account.AccountVO;
import com.hb.account.login.ILoginService;

public class AutoLoginAndGetSessionInterceptor extends HandlerInterceptorAdapter{

	@Inject
	ILoginService loginService; 
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// auto Login
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println("a:" + id);
		if(id == null) {
			Cookie cookie = WebUtils.getCookie(request, "autoLogin");
			if(cookie != null && cookie.getValue().equals("true")) {
				System.out.println("b:" + cookie.getValue());
				AccountVO dto = new AccountVO();
				id = WebUtils.getCookie(request, "id").getValue();
				dto.setId(id);
				System.out.println("c:" + id);
				dto = loginService.autoLoginReq(dto);
				session.setAttribute("id", id);
				session.setAttribute("nickname", dto.getNickname());
			}
		}
		
		return true; 
	}	
}
