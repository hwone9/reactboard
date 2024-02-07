import React, { useState } from "react";
import { MdAdd } from "react-icons/md";
import { styled } from "@mui/material";

const WorkCreateDiv = styled("div")(() => ({
  paddingTop: "20px",
  paddingBottom: "20px",
  display: "flex",
  justifyContent: "space-between",
  alignContent: "center",
}));

const Input = styled("input")(() => ({
  padding: "12px",
  borderRadius: "4px",
  border: "1px solid #dee2e6",
  width: "100%",
  outline: "none",
  fontSize: "17px",
  boxSizing: "border-box",
}));

const CreateButton = styled("button")(() => ({
  cursor: "pointer",
  background: "#4062c6",
  width: "35px",
  height: "35px",
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  fontSize: "35px",
  color: "#fff",
  border: "none",
  borderRadius: "50%",
  margin: "4px 0 4px 10px",
}));

const WorkCreate = ({ onCreate }) => {
  const [workItem, setWorkItem] = useState("");

  const onKeyDown = (event) => {
    if (event.keyCode === 13) {
      onCreate(workItem);
      event.target.blur();
      setWorkItem("");
    }
  };

  const onClickCreate = () => {
    if (workItem) {
      onCreate(workItem);
      setWorkItem("");
    }
  };

  return (
    <WorkCreateDiv>
      <Input
        autoFocus
        placeholder="write..."
        type="text"
        name="workItem"
        value={workItem}
        onChange={(event) => setWorkItem(event.target.value)}
        onKeyDown={onKeyDown}
      />
      <CreateButton type="button" onClick={onClickCreate}>
        <MdAdd />
      </CreateButton>
    </WorkCreateDiv>
  );
};

export default WorkCreate;
