package wirebarley.calculator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import wirebarley.calculator.service.api.Currency;
import wirebarley.calculator.service.api.CurrencyApiService;
import wirebarley.calculator.service.convert.Convert;
import wirebarley.calculator.service.convert.ConvertService;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class CalculatorController {

    @Value("${api.source}") private String source;
    @Value("${api.currencies}") private String[] currencies;


    private final CurrencyApiService currencyApiService;
    private final ConvertService convertService;


    @GetMapping("/")
    public String templateController(Model model){

//        HashMap<String, Object> body = currencyApiService.list();
//        System.out.println("currencyApiService.list() = " + body.toString());
//        HashMap<String, Object> body1 = currencyApiService.live(source, currencies).getBody();
//        System.out.println("currencyApiService.live() = " + body1.toString());
//        List<String[]> currencyList = calculatorService.getCurrencyListService(source, currencies);
//        model.addAttribute("currencyList", currencyList);
//        model.addAttribute("source",source);

        return "thymeleaf/calculatorTemplate";
    }


    @PostMapping("/convert")
    @ResponseBody
    public Object convertController(@RequestBody Convert.Req req, Model model){
        Convert.Res res = convertService.convert(req);

        return ResponseEntity.ok(res);
    }
}
