package com.example.hi_arch.arch.java.mvvm;

import androidx.databinding.ObservableField;


public class LoginViewModel {

    public ObservableField<User> userField = new ObservableField<>();

    public void Login() {
        User user = new User(
                "栾桂明", 12, "男"
        );
        userField.set(user);
    }
}
