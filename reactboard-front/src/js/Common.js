
// eslint-disable-next-line no-unused-vars
export const isNull = (params) => {
    if(params===null || params===undefined || params===''){
        return true;
    }
    return false;
}

/**
 * 스페이스바 사용불가
 */
export const noSpaceForm = function (value) { // 공백사용못하게
    var str_space = /\s/;  // 공백체크
    if(str_space.exec(value)) { //공백 체크
        alert("해당 항목에는 공백을 사용할수 없습니다.\n공백은 자동적으로 제거 됩니다.");
        return false;
    }
}
