package cn.yjh.spring_1;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {

    /**
     * cglib动态代理Demo
     */
    public static class Service1 {
        public void hello() {
            System.out.println("service.");
        }
        private void hello1() {
            System.out.println("service.");
        }
        public final void hello2() {
            System.out.println("service.");
        }
    }

    public static class MyMethodInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("======插入前置通知======");
            Object object = methodProxy.invokeSuper(sub, objects);
            System.out.println("======插入后者通知======");
            return object;
        }
    }

    /**
     * JDK动态代理demo
     */
    public interface Service2{
        public void showName();
        public void saying();
        void hello();
    }
    public static class Service2Impl implements Service2{
        @Override
        public void showName() {

        }

        @Override
        public void saying() {

        }

        @Override
        public void hello() {
            System.out.println("hello.");
        }
    }

    public static class Handler implements InvocationHandler{
        //private Service2 target = new Service2Impl();
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            /*System.out.println("======插入前置通知======");
            Object object = method.invoke(target, args);
            System.out.println("======插入后者通知======");
            return object;*/
            method.invoke(this, args);
            // object的公用方法直接调用当前invoke对象的。
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
                // 针对接口的不同方法书写我们具体的实现
            } else if ("showName".equals(method.getName())) {
                System.out.println("张三");
            } else if ("saying".equals(method.getName())) {
                System.out.println("我叫张三");
            }
            return null;
        }
    }


    public static void main(String[] args) {
        // 通过CGLIB动态代理获取代理对象的过程
       /* Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(Service1.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new MyMethodInterceptor());
        // 创建代理对象
        Service1 proxy= (Service1)enhancer.create();
        // 通过代理对象调用目标方法
        proxy.hello2();

        Service2 service2 = (Service2) Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(), Service2Impl.class.getInterfaces(), new Handler());
        //service2.hello();*/

        Service2 person = (Service2) Proxy.newProxyInstance(Service2.class.getClassLoader(),new Class[]{Service2.class}, new Handler());
        Proxy.getInvocationHandler(new Object());
        person.showName();
        person.saying();
        person.hello();
        System.out.println(person);
        System.out.println(person.getClass());
    }
}
