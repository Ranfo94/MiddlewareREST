package MiddlewareProject.rest;

import MiddlewareProject.handler.TaskHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "policy")
public class PolicyService {

    @RequestMapping(path = "/{line}", method = RequestMethod.GET)
    public void getPolicy(@PathVariable String line) {
            String policy = null;
            String res = null;
            /*
            System.out.println("politica corrente: " + policy);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line = bufferedReader.readLine();
            */
            if (line.equals("random") || line.equals("round-robin") || line.equals("save-the-battery")
                    || line.equals("check-the-distance") || line.equals("distance-and-battery")) {
                policy = line;
                TaskHandler.getInstance().setPolicy(policy);
                System.out.println("policy " + policy + ": impostata");
                //res = "policy " + policy + ": impostata";
            } else {
                System.out.println("la politica inserita non è valida");
                //res = "la politica inserita non è valida";
            }
    }
}
