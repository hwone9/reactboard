import { styled } from "@mui/material";
import { useEffect, useState } from "react";

const WorkHeadDiv = styled("div")(() => ({
  paddingBottom: "24px",
  borderBottom: "1px solid #e9ecef",
  h1: {
    margin: 0,
    fontSize: "25px",
    color: "#343a40",
  },
  ".day": {
    marginTop: "4px",
    color: "#868e96",
    fontSize: "21px",
  },
  ".tasks-left": {
    color: "#5270c8",
    fontSize: "15px",
    marginTop: "40px",
    fontWeight: "bold",
  },
}));

const WorkHead = ({ count }) => {
  const [today, setToday] = useState("");

  useEffect(() => {
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth() + 1;
    const date = today.getDate();
    setToday(year + " / " + month + " / " + date);
  }, []);

  return (
    <WorkHeadDiv>
      <h1>{today}</h1>
      <div className="tasks-left">{count} 건</div>
    </WorkHeadDiv>
  );
};

export default WorkHead;
