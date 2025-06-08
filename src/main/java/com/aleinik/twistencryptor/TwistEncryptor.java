package com.aleinik.twistencryptor;

import com.aleinik.twistencryptor.application.Application;
import com.aleinik.twistencryptor.controller.MainController;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.view.ConsoleView;
import com.aleinik.twistencryptor.view.View;


public class TwistEncryptor {
    public static void main(String[] args) {
        View view = new ConsoleView();
        MainController mainController = new MainController(view);
        Application application = new Application(mainController);
        Result result = application.run();
        application.printResult(result);
    }
}
