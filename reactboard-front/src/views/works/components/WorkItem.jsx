import React from "react";
import { MdDone, MdDelete } from "react-icons/md";
import styled, { css } from "styled-components";

const Remove = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  color: #dee2e6;
  font-size: 24px;
  cursor: pointer;
  &:hover {
    color: #ff6b6b;
  }
  display: none;
`;

const WorkItemBlock = styled.div`
  display: flex;
  align-items: center;
  //   padding-top: 5px;
  //   padding-bottom: 12px;
  &:hover {
    /*styled-component에서 제공하는 component selector 기능 : hover인 경우 remove component show*/
    ${Remove} {
      display: initial;
    }
  }
`;

const CheckCircle = styled.div`
  width: 27px;
  height: 27px;
  border-radius: 16px;
  border: 1px solid #ced4da;
  font-size: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  cursor: pointer;
  ${(props) =>
    props.$done &&
    css`
      border: 1px solid #5270c8;
      color: #5270c8;
    `}
`;

const Input = styled.input`
  padding: 12px;
  border-radius: 4px;
  width: 100%;
  outline: none;
  font-size: 17px;
  box-sizing: border-box;
  border: none;
  &:hover,
  &:focus {
    border: 1px solid #dee2e6;
  }
  ${(props) =>
    props.$done &&
    css`
      color: #ced4da;
    `}
`;

const WorkItem = ({ idx, $done, text, onUpdate, onRemove }) => {
  const onKeyDownTitle = (event) => {
    //input enterKey press -> blur -> update
    if (event.keyCode === 13) {
      event.target.blur();
    }
  };

  return (
    <WorkItemBlock>
      <CheckCircle
        $done={$done}
        onClick={() => {
          onUpdate(idx, text, !$done);
        }}
      >
        {$done && <MdDone />}
      </CheckCircle>
      <Input
        type="text"
        name="title"
        $done={$done}
        defaultValue={text}
        onBlur={(event) => {
          onUpdate(idx, event.target.value, $done);
        }}
        onKeyDown={onKeyDownTitle}
      />
      <Remove
        onClick={() => {
          onRemove(idx);
        }}
      >
        <MdDelete />
      </Remove>
    </WorkItemBlock>
  );
};

export default React.memo(WorkItem);
