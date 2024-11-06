package com.white.apidoc.payment.vnpay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.white.apidoc.core.config.payment.VNPAYConfig;
import com.white.apidoc.payment.vnpay.apiclient.DepositClient;
import com.white.apidoc.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private VNPAYConfig vnPayConfig;
    @Autowired
    private DepositClient depositClient;
    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_ReturnUrl",vnpParamsMap.get("vnp_ReturnUrl") + "?userId="+request.getParameter("userId"));
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }
    public void fetchUpdateDeposit(Map<String, Object> parameters) {
        PaymentResponse paymentResponse = new ObjectMapper().convertValue(parameters, PaymentResponse.class);
        paymentResponse.setAmount(Long.toString(Long.parseLong(paymentResponse.getAmount()) / 100L));
        depositClient.fetchData(paymentResponse);
    }
}
