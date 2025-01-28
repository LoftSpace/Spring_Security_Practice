package moin.demo.service.CurrencyService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceFactory {
    private final Map<String, CurrencyService> currencyServices;

    public CurrencyServiceFactory(List<CurrencyService> services) {
        this.currencyServices = services.stream()
                .collect(Collectors.toMap(service -> service.getClass().getSimpleName(), service->service));
    }

    public CurrencyService getService(String currency) {
        switch(currency) {
            case "USD":
                return currencyServices.get("USDService");
            case "JPY":
                return currencyServices.get("JPYService");
            default:
                throw new IllegalArgumentException("지원되지 않는 통화입니다.");
        }
    }
}
