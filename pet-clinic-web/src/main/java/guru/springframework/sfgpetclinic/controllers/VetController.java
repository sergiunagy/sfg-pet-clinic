package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

//@RequestMapping("/vets")
@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"/vets.html", "/vets",  "/vets/index", "/vets/index.html"})
    public String index(Model model){

        model.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }

    @GetMapping({"/find"})
    public String vetsFind(){

        return "notimplemented";
    }

    @GetMapping("/api/vets")
    public @ResponseBody Set<Vet> getVetsJson(){

        return vetService.findAll();
    }
}
