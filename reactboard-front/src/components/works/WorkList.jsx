import React from "react";
import { styled } from "@mui/material";
import WorkItem from "./WorkItem";

const WorkListDiv= styled('div')(() => ({
    flex: 1,
    padding: '20px',
    // paddingBottom: '48px',
    overflowY: 'auto',
    // border: '1px solid gray' /* 사이즈 조정이 잘 되고 있는지 확인하기 위한 임시 스타일 */
}));

const WorkList = ({WorkList}) => {
    return (
        <WorkListDiv>
            {
                WorkList.map((work)=>(
                    <WorkItem key={work.IDX} idx={work.IDX} text={work.WORK} $done={work.DONE_YN==="Y"} />
                ))
            }
        </WorkListDiv>
    )
}

export default WorkList;