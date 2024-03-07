package com.example.reactboardback.core.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * Spring에서 제공하는 RequestContextHolder 를 이용하여
 * request 객체를 service까지 전달하지 않고 사용할 수 있게 해줌
 * </pre>
 */

public class UserSessionUtil {

	private static final Logger logger = LoggerFactory.getLogger(UserSessionUtil.class);
	
	/**
	 * 사용자 session 정보
	 *
	 * @param request  attribute key name
	 * @return Object attribute obj
	 */
	public static Map<String,Object> getUserSession(HttpServletRequest request) throws Exception {

		try {
			HttpSession session = request.getSession();

			if( session != null ) {
				return getUserSession(session);
			} else {
				return null;
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
	}

	/**
	 * 사용자 session 정보
	 *
	 * @param session  attribute key name
	 * @return Object attribute obj
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getUserSession(HttpSession session)  throws Exception {

		try  {
			if( session != null ) {
		/*		if( PropertiesUtil.isLocalMode()  ) {
					return (Map<String,Object>)(session.getServletContext().getContext(PropertiesUtil.getProperty("SESSION_CONTEXT")).getAttribute( session.getId()) );
		
				}  else {
					return (Map<String,Object>)(session.getAttribute("LOGIN_INFO"));
				}*/
				return (Map<String,Object>)(session.getAttribute("LOGIN_INFO"));
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	/**
     * 사용자 session 정보 설정
     *
     * @param request  attribute key name
     * @param userInfo  attribute obj
     * @return void
     */
	public static void setUserSession(HttpServletRequest request, Object userInfo) {
		//if( PropertiesUtil.isLocalMode() ) {
			request.getSession().setAttribute("LOGIN_INFO", userInfo);
		//}
	}

  
   /**
    * 사용자 session 정보 삭제
    *
    * @param request  attribute key name
    * @return void
    */
	public static void removeUserSession(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		if( session != null ) {
			removeUserSession(session);
		}

	}
	

   /**
    * 사용자 session 정보 삭제
    *
    * @param session  attribute key name
    * @return void
    */
	public static void removeUserSession(HttpSession session) {

		try {
			if( session != null ) {
				String sessionId = session.getId();

				Map<String,Object> userInfo = getUserSession(session);
				if( userInfo != null ) {
					userInfo.clear();
					userInfo = null;
					session.invalidate();

//					if( PropertiesUtil.isLocalMode() ) {
//					   //session.getServletContext().getContext(PropertiesUtil.getProperty("SESSION_CONTEXT")).removeAttribute("sessionId");
//
//					   if( logger.isDebugEnabled() ) {
//						   logger.debug("SESSIONN : " + sessionId + " Removed");
//					   }
//					}  else {
//						logger.debug("SESSIONN : " + sessionId + " Removed");
//						/*내용없음*/
//						// 처리 사항 없음
//						//session.removeAttribute("LOGIN_INFO");
//					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			//throw e;
		}
	}
	
	/**
     * 서버 포트 session 정보 설정
     *
     * @param request  attribute key name
     * @param info  attribute obj
     * @return void
     */
	public static void setSvrPortSession(HttpServletRequest request, Object info) {
		request.getSession().setAttribute("SVR_PORT_LIST", info);
	}
	
	/**
	 * 서버 포트 session 정보
	 *
	 * @param request  attribute key name
	 * @return Object attribute obj
	 */
	public static List<Map<String, Object>> getSvrPortSession(HttpServletRequest request) throws Exception {

		try {
			HttpSession session = request.getSession();

			if( session != null ) {
				return getSvrPortSession(session);
			} else {
				return null;
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
	}

	/**
	 * 서버 포트  session 정보
	 *
	 * @param session  attribute key name
	 * @return Object attribute obj
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getSvrPortSession(HttpSession session)  throws Exception {

		try  {
			if( session != null ) {
				return (List<Map<String, Object>>)(session.getAttribute("SVR_PORT_LIST"));
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	/**
     * 메세지 session 정보 설정
     *
     * @param request  attribute key name
     * @param info  attribute obj
     * @return void
     */
	public static void setMsgSession(HttpServletRequest request, Object info) {
		request.getSession().setAttribute("SVR_MSG_LIST", info);
	}
	
	/**
	 * 메세지 session 정보
	 *
	 * @param request  attribute key name
	 * @return Object attribute obj
	 */
	public static List<Map<String, Object>> getMsgSession(HttpServletRequest request) throws Exception {

		try {
			HttpSession session = request.getSession();

			if( session != null ) {
				return getMsgSession(session);
			} else {
				return null;
			}
		} catch(Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
	}

	/**
	 * 메세지  session 정보
	 *
	 * @param session  attribute key name
	 * @return Object attribute obj
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getMsgSession(HttpSession session)  throws Exception {

		try  {
			if( session != null ) {
				return (List<Map<String, Object>>)(session.getAttribute("SVR_MSG_LIST"));
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
}
