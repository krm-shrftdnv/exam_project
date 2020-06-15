package ru.kpfu.itis.group_804.project.controllers.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.group_804.project.service.interfaces.SignUpService;

@RestController
public class SignUpAjaxController {

    @Autowired
    SignUpService service;

    @PreAuthorize("permitAll()")
    @GetMapping("/checkEmailAvailability")
    public boolean checkEmailAvailability(@RequestParam(name = "email")String email){
        return service.checkEmailAvailability(email);
    }

}
