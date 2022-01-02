package wirebarley.calculator.exchange.dto;


import lombok.*;

import java.math.BigDecimal;


public class CalculatorDto {

//    @Getter
//    @AllArgsConstructor
//    @Builder


    @Getter@Setter
    @Builder
    @AllArgsConstructor
    public static class Req {
        private String from;
        private String to;
        private BigDecimal amount;
        private BigDecimal rate; //환율
    }

    @Getter@Setter
    @Builder
    @AllArgsConstructor
    public static class Res{
        private BigDecimal amount;
        boolean success;
    }
}
