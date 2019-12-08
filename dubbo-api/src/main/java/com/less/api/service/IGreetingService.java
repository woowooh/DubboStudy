package com.less.api.service;

import com.less.api.model.Greeting;

public interface IGreetingService {
    void sayHello(Greeting greeting);
    String say();
}
