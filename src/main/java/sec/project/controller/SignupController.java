package sec.project.controller;

import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.CustomUserDetailsService;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    public SignupRepository signupRepository;
    
    @Autowired
    public CustomUserDetailsService service;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        return "done";
    }
    
    @RequestMapping(value = "/user/{user_name}", method = RequestMethod.GET)
    public String loadUser(@PathVariable String user_name, Model model) throws IOException {
        model.addAttribute("user", service.loadUserByUsername(user_name));
        return "user";
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String showParticipants(Model model) {
        model.addAttribute("users", service.list());
        model.addAttribute("participants", signupRepository.findAll());
        return "users";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadLogin() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitLogin(@RequestParam String name, @RequestParam String password, Model model) {
        model.addAttribute("details", service.loadUserByUsername(name));
        return "welcome";
    }
    
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String loadWelcome() {
        return "welcome";
    }

}
