package wirebarley.calculator.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class CalculatorController {

    @Value("${api.source}") private String source;
    @Value("${api.currencies}") private String[] currencies;

    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/")
    public String templatController(Model model){

        List<String[]> currencyList = calculatorService.getCurrencyListService(source, currencies);
        model.addAttribute("currencyList", currencyList);
        model.addAttribute("source",source);

        return "thymeleaf/calculatorTemplate";
    }


    @PostMapping("/convert")
    @ResponseBody
    public Object convertController(@ModelAttribute HashMap<String, Object> hashMap, Model model){

        return null;
    }
}
