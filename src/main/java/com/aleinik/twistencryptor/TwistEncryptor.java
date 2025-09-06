package com.aleinik.twistencryptor;

import com.aleinik.twistencryptor.application.Application;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.view.ConsoleView;
import com.aleinik.twistencryptor.view.View;


public class TwistEncryptor {
    public static void main(String[] args) {
        View view = new ConsoleView();
        Application application = new Application(view);
        Result result = application.run();
        application.printResult(result);
    }
}
