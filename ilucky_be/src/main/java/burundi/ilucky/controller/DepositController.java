package burundi.ilucky.controller;

import burundi.ilucky.constants.GlobalConstant;
import burundi.ilucky.model.dto.TurnDTO;
import burundi.ilucky.payload.PaymentResponse;
import burundi.ilucky.payload.Response;
import burundi.ilucky.model.dto.ResponseData;
import burundi.ilucky.service.DepositService;
import burundi.ilucky.service.LuckyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Log4j2
public class DepositController {
    @Autowired
    private DepositService depositService;
    @Autowired
    private LuckyService luckyService;
    public ResponseEntity<?> getAllPayment() {
        // getall
        return ResponseEntity.ok().body(null);
    }
    @GetMapping("/deposit/pay")
    public ResponseEntity<?> generatePaymentLink(@AuthenticationPrincipal UserDetails userDetails,@RequestParam Map<String, Object> parameters) {
        if (userDetails == null) {
            throw new AuthenticationServiceException(GlobalConstant.MESSAGE_TOKEN_NULL);
        }
        ResponseData resp = new ResponseData(GlobalConstant.STATUS_200, GlobalConstant.MESSAGE_200, depositService.getPaymentLink(userDetails.getUsername(), parameters));
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
    @PostMapping("/deposit")
    public ResponseEntity<Void> fetchData(@RequestBody PaymentResponse paymentResponse) {
        depositService.updateBalance(paymentResponse);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/mps/charge")
    public ResponseEntity<?> buyTurn(@AuthenticationPrincipal UserDetails userDetails,@RequestBody TurnDTO turn) {
        if (userDetails == null) {
            throw new AuthenticationServiceException(GlobalConstant.MESSAGE_TOKEN_NULL);
        }
        luckyService.buyTurn(userDetails,turn);
        ResponseData resp = new ResponseData(GlobalConstant.STATUS_200,GlobalConstant.MESSAGE_200,null);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

}
