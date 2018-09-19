package MiddlewareProject.rest;

import MiddlewareProject.entities.Node;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "registration")
public class RegistrationService {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Node> fogNodeRegistration(@RequestBody Node node) {
        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String hello() {

        return "HELLO WORLDdddd!";
    }
}
