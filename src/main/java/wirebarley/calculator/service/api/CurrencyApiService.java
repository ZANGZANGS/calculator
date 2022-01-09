package wirebarley.calculator.service.api;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;


public interface CurrencyApiService {
    /**
     * "live" - get the most recent exchange rate data
     * https://api.currencylayer.com/live
     *
     * Request Parameters
     * access_key	[Required] Your API Access Key.
     * source	    [optional] Specify a Source Currency other than the default USD. Supported on the Basic Plan and higher.
     * currencies	[optional] Specify a comma-separated list of currency codes to limit your API response to specific currencies.
     */
    public HashMap<String,Object> live(String source, String[] currencies);

    /**
     * "list" - A full list of supported currencies can be accessed both in JSON Format (access key required) and on this website.
     */
    public HashMap<String,Object> list();
}
