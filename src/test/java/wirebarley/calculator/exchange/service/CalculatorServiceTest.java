package wirebarley.calculator.exchange.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wirebarley.calculator.exchange.dto.CalculatorDto;

import java.math.BigDecimal;
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

        for (String[] item: list){
            System.out.println(item.toString());
        }

        //then
        Assertions.assertThat(list.get(0)[0]).isEqualTo(currencies[0]);
        Assertions.assertThat(list.get(1)[0]).isEqualTo(currencies[1]);
        Assertions.assertThat(list.get(2)[0]).isEqualTo(currencies[2]);

    }

    @Test
    public void convertService(){
        //given
        CalculatorDto.Req req = CalculatorDto.Req.builder()
                                            .from("USD")
                                            .to("KRW")
                                            .amount(new BigDecimal("300"))
                                            .rate(new BigDecimal("1104.00"))
                                            .build();

        //when
        CalculatorDto.Res res = calculatorService.convertService(req);
        System.out.println(res.getAmount().toPlainString());

        //then
        Assertions.assertThat(res.getAmount().toPlainString()).isEqualTo("331200.00");

    }

}