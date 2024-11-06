package burundi.ilucky.controller;

import burundi.ilucky.jwt.JwtTokenProvider;
import burundi.ilucky.model.User;
import burundi.ilucky.model.dto.AuthRequest;
import burundi.ilucky.model.dto.ResponseData;
import burundi.ilucky.payload.*;
import burundi.ilucky.service.UserService;
import burundi.ilucky.constants.GlobalConstant;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Log4j2
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;
@Autowired
private UserService userService;
	@PostMapping("/login")
	public ResponseEntity<?> auth(@Valid @RequestBody AuthRequest authRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = userService.findByUserName(((UserDetails) authentication.getPrincipal()).getUsername());
		String jwt = tokenProvider.generateToken(user);

		AuthResponse authResponse = new AuthResponse(jwt);
		ResponseData resp = new ResponseData();
		resp.setData(authResponse);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
	@PostMapping("/register")
	public ResponseEntity<?> regis(@Valid @RequestBody AuthRequest authRequest) {
		userService.createUser(authRequest.getUsername(), authRequest.getPassword());
		ResponseData resp = new ResponseData(GlobalConstant.STATUS_201,GlobalConstant.MESSAGE_201,null);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}
}
