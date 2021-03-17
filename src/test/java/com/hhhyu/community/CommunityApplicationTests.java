package com.hhhyu.community;

import com.hhhyu.community.mapper.UserMapper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
@SpringBootTest
class CommunityApplicationTests {
    public Integer lock = 19;
    @Autowired
    UserMapper userMapper;


    class ThreadA extends Thread{
        private int i = 50;
        ThreadB threadB = new ThreadB();
        @Override
        public void run() {
            threadB.setName("Thread B");
            synchronized (threadB) {
                threadB.start();
                while (i > 0) {
                    System.out.println("This is:" + Thread.currentThread().getName());
                    /*try {
                        threadB.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    i--;
                }
            }
        }
    }

    class ThreadB extends Thread{
        private int count = 50;
        @Override
        public void run() {
            synchronized (this) {
                while (count > 0) {
                    System.out.println("This is:" + Thread.currentThread().getName());
                    count--;
                }
            }
        }
    }

    @Test
    void ThreadTest(){
        ThreadA threadA = new ThreadA();
        threadA.setName("Thread A");
        threadA.start();

    }


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
