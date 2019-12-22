package com.lsy.java.test.javassist;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtField.Initializer;
import javassist.CtMethod;
import javassist.CtNewMethod;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

/**
 * Created by lisiyu on 2019/1/21.
 */
public class JavassistLearn {

    public static void main(String[] args) {
        try {
            makeClass();
            aop();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    //用javassist来创建类的代码
    public static void makeClass() throws Exception{
        ClassPool cp=ClassPool.getDefault();
        CtClass ctClass=cp.makeClass("com.lsy.java.test.javassist.JavassistClass");

        StringBuffer body=null;
        //参数  1：属性类型  2：属性名称  3：所属类CtClass
        CtField ctField=new CtField(cp.get("java.lang.String"), "name", ctClass);
        ctField.setModifiers(Modifier.PRIVATE);
        //设置name属性的get set方法
        ctClass.addMethod(CtNewMethod.setter("setName", ctField));
        ctClass.addMethod(CtNewMethod.getter("getName", ctField));
        ctClass.addField(ctField, Initializer.constant("default"));

        //参数  1：参数类型   2：所属类CtClass
        CtConstructor ctConstructor=new CtConstructor(new CtClass[]{}, ctClass);
        body=new StringBuffer();
        body.append("{\n name=\"me\";\n}");
        ctConstructor.setBody(body.toString());
        ctClass.addConstructor(ctConstructor);

        //参数：  1：返回类型  2：方法名称  3：传入参数类型  4：所属类CtClass
        CtMethod ctMethod=new CtMethod(CtClass.voidType,"execute",new CtClass[]{},ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        body=new StringBuffer();
        body.append("{\n System.out.println(name);");
        body.append("\n System.out.println(\"execute ok\");");
        body.append("\n return ;");
        body.append("\n}");
        ctMethod.setBody(body.toString());
        ctClass.addMethod(ctMethod);
        Class<?> c=ctClass.toClass();
        Object o=c.newInstance();
        Method method=o.getClass().getMethod("execute", new Class[]{});
        //调用字节码生成类的execute方法
        method.invoke(o, new Object[]{});
    }

    /**
     * javassist还有一个比较常见的用途是AOP，比如对一些类统一加权限过滤，加日志监控等等。
     下面示例如何使用javassist来进行AOP拦截处理

     我们对上面例子的JavassistClass类的getName()方法进行拦截前置处理

     * @throws Exception
     */
    public static void aop() throws Exception{
        ProxyFactory factory=new ProxyFactory();
        //设置父类，ProxyFactory将会动态生成一个类，继承该父类
        factory.setSuperclass(JavassistClass.class);
        //设置过滤器，判断哪些方法调用需要被拦截
        factory.setFilter(new MethodFilter() {
//            @Override
            public boolean isHandled(Method m) {
                if(m.getName().equals("getName")){
                    return true;
                }
                return false;
            }
        });
        //设置拦截处理
        factory.setHandler(new MethodHandler() {
//            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed,
                                 Object[] args) throws Throwable {
                //拦截后前置处理，改写name属性的内容
                //实际情况可根据需求修改
                JavassistClass o=(JavassistClass) self;
                o.setName("haha");
                return proceed.invoke(self, args);
            }
        });

        Class<?> c=factory.createClass();
        JavassistClass object=(JavassistClass) c.newInstance();
        System.out.println(object.getName());

    }
}
