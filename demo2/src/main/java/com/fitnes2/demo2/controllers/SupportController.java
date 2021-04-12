package com.fitnes2.demo2.controllers;

import com.fitnes2.demo2.model.Model_stat;
import com.fitnes2.demo2.repositoryes.Model_stat_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class SupportController {
    @Autowired
    private Model_stat_Repository stat;
    @GetMapping("/support")
    public String support( Model model) {
        Iterable<Model_stat> Stat = stat.findAll();
        model.addAttribute("Stat", Stat);
        return "support";
    }
    @GetMapping("/support/support_new_Post")
    public String newPost( Model model) {
        return "support_new_Post";
    }

    @PostMapping("/support/support_new_Post")
    public String newPost_add(@RequestParam String title, @RequestParam String Anons, @RequestParam String full_text,
                             Model model) {
        Model_stat Model_Stat = new Model_stat(title, Anons, full_text);
        stat.save(Model_Stat);
        return "redirect:/support";
    }
    @GetMapping("/support/{id}")
    public String viewPost(@PathVariable(value = "id") long id, Model model) {
        Optional<Model_stat> modelstat = stat.findById(id);
        ArrayList<Model_stat> res = new ArrayList<>();
        modelstat.ifPresent(res::add);
        model.addAttribute("modelstat", res);
        Model_stat Model_Stat = stat.findById(id).orElseThrow(ArithmeticException::new);
        Model_Stat.addViews();
        stat.save(Model_Stat);
        return "support_details";
    }
    @GetMapping("/support/{id}/delete")
    public String delPost(@PathVariable(value = "id") long id, Model model) {
        stat.deleteById(id);
        return "redirect:/support";
    }
    @GetMapping("/support/{id}/edit")
    public String editPost(@PathVariable(value = "id") long id, Model model) {
        if(!stat.existsById(id)){
            return "redirect:/support";
        }
        Optional<Model_stat> modelstat = stat.findById(id);
        ArrayList<Model_stat> res = new ArrayList<>();
        modelstat.ifPresent(res::add);
        model.addAttribute("modelstat", res);
        return "edit";
    }

    @PostMapping("/support/{id}/edit")
    public String PostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String Anons, @RequestParam String full_text,
                              Model model) {
        Model_stat Model_Stat = stat.findById(id).orElseThrow(ArithmeticException::new);
        Model_Stat.setTitle(title);
        Model_Stat.setAnons(Anons);
        Model_Stat.setFull_text(full_text);
        stat.save(Model_Stat);
        return "redirect:/support";
    }
}
