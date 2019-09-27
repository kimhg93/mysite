package kr.co.itcen.mysite.action.guestbook;

import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		// actionName = "joinform"
		// actionName + "Action" -> 객체를 생성하는 코드
		// String 으로 된 클래스 이름으로 동적객체 생성
		// 
		Action action = null;
		
		if("add".equals(actionName)) {
			action = new AddAction();			
		} else if("deleteform".equals(actionName)) {
			action = new DeleteFormAction();	
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();	
		} else {
			action = new ListAction();
		}
		return action;
	}

}
