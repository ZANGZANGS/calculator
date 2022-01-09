package wirebarley.calculator.service.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

/**
 *  api 호출에 필요한 메서드를 CurrencyApiInterface 인터페이스를 상속받아 구현
 *  - request 요청시 optional한 값을 오버로딩으로 구현
 *  - 미사용 메서드는 구현하지 않음
 */
@Component
public class CurrencyApiServiceImpl implements CurrencyApiService {
    @Value("${api.accesskey}") private String ACCESS_KEY;
    @Value("${api.baseurl}") private String BASE_URL;

    @Override
    public List<Currency.Res> getCurrencyList(Currency.Req req) {

        List<Currency.Res> result = new ArrayList<>();

        HashMap<String, Object> list = list();
        HashMap<String, Object> live = live(req.getSource(), req.getCurrencies());

        if( (boolean)list.get("success")){

            HashMap<String, String> listCurrencies = (HashMap<String, String>)list.get("currencies");
            HashMap<String, String> liveCurrencies = (boolean)live.get("success") ?
                    (HashMap<String, String>)live.get("currencies") : new HashMap<>();

            for (String cur : req.getCurrencies()){

                BigDecimal rate = null != liveCurrencies.get(req.getSource() + cur) ?
                        new BigDecimal(liveCurrencies.get(req.getSource() + cur)): new BigDecimal("100.00");

                Currency.Res res = Currency.Res.builder()
                        .currency(cur)
                        .content(listCurrencies.get(cur))
                        .rate(rate)
                        .build();

                result.add(res);

            }

        }

        return result;

    }




    /**
     * "live" - get the most recent exchange rate data
     */
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
    public HashMap<String, Object> list() {
        final String ENDPOINT = "list";
        return callApi(generateURI(ENDPOINT, new HashMap<>())).getBody();
    }

    /**
     * https://currencylayer.com/ API 호출 메서드
     */
    private ResponseEntity<HashMap<String,Object>> callApi(URI url){

        HashMap<String,Object> result = new HashMap<>();

        try{

            Optional.ofNullable(url)
                    .orElseThrow(()-> {
                        throw new NullPointerException("url이 없습니다.");
                    });

            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(url, HashMap.class);



        } catch (HttpStatusCodeException e) {//응답코드 에러
            result.put("success", false);
            result.put("message", "HttpStatusCodeException 발생하였습니다.");
        } catch (UnknownHttpStatusCodeException e){ //알수 없는 응답코드
            result.put("success", false);
            result.put("message", "UnknownHttpStatusCodeException 발생하였습니다.");
        } catch (ResourceAccessException e){ //네트워크 에러
            result.put("success", false);
            result.put("message", "ResourceAccessException 발생하였습니다." );
        } catch (NullPointerException e){
            result.put("success", false);
            result.put("message", "NullPointerException 발생하였습니다. " + e.getMessage());
        } catch (Exception e){
            result.put("success", false);
            result.put("message", "알수 없는 Exception이 발생하였습니다." );
        }



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

