package com.example.projet1.controller;

import com.example.projet1.Service.IAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.projet1.entitice.Utilisateur;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/user")
class userController {
    @Autowired
    IAccountService userS ;



    @GetMapping("/retrieve-all-user")
    public String getuser(@RequestParam(name = "search", required = false) String search, Model model)  {
        List<Utilisateur> listuser ;
        if (search != null && !search.isEmpty()) {
            listuser = userS.searchuser(search);
        } else {
            listuser = userS.findAll();
        }
        model.addAttribute("listauser", listuser);
        model.addAttribute("search", search);
        return "user/listeuser";
    }
    @GetMapping("/adduser")
    public String showAddForm(Model model) {
        model.addAttribute("user", new Utilisateur());
        return "user/ajouteruser";
    }


    @PostMapping("/adduser")
    public String adduser(@ModelAttribute("user") @Valid Utilisateur user,
                          BindingResult result,
                          @RequestParam("confirmPassword") String confirmPassword,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "user/ajouteruser";
        }

        try {

            userS.addNewUser(user.getNom(), user.getMotDePasse(), confirmPassword, user.getEmail(), user.getPrenom(), user.getRole());
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur ajouté avec succès !");
            return "redirect:/user/retrieve-all-user";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/adduser";
        }
    }
    @PostMapping("/delete")
    public String deleteuser(@RequestParam("userId") Long id, RedirectAttributes redirectAttributes) {
        userS.DeleteUtilisateur(id);
        redirectAttributes.addFlashAttribute("successMessage", "user supprimé avec succès !");
        return "redirect:/user/retrieve-all-user";
    }
    @GetMapping("/update/{id}")
    public String updateuserForm(  @PathVariable("id") Long id, Model model) {
        Utilisateur user = userS.findByIdUtilisateur(id);
        if (user == null) {
            return "redirect:/user/retrieve-all-user?error=notfound";
        }

        model.addAttribute("user", user);
        return "user/modifieruser";
    }


    @PostMapping("/modifieruser")
    public String updateuser(@ModelAttribute("user") Utilisateur user, RedirectAttributes redirectAttributes) {

        userS.updateUtilisateur(user.getIdUtilisateur(), user);
        redirectAttributes.addFlashAttribute("successMessage", "Utilisateur modifié avec succès !");
        return "redirect:/user/retrieve-all-user";
    }


}
