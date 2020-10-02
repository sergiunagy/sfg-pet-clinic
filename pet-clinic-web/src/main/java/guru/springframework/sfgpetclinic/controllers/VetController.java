package guru.springframework.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetController {

    @RequestMapping({"vets", "vets/", "vets/index", "vets/ndex.html"})
    public String index(){

        return "vets/index";
    }
}
