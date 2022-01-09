package wirebarley.calculator.service.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

/**
 *  api 호출에 필요한 메서드를 CurrencyApiInterface 인터페이스를 상속받아 구현
 *  - request 요청시 optional한 값을 오버로딩으로 구현
 *  - 미사용 메서드는 구현하지 않음
 */
@Component
public class CurrencyApiServiceImpl implements CurrencyApiService {
    @Value("${api.accesskey}") private String ACCESS_KEY;
    @Value("${api.baseurl}") private String BASE_URL;


    /**
     * "live" - get the most recent exchange rate data
     */

    @Override
    public HashMap<String,Object> live(String source, String[] currencies){
        final String ENDPOINT = "live";

        HashMap<String, String> param = new HashMap<>();
        param.put("source", source);
        param.put("currencies", String.join(",", Optional.ofNullable(currencies).orElseGet(() -> new String[]{})));

        return callApi(generateURI(ENDPOINT, param)).getBody();
    }



    /**
     * "list" - A full list of supported currencies can be accessed both in JSON Format (access key required) and on this website.
     */
    @Override
    public HashMap<String, Object> list() {
        final String ENDPOINT = "list";
        return callApi(generateURI(ENDPOINT, new HashMap<>())).getBody();
    }

    /**
     * https://currencylayer.com/ API 호출 메서드
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
     * URI 생성 메서드
     */
    private URI generateURI(String endPoint, HashMap<String, String> param){

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