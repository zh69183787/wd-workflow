package com.wonders.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoushun
 * Date: 2015/4/1
 * Time: 10:11
 * To change this template use File | Settings | File Templates.
 */
public class Plate {
    List<Object> eggs = new ArrayList<Object>();

    public synchronized Object getEgg(){
        while(eggs.size()==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Object egg = eggs.get(0);
        eggs.clear();
        notify();
        System.out.println("get egg");
        return egg;

    }

    public synchronized void putEgg(Object egg){
        while(eggs.size() > 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        eggs.add(egg);
        notify();
        System.out.println("put egg");
    }
    static class AddThread implements Runnable  {
        private Plate plate;
        private Object egg = new Object();
        public AddThread(Plate plate) {
            this.plate = plate;
        }
        public void run() {
            plate.putEgg(egg);
        }
    }
    static class GetThread implements Runnable  {
        private Plate plate;
        public GetThread(Plate plate) {
            this.plate = plate;
        }
        public void run() {
            plate.getEgg();
        }
    }
    public static void main(String args[]) {
        Plate plate = new Plate();
        for(int i = 0; i < 10; i++) {
            new Thread(new AddThread(plate)).start();
            new Thread(new GetThread(plate)).start();
        }
    }
}
