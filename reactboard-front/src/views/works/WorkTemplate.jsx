import { styled } from "@mui/material";
import { useEffect, useState } from "react";
import WorkCreate from "src/views/works/components/WorkCreate";
import WorkHead from "src/views/works/components/WorkHead";
import WorkList from "src/views/works/components/WorkList";
import * as util from "src/js/Util";

const WorkTemplateDiv = styled("div")(() => ({
  position: "relative",
  background: "white",
  margin: "0 auto",
  marginTop: "20px",
  display: "flex",
  flexDirection: "column",
}));

const WorkTemplate = ({ children }) => {
  const [workList, setWorkList] = useState([]);

  const getWorkList = async () => {
    //리스트 조회
    console.log("getWorkList");

    let resp = await util.process("get", `/work`, null);
    if (resp.RESULT==="SUCCESS") {
      setWorkList(resp.RES.data);
    }
  };

  const onCreate = async (workItem) => {
    //리스트 추가 함수
    console.log("onCreate");

    if (workItem) {
      let param = { work: workItem };
      let resp = await util.process("post", `/work`, param);
      if (resp.RESULT==="SUCCESS") {
        getWorkList();
      } else {
        alert("create fail!!!!");
      }
    }
  };

  const onUpdate = async (updateId, updateText, updateDone) => {
    //내용, 완료여부 수정
    console.log("onUpdate");

    let param = {
      idx: updateId,
      work: updateText,
      done: updateDone ? "Y" : "N",
    };
    let resp = await util.process("patch", `/work`, param);
    if (resp.RESULT==="SUCCESS") {
      getWorkList();
    } else {
      alert("update fail!!!");
    }
  };

  const onRemove = async (idx) => {
    //삭제버튼
    console.log("onRemove");

    let resp = await util.process("delete", `/work/${idx}`);
    if (resp.RESULT==="SUCCESS") {
      getWorkList();
    } else {
      alert("delete fail!!!!");
    }
  };

  useEffect(() => {
    getWorkList();
  }, []);

  return (
    <WorkTemplateDiv>
      <WorkHead count={workList.filter((work)=> work.DONE_YN==='N').length} />
      <WorkCreate onCreate={onCreate} />
      <WorkList workList={workList} onUpdate={onUpdate} onRemove={onRemove} />
    </WorkTemplateDiv>
  );
};

export default WorkTemplate;
