package com.lsy.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 有多个线程，代表行使的汽车，写一个红绿灯函数，
 * 等红灯亮时把所有汽车线程都暂停，当绿灯亮时把所有线程都启动起来
 * Created by lisiyu on 2019/8/16.
 */
public class CarAndRrafficLight {

    public static void main(String[] args) {
        RrafficLight rrafficLight1 = new RrafficLight();
        RrafficLight rrafficLight2 = new RrafficLight();
        RrafficLight rrafficLight3 = new RrafficLight();
        Car car1 = new Car("小汽车1",rrafficLight1);
        Car car2 = new Car("小汽车2",rrafficLight2);
        Car car3 = new Car("小汽车3",rrafficLight3);
        car1.start();
        car2.start();
        car3.start();
        List<RrafficLight> rrafficLightList = new ArrayList<RrafficLight>();
        rrafficLightList.add(rrafficLight1);
        rrafficLightList.add(rrafficLight2);
        rrafficLightList.add(rrafficLight3);
        //创建定时器对象
        Timer t = new Timer();
        //在2秒后执行MyTask类中的run方法
        t.schedule(new ModifyColorOfLightTask(rrafficLightList), 5000, 5000);

    }



    static class Car extends Thread {
        RrafficLight rrafficLight;

        public Car(String name, RrafficLight rrafficLight) {
            super(name);
            this.rrafficLight = rrafficLight;
        }

        public void run() {
            while (true) {
                synchronized (rrafficLight) {
                    try {
                        if (rrafficLight.color.hashCode() == Color.READ.hashCode()) {
                            System.out.println("红灯：" + Thread.currentThread().getName() + "停止行驶");
                            rrafficLight.wait();
                        } else if (rrafficLight.color.hashCode() == Color.GREEN.hashCode()) {
                            System.out.println("绿灯：" + Thread.currentThread().getName() + "继续行驶");
                            sleep(500);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 红绿灯
     */
    static class RrafficLight {

        Color color = Color.GREEN;

        public void changingBlinker() {
            if (color.hashCode() == Color.READ.hashCode()) {
                color = Color.GREEN;
            } else {
                color = Color.READ;
            }
        }
    }

    /**
     * 颜色枚举
     */
    enum Color {
        READ, GREEN;

        @Override
        public String toString() {
            switch (this) {
                case READ:
                    return "红灯";

                case GREEN:
                    return "绿灯";
            }
            return "异常灯";
        }
    }

    /**
     * 修改灯的颜色
     */
    static class ModifyColorOfLightTask extends TimerTask {
        List<RrafficLight> rrafficLights;

        public ModifyColorOfLightTask(List<RrafficLight> rrafficLights) {
            this.rrafficLights = rrafficLights;
        }

        @Override
        public void run() {
            for (RrafficLight rrafficLight : rrafficLights) {
                rrafficLight.changingBlinker();
                synchronized (rrafficLight) {
                    rrafficLight.notifyAll();
                }
            }
        }
    }
}
