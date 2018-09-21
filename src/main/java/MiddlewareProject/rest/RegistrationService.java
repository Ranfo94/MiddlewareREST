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

@RestController
@RequestMapping(path = "registration")
public class RegistrationService {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<FogNode> fogNodeRegistration(@RequestBody FogNode fogNode, HttpServletRequest request) {

        FogNode updatedFogNode = RegistrationHandler.getInstance().addNodeToNodeList(fogNode);

        updatedFogNode.setPort(String.valueOf(request.getRemotePort()-1));
        System.out.println(updatedFogNode.getPort());

        return new ResponseEntity<>(updatedFogNode, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String hello() {

        return "HELLO WORLD registration!";
    }
}
