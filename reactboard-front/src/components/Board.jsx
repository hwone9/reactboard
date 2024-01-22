import React, {useEffect, useState} from "react";

const Board = ({idx, title, contents, createdBy}) => {
    console.log(idx);
    return(
        <div>
            <h2>{title}</h2>
            <h5>{createdBy}</h5>
            <hr/>
            <p>{contents}</p>
        </div>
    )
}

export default Board;