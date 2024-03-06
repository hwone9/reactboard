import React, { useState } from 'react';
import { Box, Typography, Button, TextField } from '@mui/material';
import { Stack } from '@mui/system';
import * as comm from "src/js/Common";
import * as util from "src/js/Util";
import { useNavigate } from 'react-router-dom';
// import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
// import VisibilityIcon from '@mui/icons-material/Visibility';

/**
 * Error Message Component
 * @param {Map} param : {message}
 * @returns Error Message Component
 */
const ErroMsgComponent = ({message})=>{
    return (
        <Typography variant="subtitle1" component="label" htmlFor={message} sx={{color: '#f3704d'}}>
            {message}
        </Typography>
    );
}

const AuthJoin = ({ title, subtitle, subtext }) => {
    const navigate = useNavigate();

    const [join, setJoin] = useState({
        name: '',
        email: '',
        password: ''
    });

    const onChange = (event)=>{
        if(comm.noSpaceForm(event.target.value)){
            setJoin({
                ...join,
                [event.target.name] : event.target.value,
            });
        } else {
            setJoin({
                ...join,
                [event.target.name] : event.target.value.replace(' ',''),// 공백제거
            });

        }
    }

    const onClick = async ()=> {
        let resp = await util.process("patch", `/auth/join`, join);
        if (resp.RESULT === "SUCCESS") {
            resp = resp.resData;
            alert("회원가입 완료");
            navigate("/");
            
        } else {
            alert(resp.ERR_MSG);
        }
    }

    return (
        <>
            {title ? (
                <Typography fontWeight="700" variant="h2" mb={1}>
                    {title}
                </Typography>
            ) : null}

            {subtext}

            <Box>
                <Stack mb={3}>
                    <Typography variant="subtitle1" fontWeight={600} component="label" htmlFor='name' mb="5px">Name</Typography>
                    <TextField id="name" variant="outlined" fullWidth error={comm.isNull(join.name)}
                                name='name' type='text' value={join.name} 
                                onChange={onChange} />
                    {comm.isNull(join.name) && 
                        <ErroMsgComponent message={'write your name'}></ErroMsgComponent>
                    }
                    {/* 중복확인 //todo */}

                    <Typography variant="subtitle1" fontWeight={600} component="label" htmlFor='email' mb="5px" mt="25px">Email Address</Typography>
                    <TextField id="email" variant="outlined" fullWidth error={comm.isNull(join.email)}
                                name='email' type='email' value={join.email} 
                                onChange={onChange} />
                    {comm.isNull(join.email) &&
                        <ErroMsgComponent message={'write your email'}></ErroMsgComponent>
                    }

                    <Typography variant="subtitle1" fontWeight={600} component="label" htmlFor='password' mb="5px" mt="25px">Password</Typography>
                    <TextField id="password" variant="outlined" fullWidth error={comm.isNull(join.password)}
                                name='password' type='password' value={join.password} 
                                onChange={onChange} >
                    </TextField>
                    {comm.isNull(join.password) &&
                        <ErroMsgComponent message={'write your password'}></ErroMsgComponent>
                    }
                </Stack>
                <Button color="primary" variant="contained" size="large" fullWidth onClick={onClick} 
                        disabled={comm.isNull(join.name) || comm.isNull(join.email) || comm.isNull(join.password)}>
                    Sign Up
                </Button>
            </Box>
            {subtitle}
        </>
    )
};

export default AuthJoin;
