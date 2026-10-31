package com.example.projet1.controller;

import com.example.projet1.Repository.Utilisateurrepository;
import com.example.projet1.Service.DemandService;
import com.example.projet1.Service.IdArticleService;
import com.example.projet1.Repository.DemandeMouvementrepository;
import com.example.projet1.entitice.DemandeMouvement;
import com.example.projet1.entitice.Utilisateur;
import com.example.projet1.entitice.etat_demande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/demandes")
public class demoudmouvementcontroller {

    @Autowired
    private DemandService demS;

    @Autowired
    private IdArticleService artS;

    @Autowired
    private DemandeMouvementrepository dmR;

    @Autowired
    private Utilisateurrepository utilisateurRepository;

    @GetMapping("/retrieve-all-demande")
    public String getAllDemandes(Model model) {
        List<DemandeMouvement> listdemande = demS.findAll();
        model.addAttribute("listDemande", listdemande);
        return "listdemande";
    }

    @GetMapping("/employe/demandes")
    public String showEmployeDemandes(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
        List<DemandeMouvement> listDemandes = dmR.findByUtilisateurIdUtilisateur(currentUser.getIdUtilisateur());
        model.addAttribute("demandes", listDemandes);
        return "employee-stock/listedemande";
    }

    @GetMapping("/add")
    public String showAddForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("demande", new DemandeMouvement());
        model.addAttribute("articles", artS.findAll());
        return "employee-stock/envoyedemande";
    }

    @PostMapping("/addDemande")
    public String addDemande(@ModelAttribute("demande") DemandeMouvement demande,
                             @RequestParam("id_article") Long idArticle,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        try {
            Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
            demande.setEtat(etat_demande.en_attente);
            demS.addDemande(idArticle, currentUser.getIdUtilisateur(), demande);
            redirectAttributes.addFlashAttribute("msg", "Demande ajoutee avec succes!");
            return "redirect:/demandes/employe/demandes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur: " + e.getMessage());
            return "redirect:/demandes/add";
        }
    }

    @PostMapping("/admin/autorisations/accepter")
    public String accepterDemande(@RequestParam("idDemande") Long id, RedirectAttributes ra) {
        DemandeMouvement dm = demS.findByIddemande(id);
        if (dm != null) {
            dm.setEtat(etat_demande.accepte);
            dmR.save(dm);
            ra.addFlashAttribute("success", "Demande autorisee !");
        }
        return "redirect:/demandes/admin/autorisations";
    }

    @PostMapping("/admin/autorisations/refuser")
    public String refuserDemande(@RequestParam("idDemande") Long id, RedirectAttributes ra) {
        DemandeMouvement dm = demS.findByIddemande(id);
        if (dm != null) {
            dm.setEtat(etat_demande.refusee);
            dmR.save(dm);
            ra.addFlashAttribute("danger", "Demande refusee !");
        }
        return "redirect:/demandes/admin/autorisations";
    }

    @GetMapping("/admin/autorisations")
    public String showAdminAutorisations(Model model) {
        model.addAttribute("demandes", dmR.findByEtat(etat_demande.en_attente));
        return "admin/autorisations";
    }
}