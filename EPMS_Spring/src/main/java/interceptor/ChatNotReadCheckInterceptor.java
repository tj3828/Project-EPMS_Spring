package interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hb.account.AccountVO;
import com.hb.chat.IChatService;

public class ChatNotReadCheckInterceptor extends HandlerInterceptorAdapter {
	
	private Log log = LogFactory.getLog(LoginCheckInterceptor.class);
	
	@Inject
	IChatService chatService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		AccountVO vo = (AccountVO)request.getSession().getAttribute("dto");
		if(vo != null) {
			int cnt = chatService.selectNotReadCheck(vo);
			int reservationNotReadCount = chatService.selectReservationNotReadCheck(vo);
			modelAndView.addObject("notReadCount", cnt);
			modelAndView.addObject("reservationNotReadCount", reservationNotReadCount);
		}
	}

	
}
