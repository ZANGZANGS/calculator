package wirebarley.calculator.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wirebarley.calculator.api.CurrencyApi;
import wirebarley.calculator.exchange.dto.CalculatorDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class CalculatorService {

    private final CurrencyApi currencyApi;

    @Autowired
    public CalculatorService(CurrencyApi currencyApi) {
        this.currencyApi = currencyApi;
    }

    /**
     * 화면 로딩시 필요한 환율 정보를 조회하는 서비스
     * @param source ()
     * @param currencies
     * @return List (화폐, 설명, 환율)
     */
    public List<String[]> getCurrencyListService(String source, String[] currencies){

        HashMap<String, Object> listEntityBody = currencyApi.list().getBody();
        HashMap<String, Object> currenciesInfo = listEntityBody.get("currencies") != null ?
                (HashMap<String, Object>)listEntityBody.get("currencies") : new HashMap<>();

        HashMap<String, Object> liveEntityBody = currencyApi.live(source, currencies).getBody();
        HashMap<String, Object> quotesInfo = liveEntityBody.get("quotes") != null ?
                (HashMap<String, Object>) Objects.requireNonNull(liveEntityBody).get("quotes") : new HashMap<>();

        List<String[]> list = new ArrayList<>();

        for (String code : currencies){
            //KRW, 설명, 환율
            String rate = quotesInfo.get(source+code) != null ?
                    String.valueOf(quotesInfo.get(source+code)) : (String)quotesInfo.get(source+code);

            list.add(new String[]{code,(String)currenciesInfo.get(code), rate});
        }

        return list;

    }

    /**
     * 환율 계산 서비스
     * api 사용 불가로 주석 처리 후 계산
     * @param req (from(String), to(String), amount(BigDecimal), date(String))
     * @return res(amount(BigDecimal))
     */
    public CalculatorDto.Res convertService(CalculatorDto.Req req){

//        convert api 유료,,
//        ResponseEntity<HashMap<String,Object>> responseEntity = currencyApi.convert(req.getFrom(),req.getTo(),req.getAmount());

        CalculatorDto.Res res = CalculatorDto.Res.builder()
                .amount(req.getAmount().multiply(req.getRate()))
                .success(true)
                .build();

        return  res;
    }
}
