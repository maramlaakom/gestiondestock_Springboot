package com.example.projet1.controller;

import com.example.projet1.entitice.Utilisateur;
import com.example.projet1.Repository.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class DashboardAdminController {

   articlerepository articleRepository;
     Magasinrepository magasinRepository;
     Mouvementrepository mouvementRepository;
   Utilisateurrepository utilisateurRepository;

    public DashboardAdminController(articlerepository articleRepository,
                                    Magasinrepository magasinRepository,
                                    Mouvementrepository mouvementRepository,
                                    Utilisateurrepository utilisateurRepository) {
        this.articleRepository = articleRepository;
        this.magasinRepository = magasinRepository;
        this.mouvementRepository = mouvementRepository;
        this.utilisateurRepository = utilisateurRepository;
    }
    @GetMapping("/admin/dashboard")
    public String adminPanel() {
        return "admin/admin_panel";
    }
    @GetMapping("/admin/dashboard/index")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
        if (userDetails == null) {
            return "redirect:/login";
        }

        Long idUser = currentUser.getIdUtilisateur();

        model.addAttribute("nomUser", currentUser.getNom());
        model.addAttribute("prenomUser", currentUser.getPrenom());
        model.addAttribute("currentDate", LocalDate.now());

        model.addAttribute("totalArt", articleRepository.countByQuantiteGreaterThan(0));
        model.addAttribute("totalMag", magasinRepository.count());

        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        model.addAttribute("totalMvt", mouvementRepository.countMouvementsByMonth(idUser, currentMonth, currentYear));

        model.addAttribute("nbAlertStock", articleRepository.countByQuantiteLessThanAndQuantiteGreaterThan(20, 0));


        model.addAttribute("alerts", articleRepository.findTop10ByQuantiteLessThanAndQuantiteGreaterThan(20, 0));

        model.addAttribute("movements", mouvementRepository.findTop5ByUtilisateurIdUtilisateurOrderByDateMouvementDesc(idUser));

        return "admin/dashboard_admin";
    }
}