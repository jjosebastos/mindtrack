package br.com.fiap.mindtrack.form.mood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @GetMapping
    public String historyShow(){
        return "fragments/history";
    }
}
