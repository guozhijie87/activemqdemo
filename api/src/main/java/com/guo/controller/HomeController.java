package com.guo.controller;

import com.guo.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jms.Destination;

import javax.annotation.Resource;

/**
 * Created by jack on 2017/7/20.
 */
@Controller
public class HomeController {
    @Resource(name = "demoQueueDestination")
    private Destination demoQueueDestination;

    @Autowired
    private ProducerService producer;

    @RequestMapping(value = "/onsend", method = RequestMethod.POST)
    public String producer(@RequestParam("message") String message) {
        System.out.println("---------send to jms");

        producer.sendMessage(demoQueueDestination, message);
        return null;
    }

    @RequestMapping(value = "test")
    public String test(){
        System.out.println("---------send to jms");
        return null;
    }
}
