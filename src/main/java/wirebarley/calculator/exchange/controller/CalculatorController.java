package wirebarley.calculator.exchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalculatorController {

    @GetMapping("/")
    public String calculatorTemplate(Model model){

        return "calculatorTemplate";
    }
}
