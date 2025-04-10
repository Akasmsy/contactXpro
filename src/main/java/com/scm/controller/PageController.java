package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.entities.User;
import com.scm.form.UserForm;
import com.scm.helper.AlertMessage;
import com.scm.helper.ColorType;
import com.scm.helper.ResourceNotFoundException;
import com.scm.userservices.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private UserServices userServices;

    @GetMapping("/")  // Change to GET
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")  // Change to GET
    public String home(Model model) {
       
        return "home";
    }

    @GetMapping("/about")  // Change to GET
    public String about(Model model) {
   
        return "about";
    }

    @GetMapping("/services")  // Change to GET
    public String services(Model model) {
       
        return "services";
    }

    @GetMapping("/contact")  // Change to GET
    public String contactUs(Model model) {
       
        return "contact";
    }

    @GetMapping("/login")  // Change to GET
    public String login(Model model) {
      
        return "login";
    }

    @GetMapping("/register")  // Change to GET
    public String signUp(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @PostMapping("/do-register")
    public String doRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rbindingResult,RedirectAttributes redirectAttributes) {
        if (rbindingResult.hasErrors()) {
            return "register";
        }
        
          
        	User checkUser = userServices.getUserEmailById(userForm.getEmail());
            if(checkUser!=null)
            {
            	redirectAttributes.addFlashAttribute("successMsg", new AlertMessage()
            			                                            .builder()
            			                                            .message("This Email is Already Registerd...!")
            			                                            .color(ColorType.red)
            			                                            .build());
            	
            	return "redirect:/register";
            }
        	
            
            
           
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilepic("https://beddingnewsnow.com/wp-content/uploads/2024/06/photos.png");

        User savedUser = userServices.createById(user);

        AlertMessage alertMessage = AlertMessage.builder()
                .message("Registration Successfully")
                .color(ColorType.yellow)
                .build();
        redirectAttributes.addFlashAttribute("successMsg", alertMessage);

        return "redirect:/register";
            
    }
}
