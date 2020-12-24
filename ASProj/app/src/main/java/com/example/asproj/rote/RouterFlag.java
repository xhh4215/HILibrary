package com.example.asproj.rote;

public interface RouterFlag {
    int FLAG_LOGIN = 0x01;
    int FLAG_AUTHENTICATION = FLAG_LOGIN<<1;
    int FLAG_VIP = FLAG_AUTHENTICATION<<1;
}
