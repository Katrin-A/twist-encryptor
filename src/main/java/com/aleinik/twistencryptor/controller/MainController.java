package com.aleinik.twistencryptor.controller;

import com.aleinik.twistencryptor.view.View;

public class MainController {

    private View view;

    public MainController(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
