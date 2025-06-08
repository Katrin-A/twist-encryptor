package com.aleinik.twistencryptor.application;

import com.aleinik.twistencryptor.controller.MainController;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.repository.FunctionCode;
import com.aleinik.twistencryptor.service.Function;

import static com.aleinik.twistencryptor.constants.FunctionCodeConstants.*;


public class Application {
    private final MainController mainController;
    public Application(MainController mainController) {
        this.mainController = mainController;
    }

    public Result run() {

        UserParameters param = mainController.getView().getParameters();
        Function function  = getFunction(param.getMode());
        return function.execute(param, mainController.getView());

    }

    private Function getFunction(int mode) {
        return switch (mode){
            case 1 -> FunctionCode.valueOf(ENCODE).getFunction();
            case 2 -> FunctionCode.valueOf(DECODE).getFunction();
            case 3 -> FunctionCode.valueOf(BRUTE_FORCE).getFunction();
            default -> FunctionCode.valueOf(UNSUPPORTED_FUNCTION).getFunction();
        };
    }

    public void printResult(Result result) {
        mainController.getView().printResult(result);
    }
}
