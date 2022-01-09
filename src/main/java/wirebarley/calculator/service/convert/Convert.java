package wirebarley.calculator.service.convert;

import lombok.*;

import java.math.BigDecimal;

/**
 * 환율 계산에 필요한 DTO Class
 */



public class Convert {


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Req {
        private String from;    //from
        private String to;      //to
        private BigDecimal amount;  // 금액
        private BigDecimal rate; //환율
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Res{
        private BigDecimal amount;  //금액
    }


}
