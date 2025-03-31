package com.gv.user_service.util;

import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.dto.response.Error;
import com.gv.user_service.dto.response.Result;

public class ResponseUtils {

    public static <T> APIResponse createApiResponse(boolean isSuccess, String statusCode, String statusDesc,
                                                    T data, Error error) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(isSuccess);
        apiResponse.setError(error);
        Result result = new Result();
        result.setData(data);

        result.setStatusCode(statusCode);
        result.setStatusDesc(statusDesc);
        apiResponse.setResult(result);
        return apiResponse;
    }
}
