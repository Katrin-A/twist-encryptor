package com.aleinik.twistencryptor.service;

import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.exception.ApplicationException;
import com.aleinik.twistencryptor.repository.ResultCode;
import com.aleinik.twistencryptor.view.View;

public class UnsupportedFunction  implements Function{

    @Override
    public Result execute(UserParameters param, View view) {
        String message = "Unsupported function.";
        return new Result(ResultCode.ERROR, new ApplicationException("message"));
    }
}
