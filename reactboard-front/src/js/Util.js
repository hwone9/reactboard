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
        resData = resp;
    } catch (error) {
        resData.RESULT = "ERROR";
        resData.ERR_MSG = error.response;
    }
    
    return resData;
}
