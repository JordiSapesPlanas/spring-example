package com.udl.softarch.springexample.controllers;

import com.google.common.base.Preconditions;
import com.udl.softarch.springexample.models.Greeting;
import com.udl.softarch.springexample.models.User;
import com.udl.softarch.springexample.repositories.GreetingRepository;
import com.udl.softarch.springexample.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.awt.SunHints;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by david on 23/03/15.
 */

@Controller
@RequestMapping(value = "/users")
public class UserController {


    // templates not do it

    @Autowired
    UserRepository userRepository;
    @Autowired
    GreetingRepository greetingRepository;
    /****************** GET *******************/
    //LIST
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<User> list(@RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "10") int size){
        PageRequest request = new PageRequest(page, size);
        return userRepository.findAll(request).getContent();
    }
    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public ModelAndView listHtml(@RequestParam(required = false, defaultValue = "0") int page,
                                 @RequestParam(required = false, defaultValue = "10") int size){
        return new ModelAndView("users", "users", list(page, size));
    }
    //RETRIEVE
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User retrieve(@PathVariable("id") Long id){
        Preconditions.checkNotNull(userRepository.findOne(id), "user with id %s not found", id);
        return userRepository.findOne(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView retrieveHtml(@PathVariable( "id" ) Long id){
        return new ModelAndView("user", "user", retrieve(id));
    }

    /************************* post ********************/
    //Create

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User create(@Valid @RequestBody User user, HttpServletResponse response){
        Preconditions.checkNotNull(userRepository.findUserByEmail(user.getEmail()), "user with %s email already exists", user.getEmail());
        Preconditions.checkNotNull(userRepository.findUserByEmail(user.getUsername()), "user with %s email already exists", user.getUsername());

        return userRepository.save(user);
    }
    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces = "text/html")
    public String createHtml(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "formUser";
        }
        return "redirect:/users"+create(user, response).getId();
    }
    @RequestMapping(value = "/form", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView createForm(){
        User empty = new User();
        return new ModelAndView("formUser", "user", empty);
    }
    /**************************** Put ****************************/
    // UPDATE
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User update(@PathVariable("id") Long id, @Valid @RequestBody User user){
        User old = userRepository.findOne(id);
        old.setEmail(user.getEmail());
        old.setUsername(user.getUsername());
        return userRepository.save(old);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/x-www-form-urlencoded")
    @ResponseStatus(HttpStatus.OK)
    public String updateHTML(@PathVariable("id") Long id, @Valid @ModelAttribute("user") User user,
                             BindingResult binding) {
        if (binding.hasErrors()) {
            return "formUser";
        }
        return "redirect:/greetings/" + update(id, user).getId();
    }

    // Update form
    @RequestMapping(value = "/{id}/form", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView updateForm(@PathVariable("id") Long id) {
        return new ModelAndView("formUser", "user", userRepository.findOne(id));
    }


    /******************************* DELETE *********************************/
    @RequestMapping (value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        userRepository.delete(userRepository.findOne(id));
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    public String deleteHtml(@PathVariable("id") Long id){
        delete(id);
        return "redirect:/users";
    }


    // PUT not do it
    // add greeting not do it
    // delete one greeting not do it
    // retrieve one greeting not do it

}