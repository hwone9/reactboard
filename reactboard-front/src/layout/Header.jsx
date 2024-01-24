import styles from  "../styles/Header.module.css";

import React from "react";
import {NavLink} from "react-router-dom";

const Header = ()=>{
    return(
        <div>
            <header className={styles.header}>
                <NavLink to="/">홈</NavLink>
                &nbsp;&nbsp;|&nbsp;&nbsp;
                <NavLink to="/board">게시판</NavLink>
                <hr/>
            </header>
        </div>

    )
}

export default Header;