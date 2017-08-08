package com.guo.service;

import javax.jms.Destination;
import javax.jms.TextMessage;

/**
 * Created by jack on 2017/7/21.
 */
public interface ConsumerService {
    TextMessage receive(Destination destination);
}
