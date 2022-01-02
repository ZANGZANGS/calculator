package wirebarley.calculator.api;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;


public interface CurrencyApiInterface {
    /**
     * "live" - get the most recent exchange rate data
     * https://api.currencylayer.com/live
     *
     * Request Parameters
     * access_key	[Required] Your API Access Key.
     * source	    [optional] Specify a Source Currency other than the default USD. Supported on the Basic Plan and higher.
     * currencies	[optional] Specify a comma-separated list of currency codes to limit your API response to specific currencies.
     */
    public ResponseEntity<HashMap<String,Object>> live(String source, String[] currencies);

    /**
     * "historical" - get historical rates for a specific day
     * https://api.currencylayer.com/historical?date=YYYY-MM-DD
     *
     * access_key	[Required] Your API Access Key.
     * date	        [Required] Specify a date for which to request historical rates. (Format: YYYY-MM-DD)
     * source	    [optional] Specify a Source Currency other than the default USD. Supported on the Basic Plan and higher.
     * currencies	[optional] Specify a comma-separated list of currency codes to limit your API response to specific currencies.
     */
    public ResponseEntity<HashMap<String,Object>> historical(String date, String source, String[] currencies);

    /**
     * "convert" - convert one currency to another
     * https://api.currencylayer.com/convert?from=EUR&to=GBP&amount=100
     *
     * access_key	[Required] Your API Access Key.
     * from	        [Required] Specify the currency to convert from.
     * to	        [Required] Specify the currency to convert to.
     * amount	    [Required] Specify the amount to convert.
     * date	        [optional] Specify a date to use historical rates for this conversion. (Format: YYYY-MM-DD)
     */
    public ResponseEntity<HashMap<String,Object>> convert(String from, String to, BigDecimal amount, String date);

    /**
     * "timeframe" - request exchange rates for a specific period of time
     * https://api.currencylayer.com/timeframe?start_date=2015-01-01&end_date=2015-05-01
     *
     * access_key	[Required] Your API Access Key.
     * start_date	[Required] Specify the start date of your time frame.
     * end_date	    [Required] Specify the end date of your time frame.
     * source	    [optional] Specify a Source Currency other than the default USD. Supported on the Basic Plan and higher.
     * currencies	[optional] Specify a comma-separated list of currency codes to limit your API response to specific currencies.
     */
    public ResponseEntity<HashMap<String,Object>> timeframe(String start_date, String end_date, String source, String[] currencies);

    /**
     * "change" - request any currency's change parameters (margin, percentage)
     * https://api.currencylayer.com/change?currencies=USD,EUR
     *
     * access_key	[Required] Your API Access Key.
     * start_date	[Required] Specify the start date of your time frame.
     * end_date	    [Required] Specify the end date of your time frame.
     * source	    [optional] Specify a Source Currency other than the default USD. Supported on the Basic Plan and higher.
     * currencies	[optional] Specify a comma-separated list of currency codes to limit your API response to specific currencies.
     */
    public ResponseEntity<HashMap<String,Object>> change(String start_date, String end_date, String source, String[] currencies);

    /**
     * "list" - A full list of supported currencies can be accessed both in JSON Format (access key required) and on this website.
     */
    public ResponseEntity<HashMap<String,Object>> list();
}
