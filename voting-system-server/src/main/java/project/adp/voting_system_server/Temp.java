package project.adp.voting_system_server;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Temp {
    @GetMapping("/")
    public String getMethodName(HttpServletRequest request) {
        return "request: "+request.getSession().getCreationTime();
    }

}
