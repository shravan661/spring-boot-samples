package hello;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SalutationController {

    @RequestMapping("/salutation")
    public String greeting(@Valid Salutation s, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("name", s.getName());
        return "salutation";
    }

}