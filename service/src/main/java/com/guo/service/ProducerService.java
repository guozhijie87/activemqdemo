package com.guo.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by jack on 2017/7/20.
 */
public interface ProducerService {
    void sendMessage(Destination destination, final String msg);
    void sendMessage(final String msg);
}
