package wirebarley.calculator.exchange.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 환율 계산에 필요한 DTO Class
 */
public class CalculatorDto {

    @Getter@Setter
    @Builder
    @AllArgsConstructor
    public static class Req {
        private String from;    //from
        private String to;      //to
        private BigDecimal amount;  // 금액
        private BigDecimal rate; //환율
    }

    @Getter@Setter
    @Builder
    @AllArgsConstructor
    public static class Res{
        private BigDecimal amount;  //금액
        boolean success;
    }
}
