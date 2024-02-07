import React from "react";
import { styled } from "@mui/material";
import WorkItem from "./WorkItem";

const WorkListDiv = styled("div")(() => ({
  flex: 1,
  padding: "20px",
  overflowY: "auto",
}));

const WorkList = ({ workList, onUpdate, onRemove }) => {
  return (
    <WorkListDiv>
      {workList.map((work) => (
        <WorkItem
          key={work.IDX}
          idx={work.IDX}
          text={work.WORK}
          $done={work.DONE_YN === "Y"}
          onUpdate={onUpdate}
          onRemove={onRemove}
        />
      ))}
    </WorkListDiv>
  );
};

export default WorkList;
