/*package com.example.projet1.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PanelController {

    @GetMapping({"/", "/index"})
    public String index(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login"; // Ken mouch connecte, hezzo lel login toul!
        }

        // Check el-role dynamic bech i-hezz kull wa7ed lel dashboard mte3ou flawlessly
        String role = userDetails.getAuthorities().toString();
        if (role.contains("admin")) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/employee/dashboard";
        }
    }
}
*/