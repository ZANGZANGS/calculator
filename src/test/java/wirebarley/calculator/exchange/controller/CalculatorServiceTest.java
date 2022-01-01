package wirebarley.calculator.exchange.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;


    @Test
    public void getCurrencyListService(){
        //given
        final String source = "USD";
        final String[] currencies = new String[]{"KRW", "JPY", "PHP"};

        //when
        List<String[]> list = calculatorService.getCurrencyListService(source, currencies);

        //then
        Assertions.assertThat(list.get(0)[0]).isEqualTo(currencies[0]);
        Assertions.assertThat(list.get(1)[0]).isEqualTo(currencies[1]);
        Assertions.assertThat(list.get(2)[0]).isEqualTo(currencies[2]);


    }

}