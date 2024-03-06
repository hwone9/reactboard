package com.example.reactboardback.core.auth.controller;

import com.example.reactboardback.core.auth.service.LoginService;
import com.example.reactboardback.core.crypt.DigestUtil;
import com.example.reactboardback.core.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;

    /**
     * 회원가입
     * @param param
     * @return
     */
    @PatchMapping("/auth/join")
    Map<String,Object> joinInfo(@RequestBody Map<String,Object> param) {
        logger.debug("========= joinInfo  start =========");
        Map<String, Object> retrunMap = new HashMap<String, Object>();

        try {

            //패스워드 암호화
            String encryptedPwd = DigestUtil.digest((String) param.get("password"), (String) param.get("name"));

            if (("").equals(encryptedPwd)) {
                throw new ServiceException("비밀번호 암호화에 실패했습니다.");
            }

            logger.debug("password  : " + encryptedPwd);

            param.put("encryptedPwd", encryptedPwd);        //패스워드 암호화

            loginService.joinInfo(param);

            retrunMap.put("RESULT", "SUCCESS");
            retrunMap.put("STATUS", "INSERT");

        } catch (Exception e) {
            logger.error("회원가입 등록 오류", e);

            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());

        }

        return retrunMap;
    }


    /**
     * 로그인체크
     * @param paramMap 로그인정보
     * @param request 세선처리를 위한 HttpServletRequest
     * @return 로그인결과(세션정보)
     */
    @PostMapping("/auth/loginCheck")
    public Map<String, Object> loginCheck(@RequestBody Map<String, Object> paramMap , HttpServletRequest request){
        Map<String, Object> retrunMap = new HashMap<String, Object>();

        try {
            Map<String,Object> chkMap = loginService.loginChk(paramMap);

            if(chkMap.get("CODE") == "S" ) {
                retrunMap.put("RESULT", "SUCCESS");
//                jsonData.addFields("MODULE", chkMap.get("MODULE"));

            }else {
                retrunMap.put("RESULT", "ERROR");
                retrunMap.put("ERR_MSG", chkMap.get("MSG").toString());
//                jsonData.addFields("MODULE", "");

            }

        } catch (Exception e) {
            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());

        }
        return retrunMap;
    }


    /**
     * 로그인처리
     * @param paramMap 로그인정보
     * @param request 세션처리를 위한 HttpServletRequest
     * @return 로그인결과
     * @throws Exception
     */
    @PostMapping("/login")
    public String actionLogin(@RequestParam Map<String,Object> paramMap, HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttr) throws Exception {
        Map<String,Object> userinfo  = null;
        try {
            userinfo  = UserSessionUtil.getUserSession(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (null!=userinfo)	{
            if (((String)paramMap.get("USER_ID")).equals((String)userinfo.get("USER_ID"))) {
                return "redirect:/main.do";
            }
        }

        try {
            if( logger.isDebugEnabled()) {
                logger.debug("paramMap = " + paramMap);
                logger.debug("getRequestedSessionId = " + request.getRequestedSessionId());
            }

            Map<String,Object> userInfo = loginAuthService.actionLogin(paramMap);

            if( logger.isDebugEnabled()) {
                logger.debug("userInfo = " + userInfo);
            }
            boolean isValidUser = false;

            if (userInfo != null ) {
                String userId = (String)userInfo.get("USER_ID");

                if( userId != null ) {
                    isValidUser = true;
                }
            }

            //■■■■■■ SYS(내부) 로그인 구간
            if(userInfo.get("USER_TYPE").equals("SYS")){

                if( isValidUser == true ) {
                    List<String> userRoleList = loginAuthService.selectUserRoleListForLogin(paramMap);
                    if (0==userRoleList.size())
                    {
                        try {
                            insertAccessHist(_userId, "N", "메뉴권한 없음");
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }

                        model.addAttribute("message", getMessage("M1000022"));
                        return "loginView";
                    }

                    userInfo.put("ROLE_LIST", userRoleList);
                    //				userInfo.put("PUR_USER", "N");
                    //				if(userRoleList.contains("PUR002") || userRoleList.contains("PUR003") || userRoleList.contains("SYS001")) {
                    //					//구매롤 유저
                    //					userInfo.put("PUR_USER", "Y");
                    //				}

                    List<String> deptRoleList = loginAuthService.selectDeptRoleListForLogin(paramMap);
                    userInfo.put("DEPT_ROLE", deptRoleList);

                    paramMap.put("ROLE_LIST", userRoleList);
                    paramMap.put("MODULE_TYPE", _moduleType);
                    List<Map<String,Object>> userMenuList = loginAuthService.selectUserMenuList(paramMap);

                    List<Map<String,Object>> moduleList = loginAuthService.selectModuleList(paramMap);

                    LinkedHashMap<String,Object> topMenuLinkedMap = new LinkedHashMap<String,Object>();
                    LinkedHashMap<String,Object> leftMenuLinkedMap = new LinkedHashMap<String,Object>();
                    LinkedHashMap<String,Object> menuLinkedMap = new LinkedHashMap<String,Object>();

                    if( userMenuList != null ) {
                        String currTopMenuNo = "";


                        List<Map<String,Object>> childMenuList = null;
                        for( int i = 0, nSize = userMenuList.size(); i < nSize; i++) {
                            Map<String,Object> menuInfo = userMenuList.get(i);
                            String menuNo = StringUtils.defaultIfBlank((String)menuInfo.get("MENU_CD"), "");
                            if( ! "".equals(menuNo)) {
                                menuLinkedMap.put(menuNo, menuInfo);

                                BigDecimal bdMenuDepth = new BigDecimal(String.valueOf(menuInfo.get("MENU_DEPTH")));
                                if( bdMenuDepth.compareTo( new BigDecimal(1)) == 0 ) {
                                    if( ! currTopMenuNo.equals( menuNo )) {
                                        currTopMenuNo = menuNo;
                                        childMenuList = new ArrayList<Map<String,Object>>();
                                        menuInfo.put("CHILD_MENU_LIST", childMenuList);
                                        topMenuLinkedMap.put(menuNo, menuInfo);
                                    }
                                }else {
                                    if( bdMenuDepth.compareTo( new BigDecimal(2)) == 0 ) {
                                        if( ! currTopMenuNo.equals( menuNo )) {
                                            currTopMenuNo = menuNo;
                                            childMenuList = new ArrayList<Map<String,Object>>();
                                            menuInfo.put("CHILD_MENU_LIST", childMenuList);
                                            leftMenuLinkedMap.put(menuNo, menuInfo);
                                        }
                                    } else {
                                        if( childMenuList != null ) {
                                            childMenuList.add(menuInfo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    userInfo.put("BIZ_TOP_MENU_MAP", moduleList);
                    userInfo.put("TOP_MENU_MAP", topMenuLinkedMap);
                    userInfo.put("LEFT_MENU_MAP", leftMenuLinkedMap);
                    userInfo.put("MENU_MAP", menuLinkedMap);

                    List<Map<String,Object>> userFavoriteMenuList = loginAuthService.selectUserFavoriteMenuList(paramMap);
                    userInfo.put("FAVORITE_MENU", userFavoriteMenuList);

                    String srvType		=	"";
                    String srvTypeName	=	"";
                    if (PropertiesUtil.isRealMode()) {
                        srvType		=	"REAL";
                        srvTypeName =	"";
                    } else if (PropertiesUtil.isDevMode()) {
                        srvType		=	"DEV";
                        srvTypeName =	"개발서버";
                    } else {
                        srvType		=	"LOCAL";
                        srvTypeName =	"LOCAL SYSTEM";
                    }
                    userInfo.put("SRV_TYPE", srvType);
                    userInfo.put("SRV_TYPE_NM", srvTypeName);

                    UserSessionUtil.setUserSession(request, userInfo);

                    if( logger.isDebugEnabled()) {
                        logger.debug( "LOGIN_INFO =" + UserSessionUtil.getUserSession(request) );
                    }

                    try {
                        insertAccessHist(_userId, "Y","");
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }

                    //model.addAllAttributes(paramMap);
                    redirectAttr.addFlashAttribute(paramMap);

                    String fwdUrl = paramMap.get("FWD_URL")==null?"":(String)paramMap.get("FWD_URL");
                    if (!"".equals(fwdUrl)) {
                        return "redirect:/foward.do";
                    }

                    return "redirect:/main.do";

                } else {
                    try {
                        insertAccessHist(_userId, "N", "로그인 인증 실패");
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }

                    model.addAttribute("message", getMessage("M1000022"));
                    return "loginView";
                }
            }else {
                // ■■■■■■ VENDOR 로그인 구간

                if( isValidUser == true ) {
                    List<String> userRoleList = loginAuthService.selectUserRoleListForLogin(paramMap);
                    if (0==userRoleList.size())
                    {
                        try {
                            insertAccessHist(_userId, "N", "메뉴권한 없음");
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }

                        model.addAttribute("message", getMessage("M1000022"));
                        return "loginView";
                    }

                    userInfo.put("ROLE_LIST", userRoleList);
//					userInfo.put("PUR_USER", "N");
//					if(userRoleList.contains("PUR002") || userRoleList.contains("PUR003") || userRoleList.contains("SYS001")) {
//						//구매롤 유저
//						userInfo.put("PUR_USER", "Y");
//					}

                    List<String> deptRoleList = loginAuthService.selectDeptRoleListForLogin(paramMap);
                    userInfo.put("DEPT_ROLE", deptRoleList);

                    paramMap.put("ROLE_LIST", userRoleList);
                    paramMap.put("MODULE_TYPE", _moduleType);
                    List<Map<String,Object>> userMenuList = loginAuthService.selectUserMenuList(paramMap);

                    List<Map<String,Object>> moduleList = loginAuthService.selectModuleList(paramMap);

                    LinkedHashMap<String,Object> topMenuLinkedMap = new LinkedHashMap<String,Object>();
                    LinkedHashMap<String,Object> leftMenuLinkedMap = new LinkedHashMap<String,Object>();
                    LinkedHashMap<String,Object> menuLinkedMap = new LinkedHashMap<String,Object>();

                    if( userMenuList != null ) {
                        String currTopMenuNo = "";


                        List<Map<String,Object>> childMenuList = null;
                        for( int i = 0, nSize = userMenuList.size(); i < nSize; i++) {
                            Map<String,Object> menuInfo = userMenuList.get(i);
                            String menuNo = StringUtils.defaultIfBlank((String)menuInfo.get("MENU_CD"), "");
                            if( ! "".equals(menuNo)) {
                                menuLinkedMap.put(menuNo, menuInfo);

                                BigDecimal bdMenuDepth = new BigDecimal(String.valueOf(menuInfo.get("MENU_DEPTH")));
                                if( bdMenuDepth.compareTo( new BigDecimal(1)) == 0 ) {
                                    if( ! currTopMenuNo.equals( menuNo )) {
                                        currTopMenuNo = menuNo;
                                        childMenuList = new ArrayList<Map<String,Object>>();
                                        menuInfo.put("CHILD_MENU_LIST", childMenuList);
                                        topMenuLinkedMap.put(menuNo, menuInfo);
                                    }
                                }else {
                                    if( bdMenuDepth.compareTo( new BigDecimal(2)) == 0 ) {
                                        if( ! currTopMenuNo.equals( menuNo )) {
                                            currTopMenuNo = menuNo;
                                            childMenuList = new ArrayList<Map<String,Object>>();
                                            menuInfo.put("CHILD_MENU_LIST", childMenuList);
                                            leftMenuLinkedMap.put(menuNo, menuInfo);
                                        }
                                    } else {
                                        if( childMenuList != null ) {
                                            childMenuList.add(menuInfo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    userInfo.put("BIZ_TOP_MENU_MAP", moduleList);
                    userInfo.put("TOP_MENU_MAP", topMenuLinkedMap);
                    userInfo.put("LEFT_MENU_MAP", leftMenuLinkedMap);
                    userInfo.put("MENU_MAP", menuLinkedMap);

                    List<Map<String,Object>> userFavoriteMenuList = loginAuthService.selectUserFavoriteMenuList(paramMap);
                    userInfo.put("FAVORITE_MENU", userFavoriteMenuList);

                    String srvType		=	"";
                    String srvTypeName	=	"";
                    if (PropertiesUtil.isRealMode()) {
                        srvType		=	"REAL";
                        srvTypeName =	"";
                    } else if (PropertiesUtil.isDevMode()) {
                        srvType		=	"DEV";
                        srvTypeName =	"개발서버";
                    } else {
                        srvType		=	"LOCAL";
                        srvTypeName =	"LOCAL SYSTEM";
                    }
                    userInfo.put("SRV_TYPE", srvType);
                    userInfo.put("SRV_TYPE_NM", srvTypeName);
                    userInfo.put("LOGIN_TYPE", "일반");

                    /*
                     * Open 준비
                     * 개인정보 취급방침 동의, BP윤리서약서, SHE 서약 Session 담기
                     */
                    Map<String,Object> provChk = loginAuthService.selectUserProvChk(paramMap);

                    if( provChk != null){
                        userInfo.put("INFO_AGREE_YN",   StringUtil.nvl(provChk.get("INFO_AGREE_YN"),    "N"));
                        userInfo.put("VOW_YN",          StringUtil.nvl(provChk.get("VOW_YN"),           "N"));
                        userInfo.put("SHE_YN",          StringUtil.nvl(provChk.get("SHE_YN"),           "N"));

                        userInfo.put("VOW_TARGET_YN",   StringUtil.nvl(provChk.get("VOW_TARGET_YN"),    "N"));
                        userInfo.put("VOW_TARGET_APPR", StringUtil.nvl(provChk.get("VOW_TARGET_APPR"),  "N"));
                    }

                    UserSessionUtil.setUserSession(request, userInfo);

                    if( logger.isDebugEnabled()) {
                        logger.debug( "LOGIN_INFO =" + UserSessionUtil.getUserSession(request) );
                    }

                    try {
                        insertAccessHist(_userId, "Y","");
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }

                    return "redirect:/main.do";

                } else {
                    try {
                        insertAccessHist(_userId, "N", "로그인 인증 실패");
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
//					redirectAttr.addAttribute("message", getMessage("M1000022"));
//					return "redirect:/main.do";
                    model.addAttribute("message", getMessage("M1000022"));
                    return "loginView";
                }
            }

        } catch (BizException se) {
            logger.error(se.getExceptionCode());
            model.addAttribute("message",se.getExceptionCode());

            try {
                insertAccessHist(_userId, "N",se.getMessage());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return "loginView";
        } catch( Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("message", getMessage("M1000022"));
            try {
                insertAccessHist(_userId, "N", e.getMessage());
            } catch (Exception e1) {
                logger.error(e.getMessage());
            }
            return "loginView";
        }
    }

}