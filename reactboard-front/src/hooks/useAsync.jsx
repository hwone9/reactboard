import { useEffect, useReducer } from "react";

function reducer(state, action) {
    switch (action.type) {
      case 'LOADING':
        return {
          loading:true,
          data:null,
          error:null
        }
      case 'SUCCESS':
        return {
          loading:false,
          data:action.data,
          error:null
        }
      case 'ERROR':
        return {
          loading:false,
          data:null,
          error:action.error
        }
      
      default:
        throw new Error(`Unhandled action type: ${action.type}`);
    }
}

/**
 * 
 * @param {Function} callback api요청 함수
 * @param {Array} [deps = []] useEffect의 deps로 설정 (default: 처음 렌더링시에만 호출)
 * @param {Boolean} skip true인 경우 useEffect에서 작업x
 * @returns 
 */
const useAsync = (callback, deps = [], skip=false) => {
    const [state, dispatch] = useReducer(reducer, {
        loading: false,
        data: null,
        error: false
    });

    const fetchData = async()=>{
      dispatch({type: "LOADING"});
      try {
        const data = await callback();
        dispatch({type: "SUCCESS", data:data});
      } catch (e) {
        dispatch({type: "ERROR", error:e});
      }
    }

    useEffect(()=>{
      if (skip) {
        return;
      }
      fetchData();
      // eslint 설정을 다음 줄에서만 비활성화
      // eslint-disable-next-line
    }, deps);

    return [state, fetchData];
}

export default useAsync;