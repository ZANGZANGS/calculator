package wirebarley.calculator.service.api;

import java.util.List;

public interface CurrencyApiService {

    public List<Currency.Res> getCurrencyList(Currency.Req req);

}
