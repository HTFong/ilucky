package burundi.ilucky.service.apiclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class PaymentClient {
    private final RestClient restClient;
    public PaymentClient() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public PaymentDTO.VNPayResponse pay(Map<String, Object> parameters) {
        ParameterizedTypeReference<ResponseObject<PaymentDTO.VNPayResponse>> parameterizedTypeReference  = new ParameterizedTypeReference<ResponseObject<PaymentDTO.VNPayResponse>>(){};
        ResponseObject<PaymentDTO.VNPayResponse> payResponse = restClient.get()
                .uri("/api/v1/payment/vn-pay?amount={amount}&bankCode={bankCode}&userId={userId}", parameters)
                .retrieve()
                .body(parameterizedTypeReference);
        if (!HttpStatus.OK.equals(payResponse.getStatusCode())) {
            //throw
        }
        PaymentDTO.VNPayResponse data = payResponse.getBody().data;
        return data;
    }
}
