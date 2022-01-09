package wirebarley.calculator.service.convert;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConvertServiceImplTest {

    @Autowired private ConvertService convertService;


    @Test
    @DisplayName("환율 계산 테스트")
    void convert(){

        Convert.Req req = Convert.Req.builder()
                        .from("USD")
                        .to("KRW")
                        .amount(new BigDecimal(153))
                        .rate(new BigDecimal(1101.28))
                        .build();

        Convert.Res res = convertService.convert(req);

        System.out.println(res.getAmount().toString());
        System.out.println(res.getAmount().toPlainString());
        System.out.println(res.getAmount().toBigInteger());
        System.out.println(res.getAmount().toEngineeringString());
        System.out.println(res.getAmount().doubleValue());

        Assertions.assertThat(res.getAmount().doubleValue()).isEqualTo(168495.84);


    }
}