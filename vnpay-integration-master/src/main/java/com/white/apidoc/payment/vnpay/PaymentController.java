package com.white.apidoc.payment.vnpay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.white.apidoc.core.response.ResponseObject;
import com.white.apidoc.payment.vnpay.apiclient.DepositClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${spring.application.api-prefix}/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(@RequestParam Map<String, Object> parameters) {

        String status = (String) parameters.get("vnp_TransactionStatus");
        if (status.equals("00")) {
            paymentService.fetchUpdateDeposit(parameters);
            return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }
}
