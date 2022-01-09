package wirebarley.calculator.service.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CurrencyApiServiceImplTest {

    @Autowired CurrencyApiService currencyApiService;

    @Test
    @DisplayName("KRW, JPY, PHP 환율 정보 조회 테스트")
    void getCurrencyList(){

        Currency.Req req = Currency.Req.builder()
                        .source("USD")
                        .currencies(new String[]{"KRW", "JPY", "PHP"})
                        .build();

        List<Currency.Res> currencyList = currencyApiService.getCurrencyList(req);

        for (Currency.Res res : currencyList) {
            System.out.println(res);
        }

        assertThat(currencyList.size()).isEqualTo(3);
        assertThat(currencyList.get(0)).isInstanceOf(Currency.Res.class);



    }
}