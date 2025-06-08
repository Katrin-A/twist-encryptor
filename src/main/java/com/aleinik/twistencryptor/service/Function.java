package com.aleinik.twistencryptor.service;

import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.view.View;

public interface Function {
    Result execute(UserParameters param, View view);
}
