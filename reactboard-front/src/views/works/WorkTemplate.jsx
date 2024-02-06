import { styled } from "@mui/material";
import { useEffect, useState } from "react";
import WorkCreate from "src/components/works/WorkCreate";
import WorkHead from "src/components/works/WorkHead";
import WorkList from "src/components/works/WorkList";
import * as util from "src/js/Util";

const WorkTemplateDiv= styled('div')(() => ({
    position: 'relative', /* 추후 박스 하단에 추가 버튼을 위치시키기 위한 설정 */
    background: 'white',
    margin: '0 auto',
    marginTop: '20px',
    // marginBottom: '32px',
    display: 'flex',
    flexDirection: 'column'
}));

const WorkTemplate = ({children}) => {
    const [workList , setWorkList] = useState([]);

    const getWorkList = async () => {//리스트 조회
        let resp = await util.process("get",`/work`, null);
        if (resp.success) {
            resp = resp.resData;
            setWorkList(resp.data);
        }
    };

    useEffect(()=>{
        getWorkList();
        // uesRefTest.current.count = 100;
    },[]);

    return (
        <WorkTemplateDiv>
            <WorkHead count={workList.length} />
            <WorkCreate />
            <WorkList WorkList={workList}/>
        </WorkTemplateDiv>
    )
}

export default WorkTemplate;
