package MiddlewareProject.rest;

import MiddlewareProject.entities.FogNode;
import MiddlewareProject.handler.RegistrationHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(path = "registration")
public class RegistrationService {

    private static final AtomicInteger countMiddleware = new AtomicInteger(-1);

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<FogNode> fogNodeRegistration(@RequestBody FogNode fogNode, HttpServletRequest request) {

        fogNode.setPort(String.valueOf(request.getRemotePort()-1));
        fogNode.setId(countMiddleware.incrementAndGet());

        RegistrationHandler.getInstance().addNodeToNodeList(fogNode);

        return new ResponseEntity<>(fogNode, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String hello() {

        return "HELLO WORLD registration!";
    }
}
