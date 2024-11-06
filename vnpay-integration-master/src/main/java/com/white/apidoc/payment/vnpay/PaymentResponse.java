package com.white.apidoc.payment.vnpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("vnp_Amount")
    private String amount;
    @JsonProperty("vnp_BankCode")
    private String bankCode;

    @JsonProperty("vnp_BankTranNo")
    private String bankTranNo;

    @JsonProperty("vnp_CardType")
    private String cardType;

    @JsonProperty("vnp_OrderInfo")
    private String orderInfo;

    @JsonProperty("vnp_PayDate")
    private String payDate;

    @JsonProperty("vnp_ResponseCode")
    private String responseCode;

    @JsonProperty("vnp_TmnCode")
    private String tmnCode;

    @JsonProperty("vnp_TransactionNo")
    private String transactionNo;

    @JsonProperty("vnp_TransactionStatus")
    private String transactionStatus;
    @JsonProperty("vnp_TxnRef")
    private String txnRef;

    @JsonProperty("vnp_SecureHash")
    private String secureHash;

}
