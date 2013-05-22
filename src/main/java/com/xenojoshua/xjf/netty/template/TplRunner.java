package com.xenojoshua.xjf.netty.template;

import com.xenojoshua.xjf.system.XjfSystem;
import com.xenojoshua.xjf.template.XjfTemplate;

public class TplRunner {

    public static void run() {
        XjfSystem.init("/Users/jonathan/prog/Java/projects/XJF-Netty/target");

        XjfTemplate.reset();
        XjfTemplate.assign("name", "jonathan");
        String result = XjfTemplate.fetch("index/test.vm");
        System.out.println(result);
    }

}
