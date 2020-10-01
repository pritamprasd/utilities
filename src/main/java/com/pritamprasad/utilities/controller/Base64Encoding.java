package com.pritamprasad.utilities.controller;

import com.pritamprasad.utilities.models.Base64Object;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
public class Base64Encoding {

    public static final String BASE64_TO_PLAIN = "btop";
    public static final String PLAIN_TO_BASE64 = "ptob";
    private Logger LOG = LoggerFactory.getLogger(Base64Encoding.class);

    @GetMapping(value = {"base64"})
    public String index(Model model) {
        model.addAttribute("base64Object", new Base64Object());
        model.addAttribute("conversion", "");
        return "base64/inputpage";
    }

    @PostMapping(value = "/base64converter")
    public String getBase64EncodedString(@Valid @ModelAttribute("base64Object") Base64Object base64Object,
                                         HttpServletRequest request, BindingResult bindingResult, Model model){
        LOG.info("Request received for /base64converter , body: "+ base64Object);
        if (bindingResult.hasErrors()) {
            LOG.error("Returning 404 page as there are errors, "+
                    String.join(bindingResult.getAllErrors().stream()
                            .map(ObjectError::toString)
                            .collect(Collectors.joining(" | ")), " | ")
            );
            return "404";
        } else{
            Base64Object bob = new Base64Object();
            String text = base64Object.getText();
            if(base64Object.getConversion().equals(BASE64_TO_PLAIN)){
                LOG.info(String.format("Converting %s from base64 to plain text.", text));
                bob.setText(new String(Base64.encodeBase64(text.getBytes())));
            } else if(base64Object.getConversion().equals(PLAIN_TO_BASE64)){
                LOG.info(String.format("Converting %s from plain-text to base64.", text));
                bob.setText(new String(Base64.decodeBase64(text.getBytes())));
            }
            bob.setConversion(base64Object.getConversion());
            model.addAttribute("base64Object",bob);
            model.addAttribute("base64Object",bob);
            return "base64/inputpage";
        }
    }

    @GetMapping(value = "/plaintobase64")
    public String redirectToIndex(){
        return "redirect:/base64";
    }
}
