package wirebarley.calculator.service.convert;

import org.springframework.stereotype.Service;
import wirebarley.calculator.service.api.Currency;

@Service
public class ConvertServiceImpl implements ConvertService{

    @Override
    public Convert.Res convert(Convert.Req req){

        Convert.Res res = Convert.Res.builder()
                .amount(req.getAmount().multiply(req.getRate()))
                .build();



        return res;

    }
}
