package com.example.projet1.controller;

import com.example.projet1.Repository.Magasinrepository;
import com.example.projet1.Repository.Utilisateurrepository;
import com.example.projet1.Service.ArticleService;
import com.example.projet1.Service.IdArticleService;
import com.example.projet1.entitice.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.projet1.entitice.Utilisateur;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



import java.util.List;

@Controller
@RequestMapping("/article")
class ArticleController {
    @Autowired
    IdArticleService artS;
    @Autowired
    Utilisateurrepository utilisateurRepository;
    @Autowired
    Magasinrepository magasinRepository;


    @GetMapping("/retrieve-all-article")
    public String getArticle(@RequestParam(name = "search", required = false) String search, Model model,@AuthenticationPrincipal UserDetails userDetails) {
        List<Article> listArticle;
        if (search != null && !search.isEmpty()) {
            listArticle = artS.searchArticles(search);
        } else {
            listArticle = artS.findAll();
        }
        Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("listarticle", listArticle);
        model.addAttribute("search", search);
        model.addAttribute("role", currentUser.getRole());


        return "article/liste";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("magasins", magasinRepository.findAll());
        return "article/ajoute";
    }

    @PostMapping("/ajoutarticle")
    public String addarticle(@ModelAttribute("article") @Valid Article article,
                             BindingResult result, HttpSession session,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "article/ajoute";
        }
        if (result.hasErrors()) {
            return "article/ajoute";
        }
        try {
            Utilisateur currentUser = utilisateurRepository.findByEmail(userDetails.getUsername());
            artS.addArticle(article, currentUser);
            redirectAttributes.addFlashAttribute("success", "Article ajouté avec succès !");
            return "redirect:/article/retrieve-all-article";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
            return "redirect:/article/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updatearticleForm(@PathVariable("id") Long id, Model model) {
        Article art = artS.findByIdArticle(id);
        if (art == null) {
            return "redirect:/article/retrieve-all-article?error=notfound";
        }
        model.addAttribute("article", art);
        model.addAttribute("magasins", magasinRepository.findAll());
        return "article/editarticle";
    }

    @PostMapping("/updatearticle")
    public String updatearticle(@ModelAttribute Article art, RedirectAttributes redirectAttributes) {
        artS.updateArticle(art.getIdArticle(), art);
        redirectAttributes.addFlashAttribute("successMessage", "Article modifié avec succès !");
        return "redirect:/article/retrieve-all-article";
    }

    @PostMapping("/delete")
    public String deletearticle(@RequestParam("articleId") Long id, RedirectAttributes redirectAttributes) {
        artS.DeleteArticle(id);
        redirectAttributes.addFlashAttribute("successMessage", "Article supprimé avec succès !");
        return "redirect:/article/retrieve-all-article";
    }
}