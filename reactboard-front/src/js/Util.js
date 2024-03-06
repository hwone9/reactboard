import axios from 'axios'

/**
 * axios request function
 * @param {통신방식} method 
 * @param {요청url} url 
 * @param {전달파라미터} param 
 * @returns 
 */
export const process = async (method, url, param) => {
    let resData = {};
    try {
        const resp = (await axios({
            url: url,
            method: method,
            data: param
        })).data;
        // console.log(`request url : ${url}`);
        // console.log(`request : ${param}`);
        // console.log(`response : `);
        // console.log(resp);
        // resData.success = true;
        // resData.resData = resp;
        resData = resp;
    } catch (error) {
        // console.log(`request : ${param}`);
        // console.log(`response error:`);
        // console.log(error.response);
        // resData.success = false;
        // resData.resData = error.response;
        // alert("error!!");
        resData = error;
    }
    
    return resData;
}
