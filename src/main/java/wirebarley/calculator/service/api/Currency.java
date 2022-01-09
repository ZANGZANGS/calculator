package wirebarley.calculator.service.api;


import lombok.*;

import java.math.BigDecimal;

/**
 * 환율 계산에 필요한 DTO Class
 */
public class Currency {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Req{
        private String source;
        private String[] currencies;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Res{
        private String currency;
        private String content;
        private BigDecimal rate;  //환율
    }
}
