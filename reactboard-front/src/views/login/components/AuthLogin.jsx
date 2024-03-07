import React, { useEffect, useState } from 'react';
import { Box, Typography, FormGroup, FormControlLabel, Button, Stack, Checkbox, TextField } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import * as util from "src/js/Util";

const AuthLogin = ({ title, subtitle, subtext }) => {
    const navigator = useNavigate();
    const [loginInfo , setLoginInfo] = useState({
        userid: '',
        userpw: ''
    });

    const onClick = () => {
        if(!loginInfo.userid){
            alert('아이디 입력!!');
            return;
        }

        if(!loginInfo.userpw){
            alert('비밀번호 입력!!');
            return;
        }
        
        loginCheck();
    }

    const loginCheck = async ()=>{
        // $("#ENC_USER_ID").val( rsaEnc(RSAmodulus , Exponent , userId) );
        // $("#ENC_USER_PWD").val( rsaEnc(RSAmodulus , Exponent , userPwd) );
        
        let resp = await util.process("post", `/auth/loginCheck`, loginInfo);
        console.log(resp);
        if (resp.RESULT === "SUCCESS") {
            // resp = resp.resData;
            // alert("로그인성공");
            getLogin(loginInfo);
        } else {
            alert(resp.ERR_MSG);
        }
    
    }

    const getLogin = async (param) => {
        let resp = await util.process("post", `/login`, param);
        console.log(resp);
        if (resp.RESULT === "SUCCESS") {
            // resp = resp.resData;
            // alert("로그인성공");
            sessionStorage.setItem("userid", loginInfo.userid);
            navigator("/board");
        } else {
            alert(resp.ERR_MSG);
        }
    };

    useEffect(()=>{
        // getLogin();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[]);

    return (
        <>
            {title ? (
                <Typography fontWeight="700" variant="h2" mb={1}>
                    {title}
                </Typography>
            ) : null}

            {subtext}

            <Stack>
                <Box>
                    <Typography variant="subtitle1"
                        fontWeight={600} component="label" htmlFor='username' mb="5px">Username</Typography>
                    <TextField id="username" variant="outlined" fullWidth value={loginInfo.userid}
                                onChange={(event)=>{
                                    setLoginInfo({...loginInfo, userid:event.target.value})
                                }}/>
                </Box>
                <Box mt="25px">
                    <Typography variant="subtitle1"
                        fontWeight={600} component="label" htmlFor='password' mb="5px" >Password</Typography>
                    <TextField id="password" type="password" variant="outlined" fullWidth value={loginInfo.userpw}
                                onChange={(event)=>{
                                    setLoginInfo({...loginInfo, userpw:event.target.value})
                                }}/>
                </Box>
                <Stack justifyContent="space-between" direction="row" alignItems="center" my={2}>
                    <FormGroup>
                        <FormControlLabel
                            control={<Checkbox defaultChecked />}
                            label="Remeber this Device"
                        />
                    </FormGroup>
                    <Typography
                        component={Link}
                        to="/"
                        fontWeight="500"
                        sx={{
                            textDecoration: 'none',
                            color: 'primary.main',
                        }}
                    >
                        Forgot Password ?
                    </Typography>
                </Stack>
            </Stack>
            <Box>
                <Button
                    color="primary"
                    variant="contained"
                    size="large"
                    fullWidth
                    onClick={onClick}
                    // component={Link}
                    // to="/"
                    // type="submit"
                >
                    Sign In
                </Button>
            </Box>
            {subtitle}
        </>
    )
};

export default AuthLogin;
