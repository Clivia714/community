package com.hhhyu.community;

import com.hhhyu.community.mapper.UserMapper;
import com.hhhyu.community.model.User;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.PortUnreachableException;
import java.util.Random;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    UserMapper userMapper;


    @Test
    void cglibProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(this.getClass().getClassLoader());
        enhancer.setSuperclass(this.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return null;
            }
        });
        Object o = enhancer.create();
    }


    @Test
    void jdkProxy() {
        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        return null;

                    }
                });
    }
}
