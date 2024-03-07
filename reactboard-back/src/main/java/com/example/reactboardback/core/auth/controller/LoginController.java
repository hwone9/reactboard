package com.example.reactboardback.core.auth.controller;

import com.example.reactboardback.core.auth.service.LoginService;
import com.example.reactboardback.core.crypt.DigestUtil;
import com.example.reactboardback.core.exception.ServiceException;
import com.example.reactboardback.core.util.UserSessionUtil;
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
     *
     * @param param
     * @return
     */
    @PatchMapping("/auth/join")
    Map<String, Object> joinInfo(@RequestBody Map<String, Object> param) {
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
     *
     * @param paramMap 로그인정보
     * @param request  세선처리를 위한 HttpServletRequest
     * @return 로그인결과(세션정보)
     */
    @PostMapping("/auth/loginCheck")
    public Map<String, Object> loginCheck(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
        Map<String, Object> retrunMap = new HashMap<String, Object>();

        try {
            Map<String, Object> chkMap = loginService.loginChk(paramMap);

            if (chkMap.get("CODE") == "S") {
                retrunMap.put("RESULT", "SUCCESS");
//                jsonData.addFields("MODULE", chkMap.get("MODULE"));

            } else {
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
     *
     * @param paramMap 로그인정보
     * @param request  세션처리를 위한 HttpServletRequest
     * @return 로그인결과
     * @throws Exception
     */
    @PostMapping("/login")
    public Map<String, Object> actionLogin(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
        Map<String, Object> userinfo = null;
        Map<String, Object> retrunMap = new HashMap<>();
        try {
            userinfo = UserSessionUtil.getUserSession(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());
            retrunMap.put("redirect", "login");
            return retrunMap;
        }
        if (null != userinfo) {
            if (((String) paramMap.get("userid")).equals((String) userinfo.get("USER_ID"))) {
                retrunMap.put("RESULT", "SUCCESS");
                retrunMap.put("redirect", "main");
                return  retrunMap;
            }
        }

        try {

            Map<String, Object> userInfo = loginService.actionLogin(paramMap);
            boolean isValidUser = false;
            if (userInfo != null) {
                String userId = (String) userInfo.get("USER_ID");
                if (userId != null) {
                    isValidUser = true;
                }
            }

            //■■■■■■ SYS(내부) 로그인 구간
            if (userInfo.get("USER_TYPE").equals("SYS")) {
                if (isValidUser == true) {
//                    List<String> userRoleList = loginService.selectUserRoleListForLogin(paramMap);//권한조회
//                    if (0==userRoleList.size()) {
//                        try {
//                            insertAccessHist(_userId, "N", "메뉴권한 없음");
//                        } catch (Exception e) {
//                            logger.error(e.getMessage());
//                        }
//
//                        model.addAttribute("message", getMessage("M1000022"));
//                        return "loginView";
//                    }
//                    userInfo.put("ROLE_LIST", userRoleList);
//                    paramMap.put("ROLE_LIST", userRoleList);

                    UserSessionUtil.setUserSession(request, userInfo);

//                    try {
//                        insertAccessHist(_userId, "Y","");//로그저장
//                    } catch (Exception e) {
//                        logger.error(e.getMessage());
//                    }

                    retrunMap.put("RESULT", "SUCCESS");
                    retrunMap.put("redirect", "main");
                    return  retrunMap;

                } else {
//                    try {
//                        insertAccessHist(_userId, "N", "로그인 인증 실패");
//                    } catch (Exception e) {
//                        logger.error(e.getMessage());
//                    }

                    //model.addAttribute("message", getMessage("M1000022"));//

                    retrunMap.put("RESULT", "ERROR");
                    retrunMap.put("ERR_MSG", "로그인에 실패하였습니다. 자세한 내용은 관리자에게 문의하시기 바랍니다.");
                    retrunMap.put("redirect", "login");
                    return retrunMap;
                }
            } else {
                retrunMap.put("RESULT", "ERROR");
                retrunMap.put("ERR_MSG", "로그인에 실패하였습니다. 자세한 내용은 관리자에게 문의하시기 바랍니다.");
                retrunMap.put("redirect", "login");
                return retrunMap;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());

//            retrunMap.put("RESULT", "ERROR");
//            retrunMap.put("ERR_MSG", e.getMessage());

//            try {
//                insertAccessHist(_userId, "N", e.getMessage());
//            } catch (Exception e1) {
//                logger.error(e.getMessage());
//            }

            retrunMap.put("RESULT", "ERROR");
            retrunMap.put("ERR_MSG", e.getMessage());
            retrunMap.put("redirect", "login");
            return retrunMap;
        }

    }

}