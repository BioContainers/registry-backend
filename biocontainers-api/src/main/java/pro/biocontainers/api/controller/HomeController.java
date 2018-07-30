package pro.biocontainers.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 26/07/2018.
 */
@Controller
public class HomeController {
    @RequestMapping("${biocontainers.api.base-path}")
    public String home() {
        return "redirect:/swagger-ui.html";
    }

    @RequestMapping("${biocontainers.api.base-path}/api-ui")
    public String apiUi() {
        return "redirect:/swagger-ui.html";
    }
}
