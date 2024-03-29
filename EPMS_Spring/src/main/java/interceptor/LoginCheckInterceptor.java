package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hb.account.AccountVO;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private Log log = LogFactory.getLog(LoginCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		AccountVO vo = (AccountVO)session.getAttribute("dto");
		if(vo == null) {
			response.sendRedirect("../login/login.do");
			return false;
		}
		return true;
	}
	
	
}
