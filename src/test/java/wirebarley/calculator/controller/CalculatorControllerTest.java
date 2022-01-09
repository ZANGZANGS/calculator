package wirebarley.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wirebarley.calculator.service.api.Currency;
import wirebarley.calculator.service.api.CurrencyApiService;
import wirebarley.calculator.service.convert.Convert;
import wirebarley.calculator.service.convert.ConvertService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CurrencyApiService currencyApiService;
    @MockBean
    ConvertService convertService;



    @Test
    @DisplayName("환율 정보 조회 컨트롤러 테스트")
    public void templateController() throws Exception {
        //given
        final String source = "USD";
        final String[] currencies = new String[]{"KRW", "JPY", "PHP"};

        Currency.Req req = Currency.Req.builder()
                .source(source)
                .currencies(currencies)
                .build();


        List<Currency.Res> result = new ArrayList<>();

        result.add(Currency.Res.builder().currency("KRW").content("South Korean Won").rate(new BigDecimal("1188.88")).build());
        result.add(Currency.Res.builder().currency("JPY").content("Japanese Yen").rate(new BigDecimal("115.11")).build());
        result.add(Currency.Res.builder().currency("PHP").content("Philippine Peso").rate(new BigDecimal("51.00")).build());

        //when
        given(currencyApiService.getCurrencyList(req))
                .willReturn(result);

        //then

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("South Korean Won")));



    }

    @Test
    @DisplayName("환율 계산 컨트롤러 테스트")
    void convertTest() throws Exception {

        //given
        Convert.Req req = Convert.Req.builder()
                        .from("USD")
                        .to("KRW")
                        .amount(new BigDecimal(100))
                        .rate(new BigDecimal(1140.35))
                        .build();

        String content = objectMapper.writeValueAsString(req);

        Convert.Res res = Convert.Res.builder()
                        .amount(new BigDecimal(114035))
                        .build();

        //when
        given(convertService.convert(req))
                .willReturn(res);



        //then
        mvc.perform(
                post("/convert")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("114035")));

    }
}
