package wirebarley.calculator.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

@Component
public class CurrencyApi  implements CurrencyApiInterface{
    @Value("${api.accesskey}") private String ACCESS_KEY;
    @Value("${api.baseurl}") private String BASE_URL;


    /**
     * "live" - get the most recent exchange rate data
     */
    public ResponseEntity<HashMap<String,Object>> live(){
        return live(null, null);
    }
    public ResponseEntity<HashMap<String,Object>> live(String source){
        return live(source, null);
    }
    public ResponseEntity<HashMap<String,Object>> live(String[] currencies){
        return live(null, currencies);
    }
    @Override
    public ResponseEntity<HashMap<String,Object>> live(String source, String[] currencies){
        final String ENDPOINT = "live";

        HashMap<String, String> param = new HashMap<>();
        param.put("source", source);
        param.put("currencies", String.join(",", Optional.ofNullable(currencies).orElseGet(() -> new String[]{})));

        return callApi(generateURL(ENDPOINT, param));
    }

    /**
     * "historical" - get historical rates for a specific day
     */
    @Override
    public ResponseEntity<HashMap<String,Object>> historical(String date,String source, String[] currencies) {
//        final String ENDPOINT = "historical";
        return ResponseEntity.ok(null);
    }

    /**
     * "convert" - convert one currency to another
     */
    public ResponseEntity<HashMap<String,Object>> convert(String from, String to, BigDecimal amount) {
        return convert(from, to, amount, null);
    }
    @Override
    public ResponseEntity<HashMap<String,Object>> convert(String from, String to, BigDecimal amount, String date) {
        final String ENDPOINT = "convert";

        HashMap<String, String> param = new HashMap<>();
        param.put("from", from);
        param.put("to", to);
        param.put("amount", amount.toPlainString());
        param.put("date", date);

        return callApi(generateURL(ENDPOINT, param));
    }


    /**
     * "timeframe" - request exchange rates for a specific period of time
     */
    @Override
    public ResponseEntity<HashMap<String,Object>> timeframe(String start_date, String end_date, String source, String[] currencies) {
//        final String ENDPOINT = "timeframe";
        return ResponseEntity.ok(null);
    }

    /**
     * "change" - request any currency's change parameters (margin, percentage)
     */
    @Override
    public ResponseEntity<HashMap<String,Object>> change(String start_date, String end_date, String source, String[] currencies) {
//        final String ENDPOINT = "change";
        return ResponseEntity.ok(null);
    }

    /**
     * "list" - A full list of supported currencies can be accessed both in JSON Format (access key required) and on this website.
     */
    @Override
    public ResponseEntity<HashMap<String, Object>> list() {
        final String ENDPOINT = "list";
        return callApi(generateURL(ENDPOINT, new HashMap<>()));
    }

    /**
     * https://currencylayer.com/ API 호출
     */
    private ResponseEntity<HashMap<String,Object>> callApi(URI url){

        HashMap<String,Object> result = null;

        try{
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(url, HashMap.class);



        } catch (HttpStatusCodeException | UnknownHttpStatusCodeException | ResourceAccessException e) {
            e.printStackTrace();
        }
        //응답코드 에러
        //알수 없는 응답코드
        //네트워크 에러

        return ResponseEntity.ok(result);
    }

    /**
     *
     */
    private URI generateURL(String endPoint, HashMap<String, String> param){

        Iterator<String> iter = param.keySet().iterator();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .path(endPoint)
                .queryParam("access_key",ACCESS_KEY);

        while (iter.hasNext()){
            String key = iter.next();
            builder.queryParamIfPresent(key, Optional.ofNullable(param.get(key)));
        }

        return builder.build().encode().toUri();
    }

}
