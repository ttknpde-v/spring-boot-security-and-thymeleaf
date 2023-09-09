package com.ttknpdev.understandsecurity.log;

import com.ttknpdev.understandsecurity.controller.ControllerPath;
import com.ttknpdev.understandsecurity.secure.ConfigSecureInMemory;
import org.apache.log4j.Logger;

public class MyLogging {
    public static final Logger controllerPath = Logger.getLogger(ControllerPath.class);
    public static final Logger configSecureInMemory = Logger.getLogger(ConfigSecureInMemory.class);

}
