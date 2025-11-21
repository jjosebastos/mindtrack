package br.com.fiap.mindtrack.form;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mood")
public class MoodController {


    @GetMapping
    public String showMood(){
        return "fragments/moodForm";
    }

}
