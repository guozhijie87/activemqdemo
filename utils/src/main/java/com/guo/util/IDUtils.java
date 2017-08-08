package com.guo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lmm on 16-8-10.
 */
public class IDUtils {
    public IDUtils() {
        this.initializing();
    }

    protected void initializing() {
        this.servers = new ArrayList<>();

        this.servers.add("server0");

        this.atomicNum = new AtomicInteger();
    }

    private static IDUtils instance;

    private static Lock lockInstance = new ReentrantLock();

    public static IDUtils getInstance() {
        if (instance == null) {
            lockInstance.lock();

            try {
                if (instance == null) {
                    instance = new IDUtils();
                }
            } finally {
                lockInstance.unlock();
            }
        }

        return instance;
    }

    private List<String> servers;

    protected AtomicInteger atomicNum;

    protected long getMachineNum() {
        return 6;
    }

    protected long startTime;

    Lock lockReset = new ReentrantLock();

    public long getId() {
        long now = (new Date()).getTime();
        int num = atomicNum.getAndIncrement();

        if (now - startTime > 1000) {
            lockReset.lock();
            try {
                startTime = now;
                num = 0;
                atomicNum.set(0);
            } finally {
                lockReset.unlock();
            }
        }

        long id = now * 100 + this.getMachineNum();

        id = id * 10000 + num;

        return id;
    }
}

