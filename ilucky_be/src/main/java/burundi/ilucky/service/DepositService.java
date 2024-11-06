package burundi.ilucky.service;

import burundi.ilucky.model.PaymentHistory;
import burundi.ilucky.model.User;
import burundi.ilucky.payload.PaymentResponse;
import burundi.ilucky.repository.PaymentHistoryRepository;
import burundi.ilucky.service.apiclient.PaymentClient;
import burundi.ilucky.service.apiclient.PaymentDTO;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class DepositService {

    @Autowired
    PaymentClient paymentClient;
    @Autowired
    UserService userService;
    @Autowired
    PaymentHistoryRepository paymentHistoryRepository;

    public String getPaymentLink(String userName, Map<String, Object> parameters) {
        User user = userService.findByUserName(userName);
        parameters.put("userId", user.getId());
        // api process payment0
        PaymentDTO.VNPayResponse paymentUrlResponse = paymentClient.pay(parameters);
        //
        //throw if payment fail
        return paymentUrlResponse.paymentUrl;
    }

    public PaymentHistory updateBalance(PaymentResponse paymentResponse) {
        User user = userService.findById(Long.valueOf(paymentResponse.getUserId()));
        user.setTotalVnd(user.getTotalVnd()+Long.parseLong(paymentResponse.getAmount()));
        user = userService.saveUser(user);

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setPaymentId(paymentResponse.getTxnRef());
        paymentHistory.setAmount(Long.valueOf(paymentResponse.getAmount()));
        paymentHistory.setTransactionNo(paymentResponse.getTransactionNo());
        paymentHistory.setPayDate(new Date(Long.valueOf(paymentResponse.getPayDate())));
        paymentHistory.setUser(user);
        PaymentHistory savedPaymentHistory = paymentHistoryRepository.save(paymentHistory);

        return savedPaymentHistory;
    }
}
