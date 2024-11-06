package burundi.ilucky.controller;

import burundi.ilucky.constants.GlobalConstant;
import burundi.ilucky.model.User;
import burundi.ilucky.model.dto.ResponseData;
import burundi.ilucky.model.dto.UserDTO;
import burundi.ilucky.payload.Response;
import burundi.ilucky.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/info")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new AuthenticationServiceException(GlobalConstant.MESSAGE_TOKEN_NULL);
        }
        User user = userService.findByUserName(userDetails.getUsername());
        UserDTO userDTO = new UserDTO(user);
        ResponseData resp = new ResponseData();
        resp.setData(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }



    @GetMapping("/getFreeTurn")
    public ResponseEntity<?> getDailyFreeTurn(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new AuthenticationServiceException(GlobalConstant.MESSAGE_TOKEN_NULL);
        }
        boolean alreadyGetDailyFreeTurn = userService.isAlreadyGetDailyFreeTurn(userDetails.getUsername());
        ResponseData resp = alreadyGetDailyFreeTurn
                ? new ResponseData(GlobalConstant.STATUS_200_0,GlobalConstant.MESSAGE_GET_FREETURN_OK,null)
                : new ResponseData(GlobalConstant.STATUS_200_1,GlobalConstant.MESSAGE_ALREADY_GET_FREETURN,null);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }


}
