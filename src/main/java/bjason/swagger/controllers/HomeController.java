package bjason.swagger.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
	
    @RequestMapping("/")
    public String home() {
        return "redirect:swagger-ui.html";
    }
   
	
}
