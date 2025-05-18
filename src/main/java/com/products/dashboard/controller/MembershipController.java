package com.products.dashboard.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.products.dashboard.model.MembershipModel;
import com.products.dashboard.repository.MembershipRepository;

@Controller
public class MembershipController {
    @Autowired
    private MembershipRepository membershipRepository;

    @GetMapping("/memberships")
    public String memberships(Model model) {
        model.addAttribute("memberships", membershipRepository.findAll());
        return "memberships";
    }

    @GetMapping("/membership/addMemberships")
    public String formMembership(Model model) {
        MembershipModel membership = new MembershipModel();
        model.addAttribute("membership", membership);
        return "addMemberships";
    }

    @PostMapping("/membership")
    public String saveMembership(@ModelAttribute("membership") MembershipModel membership) {
        membershipRepository.save(membership);
        return "redirect:/memberships?success=saved";
    }

    @GetMapping("/membership/edit/{id}")
    public String showMembership(@PathVariable Long id, Model model) {
        Optional<MembershipModel> membershipOptional = membershipRepository.findById(id);
        if (membershipOptional.isPresent()) {
            model.addAttribute("membership", membershipOptional.get());
            return "addMemberships";
        } else {
            return "redirect:/memberships?error=notFound";
        }
    }

    @GetMapping("/membership/delete/{id}")
    public String deleteMembership(@PathVariable Long id) {
        if (membershipRepository.existsById(id)) {
            membershipRepository.deleteById(id);
            return "redirect:/memberships?success=deleted";
        } else {
            return "redirect:/memberships?error=deleteFailed";
        }
    }

}
