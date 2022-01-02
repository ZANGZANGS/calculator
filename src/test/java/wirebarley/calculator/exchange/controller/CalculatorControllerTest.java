package wirebarley.calculator.exchange.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import wirebarley.calculator.exchange.service.CalculatorService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CalculatorService calculatorService;

    @Test
    @DisplayName("환율 정보 조회 테스트")
    public void templateController(){
        //given
        final String source = "USD";
        final String[] currencies = new String[]{"KRW", "JPY", "PHP"};

        List<String[]> result = new ArrayList<>();

        result.add(new String[]{ "KRW", "South Korean Won", "1188.88"});
        result.add(new String[]{ "JPY", "Japanese Yen", "115.11"});
        result.add(new String[]{ "PHP", "Philippine Peso", "51.00"});


        given(calculatorService.getCurrencyListService(source, currencies))
                .willReturn(result);


        //when
        //then
        try {
            mvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("South Korean Won")));
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}