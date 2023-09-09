package com.ttknpdev.understandsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
    Importance !
#    Basic Auth is the most basic option to secure the REST APIs.
#    Basic Auth uses an HTTP header in order to provide the username and password when making a request to a server.
#    Basic Auth uses Base 64 encoded username and password in the header.
#    Basic Authentication DO NOT use cookies, hence there is no concept of a session or logging out a user,
#    which means each request has to carry that header in order to be authenticated
*/
@SpringBootApplication
public class UnderstandSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(UnderstandSecurityApplication.class, args);
    }

}
