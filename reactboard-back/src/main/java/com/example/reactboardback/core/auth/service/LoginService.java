package com.example.reactboardback.core.auth.service;

import com.example.reactboardback.core.auth.mapper.LoginMapper;
import com.example.reactboardback.core.crypt.DigestUtil;
import com.example.reactboardback.core.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LoginService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DigestUtil digestUtil;

    private final LoginMapper loginMapper;

    /**
     * 회원가입
     * @param paramMap
     * @throws Exception
     */
    public void joinInfo(Map<String,Object> paramMap) throws Exception {

            Map<String, Object> insertParamMap = new HashMap<String, Object>();
            insertParamMap = paramMap;
            insertParamMap.remove("password");		//암호화된 패스워드 설정하기위해 화면에서 넘겨받은 값 삭제
            insertParamMap.put("password", paramMap.get("encryptedPwd"));		//암호화된 패스워드 설정

            //가입 정보 입력
            int insertVndInfoCnt = loginMapper.joinInfo(insertParamMap);

            if(insertVndInfoCnt == 1){

            }else{
                throw new ServiceException("가입정보(협력업체 정보) 입력 시 오류 발생하였습니다.");
            }

            //사용자 권한 정보 입력
//            int insertRepUserRoleCnt = loginMapper.insertRepUserRole(insertParamMap);
//            if(insertRepUserRoleCnt == 1){
//            }else{
//                throw new ServiceException("가입정보(사용자 권한 정보) 입력 시 오류 발생하였습니다.");
//            }

    }


    /**
     * 로그인체크
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> loginChk(Map<String,Object> paramMap) throws Exception {

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("CODE", "S");
        boolean pass = true;

        Map<String,Object> userInfo = loginMapper.selectUserLoginInfo(paramMap);

        String loginUserid = (String) paramMap.get("userid");
        String loginUserpw = (String) paramMap.get("userpw");
        String userId = "";//db user id
        String userPw = "";//db user password

        if( userInfo != null ) {
            if( !StringUtils.isEmpty( (String)userInfo.get("USER_ID") ) ){
                userId = (String)userInfo.get("USER_ID");
            }

            if( !StringUtils.isEmpty( (String)userInfo.get("PASSWORD") ) ){
                userPw = (String)userInfo.get("PASSWORD");//DB에 저장된비밀번호
            }

            //사용자 아이디 체크
            if( "".equals(userId) ) {
                logger.error( "User Not Found Error : USER_ID is NULL");
                resultMap.put("CODE", "E");
                resultMap.put("MSG", "존재하지 않는 사용자입니다.");
            } else {
                //암호 체크
                if( digestUtil.matches(loginUserpw, userPw, userId ) == false) {
                    String encryptedLoginPwd = digestUtil.digest(loginUserpw, userId);
                    logger.error( "passwd not equals = " + encryptedLoginPwd);
//                    if(PropertiesUtil.isRealMode()) {
                        pass = false;
                        resultMap.put("CODE", "E");
                        resultMap.put("MSG", "비밀번호가 맞지 않습니다.");
                        //로그인실패횟수 증가 //TODO
                        //계정잠금 설정 //TODO
//                    }
                } else {
                    //로그인정보 확인 clear
                    resultMap.put("CODE", "S");
                }
            }

        } else {
            logger.error( "User Not Found Error : USER_ID is NULL");
            resultMap.put("CODE", "E");
            resultMap.put("MSG", "존재하지 않는 사용자입니다.");
        }

//        if(pass) {
//            resultMap.put("MODULE", loginMapper.selectModuleList(paramMap));
//        }

        return resultMap;
    }

}