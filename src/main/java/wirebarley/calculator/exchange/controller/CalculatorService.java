package wirebarley.calculator.exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wirebarley.calculator.api.CurrencyApi;

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
            list.add(new String[]{code, (String)currenciesInfo.get(code), (String)quotesInfo.get(source+code)});
        }

        return list;

    }
//    public void liveService(String source, String[] currencies){
//         ResponseEntity<HashMap<String,Object>> responseEntity = currencyApi.live(source, currencies);
//    }
//
//    public void convertService(){
//
//    }
}
