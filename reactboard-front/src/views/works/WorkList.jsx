import { Container } from "@mui/material";
import React, { useEffect, useState } from "react";
import * as util from "src/js/Util";

const WorkList = () => {
    const [today, setToday] = useState("");
    const [workList , setWorkList] = useState([]);
    const [workItem , setWorkItem] = useState('');
    const [saveItem , setSaveItem] = useState({});
    
    const getWorkList = async () => {//리스트 조회
        let resp = await util.process("get",`/work`, null);
        if (resp.success) {
            resp = resp.resData;
            setWorkList(resp.data);
        }
    };

    const onKeyDown = (event)=>{//엔터키 눌렀을 때 저장
        if(event.keyCode === 13){
            insertWorkItem();
            event.target.blur();
        }        
    }

    const insertWorkItem = async ()=>{//리스트 추가 함수
        if(workItem){
            let param = {"work" : workItem};
            let resp = await util.process("post",`/work`, param);
            if (resp.success) {
                resp = resp.resData;
                setWorkItem('');
                getWorkList();
            }   
        }
    }

    const onClickDoneBtn = async (idx)=>{//완료여부
        let param = {
            "idx" : idx
          }
        let resp = await util.process("patch",`/workdone`, param);
        if (resp.success) {
            resp = resp.resData;
            getWorkList();
        }
    }

    const onChange = (event)=>{
        const {name, value} = event.target;
        console.log(name, value);
    }

    const onKeyDownTitle = (event,param)=>{//데이터 수정 후 엔터키 눌렀을때
        if(event.keyCode === 13){
            event.target.blur();
        }
    }

    const updateWork = async (param)=>{//입력된 할일 focus out 발생 시 데이터 업데이트
        console.log(param);
        return;

        // if(param.work){
        //     let param = {
        //         "idx" : 2,
        //         "work": event.target.value
        //     };
        //     let resp = await util.process("patch",`/work`, param);
        //     if (resp.success) {
        //         resp = resp.resData;
        //         getWorkList();
        //     }   
        // }

    }
    
    

    const onClickDeleteBtn = async (idx)=>{//등록된 할 일 삭제
        let resp = await util.process("delete",`/work/${idx}`);
        if (resp.success) {
            resp = resp.resData;
            setWorkItem('');
            getWorkList();
        }
    }

    // const updateWork = (param)=>{
    //     console.log(param);
    // }

    useEffect(()=>{
        getWorkList();
        
        const today = new Date();
        const year = today.getFullYear();
        const month = today.getMonth()+1;
        const date = today.getDate();
        setToday(year + " / " + month + " / " + date);

    },[]);

    return (
        <Container maxWidth="lg">
            <div>{today}</div>
            <div>{workList.length}건</div>
            <div>
                <input type="text" name="workItem" value={workItem} 
                        onChange={event => setWorkItem(event.target.value)} /* onchage가 있어야 입력가능*/
                        onKeyDown={onKeyDown} />
                <button type="button" onClick={insertWorkItem}>enter</button>
            </div>
            
            <ul>
                {workList.map((work)=>(
                    <li key={work.IDX}>
                        <button type="button" onClick={()=>{onClickDoneBtn(work.IDX)}}>done</button>
                        <input type="text" name="title" defaultValue={work.WORK} 
                                onChange={onChange}
                                onBlur={()=>{updateWork(work)}}
                                onKeyDown={(event)=>{onKeyDownTitle(event,work)}}
                                style={{textDecoration: work.DONE_YN==="Y"? 'line-through':''}}/>
                        <button type="button" onClick={()=>{onClickDeleteBtn(work.IDX)}}>delete</button>
                    </li>
                ))}    
            </ul>
        </Container>
    )
}

export default WorkList;