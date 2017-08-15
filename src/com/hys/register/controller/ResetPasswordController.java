package com.hys.register.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hys.common.api.base.ApiBaseResponse;
import com.hys.common.api.base.BaseApi;
import com.hys.register.model.ResetPasswordRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetPasswordController implements BaseApi {

    /**
     * haha.
     */
    @RequestMapping(value = "/api/resetpassword", method = RequestMethod.POST)
    public ApiBaseResponse resetpasswd(@RequestBody ResetPasswordRequest request) {
        ApiBaseResponse apiNormalResponse = new ApiBaseResponse();
        Preconditions.checkNotNull(request.getNewPassword(), "password is null");
        if (Strings.isNullOrEmpty(request.getUserCode())) {
            apiNormalResponse.setOperateSuccess(false);
            apiNormalResponse.setErrorMessage("userCode is null");
            return apiNormalResponse;
        }

        if (Strings.isNullOrEmpty(request.getNewPassword()) || request.getNewPassword().length() < 6) {
            apiNormalResponse.setOperateSuccess(false);
            apiNormalResponse.setErrorMessage("password is short");
            return apiNormalResponse;
        }

        apiNormalResponse.setOperateSuccess(true);
        return apiNormalResponse;
    }
}
