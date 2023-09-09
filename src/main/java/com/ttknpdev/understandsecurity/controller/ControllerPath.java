package com.ttknpdev.understandsecurity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttknpdev.understandsecurity.log.MyLogging;
import com.ttknpdev.understandsecurity.repository.CustomerRepository;
import com.ttknpdev.understandsecurity.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller /* Again  @Controller for getting template  */
//@RestController /* @RestController for getting rest-api only */
//@RequestMapping(value = "/api/test")
public class ControllerPath {
    private CustomerRepository customerRepository;
    @Autowired
    public ControllerPath() {
        this.customerRepository = new CustomerService();
    }
    /* Create ObjectMapper for getting json string */
    private final ObjectMapper mapper = new ObjectMapper();
    @GetMapping
    private ResponseEntity<?> getTest() {
        @Data
        @AllArgsConstructor
        class User {
            private String username;
            private String password;
        }
        User user = new User("Peter Parker","As332*-/Sd^");
        try {
            /* Convert Java pojo to Json object (format)*/
            String jsonUser = mapper.writeValueAsString(user);
            MyLogging.controllerPath.info("jsonUser store "+jsonUser);
        }
        catch (JsonProcessingException e) {
            MyLogging.controllerPath.debug("cause was "+e.getMessage());
            throw new RuntimeException(e);
        }
        finally {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(user);
        }
    }
    @GetMapping(value = "/login")
    private ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    @GetMapping(value = "/read/{fullname}")
    private ResponseEntity<?> read(@PathVariable String fullname) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerRepository.read(fullname));
    }

    @GetMapping(value = "/reads")
    private ResponseEntity<?> reads() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(customerRepository.reads());
    }
}
