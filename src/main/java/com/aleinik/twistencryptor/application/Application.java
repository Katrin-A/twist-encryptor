package com.aleinik.twistencryptor.application;

import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.service.Function;
import com.aleinik.twistencryptor.view.View;


public class Application {
    private final View view;

    public Application(View view) {

        this.view = view;
    }

    public Result run() {

        UserParameters param = view.getParameters();
        Function function  = param.getMode().getFunction();
        return function.execute(param, view);

    }

    public void printResult(Result result) {

        view.printResult(result);
    }
}
