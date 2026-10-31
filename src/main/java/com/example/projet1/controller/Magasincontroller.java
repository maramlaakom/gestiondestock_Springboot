package com.example.projet1.controller;

import com.example.projet1.Repository.Utilisateurrepository;
import com.example.projet1.Service.IdMagasinservice;
import com.example.projet1.entitice.Article;
import com.example.projet1.entitice.Magasin;
import com.example.projet1.entitice.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/magasin")
public class Magasincontroller {

    @Autowired
    private IdMagasinservice MagS;
    @Autowired
    Utilisateurrepository utilisateurRepository;

    @GetMapping("/retrieve-all-Magasin")
    public String listMagasins(@RequestParam(name = "search", required = false) String search, Model model,@AuthenticationPrincipal UserDetails userDetails) {
        List<Magasin> results;
        if (search != null && !search.isEmpty()) {
            results = MagS.searchmagasin(search);
        } else {
            results = MagS.findAll();
        }
        Map<Long, Integer> totalsMap = new HashMap<>();
        for (Magasin m : results) {
            int totalQte = 0;
            if (m.getArticles() != null) {
                for (Article a : m.getArticles()) {
                    totalQte += a.getQuantite();
                }
            }
            totalsMap.put(m.getIdMagasin(), totalQte);
        }
        Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("totalsMap", totalsMap);
        model.addAttribute("magasins", results);
        model.addAttribute("CAPACITE_DEFAUT", 2000);
        model.addAttribute("search", search);
        model.addAttribute("role", currentUser.getRole());

        return "magasin/listemagasin";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("magasin", new Magasin());
        return "magasin/ajoutemagasin";
    }

    @PostMapping("/addMagasin")
    public String addMagasin(@ModelAttribute Magasin magasin, RedirectAttributes redirectAttributes) {
        MagS.addEMagasin(magasin);
        redirectAttributes.addFlashAttribute("successMessage", "Magasin ajouté avec succès !");
        return "redirect:/magasin/retrieve-all-Magasin";
    }

    @GetMapping("/update/{id}")
    public String updateEmagasinForm(@PathVariable("id") Long id, Model model) {
        Magasin mag = MagS.findByIdMagasin(id);
        if (mag == null) {
            return "redirect:/magasin/retrieve-all-Magasin?error=notfound";
        }
        model.addAttribute("magasin", mag);
        return "magasin/modifiermagasin";
    }

    @PostMapping("/updatemagasin")
    public String updatemagasin(@ModelAttribute Magasin mag, RedirectAttributes redirectAttributes) {
        MagS.updateMagasin(mag.getIdMagasin(), mag);
        redirectAttributes.addFlashAttribute("successMessage", "Magasin modifié avec succès !");
        return "redirect:/magasin/retrieve-all-Magasin";
    }

    @PostMapping("/delete")
    public String deleteMagasin(@RequestParam("magasinId") Long id, RedirectAttributes redirectAttributes) {
        MagS.DeleteMagasin(id);
        redirectAttributes.addFlashAttribute("successMessage", "Magasin supprimé avec succès !");
        return "redirect:/magasin/retrieve-all-Magasin";
    }
}