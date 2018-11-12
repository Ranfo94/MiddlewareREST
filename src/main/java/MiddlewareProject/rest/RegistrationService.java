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

    // This variable create a unique id for the fog nodes
    private static final AtomicInteger countMiddleware = new AtomicInteger(-1);

    /**This method accepts the request for registration from fog nodes and add the node to the active fog nodes list
     * @param fogNode is the fog node that wants to registrate itself on the middleware
     * @return the entity of the response, in this case the fog node and the http status
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> fogNodeRegistration(@RequestBody FogNode fogNode,HttpServletRequest request) {

        fogNode.setId(countMiddleware.incrementAndGet());
        System.out.println("local: "+request.getLocalAddr() + "\nremote: "+request.getRemoteAddr()+"\nurl: "+request.getRemotePort());
        System.out.println("***** " + fogNode.getPort() + " " + fogNode.getId());

        RegistrationHandler.getInstance().addNodeToNodeList(fogNode);
        RegistrationHandler.getInstance().printFogNodeList();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String hello() {
        return "HELLO WORLD registration!";
    }
}
