package com.white.apidoc.payment.vnpay.apiclient;

import com.white.apidoc.payment.vnpay.PaymentResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
@Service
public class DepositClient {
    private final RestClient restClient;
    public DepositClient() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:8085")
                .build();
    }

    public void fetchData(PaymentResponse paymentResponse) {
        ResponseEntity<?> response = restClient.post()
                .uri("/api/deposit")
                .contentType(APPLICATION_JSON)
                .body(paymentResponse)
                .retrieve()
                .toBodilessEntity();
        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            //throw
        }
    }
}
