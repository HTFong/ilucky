package burundi.ilucky.controller;

import burundi.ilucky.model.dto.ResponseData;
import burundi.ilucky.model.dto.UserDTO;
import burundi.ilucky.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/api")
public class PublicController {
    @Autowired
    private UserService userService;
    @GetMapping("/top")
    public ResponseEntity<?> getUserTopTotalStars(@RequestParam int topNumber) {
        List<UserDTO> dtos = userService.getTopHighStar(topNumber).stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
}
