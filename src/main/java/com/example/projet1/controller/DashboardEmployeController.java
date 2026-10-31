package com.example.projet1.controller;

import com.example.projet1.entitice.Utilisateur;
import com.example.projet1.Repository.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
public class DashboardEmployeController {


    articlerepository articleRepository;
    Magasinrepository magasinRepository;
     Mouvementrepository movimientoRepository;
    DemandeMouvementrepository demandeRepository;
     Utilisateurrepository utilisateurRepository;

    public DashboardEmployeController(articlerepository articleRepository,
                                      Magasinrepository magasinRepository,
                                      Mouvementrepository mouvementRepository,
                                      DemandeMouvementrepository demandeRepository,
                                      Utilisateurrepository utilisateurRepository) {
        this.articleRepository = articleRepository;
        this.magasinRepository = magasinRepository;
        this.movimientoRepository = mouvementRepository;
        this.demandeRepository = demandeRepository;
        this.utilisateurRepository = utilisateurRepository;
    }
    @GetMapping("/employee/dashboard")
    public String employePanel() {
        return "employee-stock/employee_panel";
    }

    @GetMapping("/employee/dashboard/index")

    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
        if (currentUser == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        Long idUser = currentUser.getIdUtilisateur();

        model.addAttribute("nomUser", currentUser.getNom());
        model.addAttribute("prenomUser", currentUser.getPrenom());
        model.addAttribute("currentDate", LocalDate.now());

        model.addAttribute("totalArt", articleRepository.countByQuantiteGreaterThan(0));
        model.addAttribute("totalMag", magasinRepository.count());

        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        model.addAttribute("totalMvt", movimientoRepository.countMouvementsByMonth(idUser, currentMonth, currentYear));
        model.addAttribute("totalDemandes", demandeRepository.countByUtilisateurIdUtilisateur(idUser));


        model.addAttribute("movements", movimientoRepository.findTop20ByUtilisateurIdUtilisateurOrderByDateMouvementDesc(idUser));

        return "employee-stock/dashboard_employe";
    }
}