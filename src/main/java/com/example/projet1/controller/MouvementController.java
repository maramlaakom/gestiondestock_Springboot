package com.example.projet1.controller;

import com.example.projet1.Repository.Utilisateurrepository;
import com.example.projet1.Service.IdArticleService;
import com.example.projet1.Service.IdmouvementService;
import com.example.projet1.entitice.Mouvement;
import com.example.projet1.entitice.Utilisateur;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/mouvement")
public class MouvementController {

    private final IdmouvementService mouvementService;
    private final IdArticleService artS;
    private final Utilisateurrepository utilisateurRepository;

    public MouvementController(IdmouvementService mouvS, IdArticleService artS,
                               Utilisateurrepository utilisateurRepository) {
        this.mouvementService = mouvS;
        this.artS = artS;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/historique")
    public String showHistoriqueMouvements(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
        List<Mouvement> listMouvements = mouvementService.findAll();
        model.addAttribute("mouvements", listMouvements);
        return "admin/historiquemouvement";
    }

    @GetMapping("/gestionmouvement")
    public String showDashboardMouvement(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("role", currentUser.getRole());
        return "gestionmouvement/gestionmouvement";
    }

    @GetMapping("/sortie")
    public String showSortieForm(@RequestParam(name = "id", required = false) Long idDemande,
                                 @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("articles", artS.findAll());
        model.addAttribute("role", currentUser.getRole());
        model.addAttribute("idDemande", idDemande);
        return "gestionmouvement/sortieStock";
    }

    @PostMapping("/sortie")
    public String enregistrerSortie(@RequestParam("id_article") Long idArticle,
                                    @RequestParam("quantite") int quantite,
                                    @RequestParam(name = "idDemande", required = false) Long idDemande,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    RedirectAttributes redirectAttributes) {
        try {
            Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
            mouvementService.effectuerSortieStock(idArticle, quantite, idDemande, currentUser);
            redirectAttributes.addFlashAttribute("success_message", "Sortie effectuee avec succes");
            return "redirect:/mouvement/gestionmouvement";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error_message", e.getMessage());
            return "redirect:/mouvement/sortie" + (idDemande != null ? "?id=" + idDemande : "");
        }
    }

    @GetMapping("/entree")
    public String showEntreeForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("articles", artS.findAll());
        model.addAttribute("role", currentUser.getRole());
        return "gestionmouvement/entreeStock";
    }

    @PostMapping("/entree")
    public String enregistrerEntree(@RequestParam("id_article") Long idArticle,
                                    @RequestParam("quantite") int quantite,
                                    @RequestParam(name = "idDemande", required = false) Long idDemande,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    RedirectAttributes redirectAttributes) {
        try {
            Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
            mouvementService.effectuerEntreeStock(idArticle, quantite, idDemande, currentUser);
            redirectAttributes.addFlashAttribute("success_message", "Entree effectuee avec succes");
            return "redirect:/article/retrieve-all-article";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error_message", e.getMessage());
            return "redirect:/mouvement/entree";
        }
    }
}