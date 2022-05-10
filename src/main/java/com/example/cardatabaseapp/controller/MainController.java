package com.example.cardatabaseapp.controller;

import com.example.cardatabaseapp.login.security.service.UserDetailsImpl;
import com.example.cardatabaseapp.model.Car;
import com.example.cardatabaseapp.repositories.CarRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
public class MainController {
    private final CarRepository carRepository;

    public MainController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @GetMapping(path = "/")
    public String mainPage(Model model, @RequestParam(defaultValue = "1") int page) {
        if (page >= 0) {
            model.addAttribute("car", carRepository.findAllByOrderByIdDesc(
                    PageRequest.of(page == 0 ? page : page - 1, 5)));
        } else {
            model.addAttribute("car", carRepository.findAllByOrderByIdDesc(
                    PageRequest.of(0, 5)));
        }
        model.addAttribute("currentPage", page > 0 ? page - 1 : 0);
        return "index";
    }

    @GetMapping(path = "/filter")
    public String filteredPage(Model model, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "1") int page) {
        if (!query.isEmpty()) {
            if (page >= 0) {
                model.addAttribute("car", carRepository.findAllByBrandIgnoreCaseContainingOrModelIgnoreCaseContainingOrEngineIgnoreCaseContainingOrBodyTypeIgnoreCaseContainingOrGearTypeIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(query, query, query, query, query, query,
                        PageRequest.of(page == 0 ? page : page - 1, 5)));
            } else {
                model.addAttribute("car", carRepository.findAllByBrandIgnoreCaseContainingOrModelIgnoreCaseContainingOrEngineIgnoreCaseContainingOrBodyTypeIgnoreCaseContainingOrGearTypeIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(query, query, query, query, query, query,
                        PageRequest.of(0, 5)));
            }
            model.addAttribute("query", query);
            model.addAttribute("currentPage", page > 0 ? page - 1 : 0);
            return "index";
        }
        return "redirect:/";
    }


    @GetMapping(path = "/delete")
    public String deleteCar(Authentication authentication, RedirectAttributes redirectAttributes, @RequestParam Integer id) {
        if (authentication == null) {
            redirectAttributes.addFlashAttribute("deleteError", true);
        } else {
            if (!authentication.isAuthenticated()) {
                redirectAttributes.addFlashAttribute("deleteError", true);
            } else {
                UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
                if (userPrincipal == null) {
                    redirectAttributes.addFlashAttribute("deleteError", true);
                } else {
                    try {
                        Car c = carRepository.getById(id);
                        if (Objects.equals(userPrincipal.getId().intValue(), c.getCreatorId())) {
                            carRepository.deleteById(id);
                            redirectAttributes.addFlashAttribute("deleted", true);
                        } else {
                            redirectAttributes.addFlashAttribute("deleteError", true);
                        }
                    } catch (Exception e) {
                        redirectAttributes.addFlashAttribute("deleteError", true);
                    }
                }
            }
        }

        return "redirect:/";
    }

    @PostMapping(path = "/save")
    public String addCar(
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Car c
    ) {
        if (authentication == null) {
            redirectAttributes.addFlashAttribute("saveError", true);
        } else {
            if (!authentication.isAuthenticated()) {
                redirectAttributes.addFlashAttribute("saveError", true);
            } else {
                try {
                    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
                    c.setCreatorId(userPrincipal.getId().intValue());
                    carRepository.save(c);
                    redirectAttributes.addFlashAttribute("saved", true);
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("saveError", true);
                }
            }
        }

        return "redirect:/";
    }

    @PostMapping(path = "/update")
    public String updateCar(
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Car c
    ) {
        if (authentication == null) {
            redirectAttributes.addFlashAttribute("updateError", true);
        } else {
            if (!authentication.isAuthenticated()) {
                redirectAttributes.addFlashAttribute("updateError", true);
            } else {
                UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
                if (Objects.equals(userPrincipal.getId().intValue(), c.getCreatorId())) {
                    try {
                        carRepository.save(c);
                        redirectAttributes.addFlashAttribute("updated", true);
                    } catch (Exception e) {
                        redirectAttributes.addFlashAttribute("updateError", true);
                    }
                } else {
                    System.out.println("ids");
                    redirectAttributes.addFlashAttribute("updateError", true);
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("/car")
    @ResponseBody
    public Optional<Car> findOne(Integer id) {
        return carRepository.findById(id);
    }
}
