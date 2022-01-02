package wirebarley.calculator.api;

import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.parser.JSONParser;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class CurrencyApiTest {

    @Autowired
    private CurrencyApi currencyApi;
    @Value("${api.accesskey}") private String ACCESS_KEY;
    @Value("${api.baseurl}") private String BASE_URL;

    @Test
    public void live(){

        //given
        final String source = "USD";
        final String[] currencies = new String[]{"KRW", "JPY", "PHP"};

        //when
        ResponseEntity<HashMap<String, Object>> responseEntity1 = currencyApi.live();
        System.out.println(responseEntity1.toString());

        ResponseEntity<HashMap<String, Object>> responseEntity2 = currencyApi.live(source);
        System.out.println(responseEntity2.toString());

        ResponseEntity<HashMap<String, Object>> responseEntity3 = currencyApi.live(currencies);
        System.out.println(responseEntity3.toString());

        ResponseEntity<HashMap<String, Object>> responseEntity4 = currencyApi.live(source, currencies);
        System.out.println(responseEntity4.toString());

        //잘못된 케이스
        ResponseEntity<HashMap<String, Object>> responseEntity5 = currencyApi.live("ZZZ", currencies);
        System.out.println(responseEntity5.toString());

        ResponseEntity<HashMap<String, Object>> responseEntity6 = currencyApi.live(source, new String[]{"ASD", "ZXC", "PUU"});
        System.out.println(responseEntity6.toString());

        //then
        Assertions.assertThat(responseEntity1).isInstanceOf(ResponseEntity.class);
        Assertions.assertThat(responseEntity2).isInstanceOf(ResponseEntity.class);
        Assertions.assertThat(responseEntity3).isInstanceOf(ResponseEntity.class);
        Assertions.assertThat(responseEntity4).isInstanceOf(ResponseEntity.class);
        Assertions.assertThat(responseEntity5).isInstanceOf(ResponseEntity.class);
        Assertions.assertThat(responseEntity6).isInstanceOf(ResponseEntity.class);


    }

    @Test
    public void convert(){
        //Given
        //String from, String to, String amount, String date

        //when
        ResponseEntity<HashMap<String, Object>> responseEntity1 = currencyApi.convert("USD", "KRW", new BigDecimal(100));
        System.out.println(responseEntity1.toString());

        ResponseEntity<HashMap<String, Object>> responseEntity2 = currencyApi.convert("USD", "JPY", new BigDecimal(300), "2021-12-31");

        //then
        Assertions.assertThat(responseEntity1).isInstanceOf(ResponseEntity.class);

    }

    @Test
    public void list(){
        //given

        //when
        ResponseEntity<HashMap<String, Object>> responseEntity = currencyApi.list();
        System.out.println(responseEntity.toString());
        //then
        Assertions.assertThat(responseEntity).isInstanceOf(ResponseEntity.class);


    }

    @Test
    public void generateURL() {

        //given
        final String ENDPOINT = "live";

        HashMap<String,String> param1= new HashMap<>();

        HashMap<String,String> param2 = new HashMap<>();
        param2.put("source", "PHP");

        HashMap<String,String> param3 = new HashMap<>();
        param3.put("source", "USD");
        param3.put("currencies", String.join(",", new String[]{"KRW", "JPY", "PHP"}));


        try {
            //when
            Method method = currencyApi.getClass().getDeclaredMethod("generateURL", String.class, HashMap.class);
            method.setAccessible(true);

            URI uri1 = (URI) method.invoke(currencyApi, ENDPOINT, param1);
            System.out.println(uri1.toString());

            URI uri2 = (URI) method.invoke(currencyApi, ENDPOINT, param2);
            System.out.println(uri2.toString());

            URI uri3 = (URI) method.invoke(currencyApi, ENDPOINT, param3);
            System.out.println(uri3.toString());


            //then
            Assertions.assertThat(uri1.toString())
                    .isEqualTo("http://api.currencylayer.com/live?access_key=ee50cd7cc73c9b7a7bb3d9617cfb6b9c");

            Assertions.assertThat(uri2.toString())
                    .isEqualTo("http://api.currencylayer.com/live?access_key=ee50cd7cc73c9b7a7bb3d9617cfb6b9c&source=PHP");

            Assertions.assertThat(uri3.toString())
                    .isEqualTo("http://api.currencylayer.com/live?access_key=ee50cd7cc73c9b7a7bb3d9617cfb6b9c&source=USD&currencies=KRW,JPY,PHP");

        } catch (NoSuchMethodException e){
            e.printStackTrace();
        } catch (InvocationTargetException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }

    }
}
