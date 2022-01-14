package org.springframework.samples.petclinic.feeding;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedingController {
    
	private final FeedingService feedingService;
	private final PetService petService;
    
    @Autowired
	public FeedingController(FeedingService feedingService, PetService petService) {
		this.feedingService = feedingService;
		this.petService = petService;
	}
    
    @GetMapping(value = "/feeding/create")
	public String initFeedingForm(ModelMap model) {
    	Feeding feeding = new Feeding();
    	List<FeedingType> feedingTypes = feedingService.getAllFeedingTypes();
    	List<Pet> pets = petService.getAllPets();
    	System.out.println(feedingTypes);
    	model.addAttribute("feeding", feeding);
		model.addAttribute("pets", pets);
		model.addAttribute("feedingTypes", feedingTypes);
		return "feedings/createOrUpdateFeedingForm";
	}
    
    
    
    @PostMapping(value = "/feeding/create")
	public String processCreationForm(@Valid Feeding feeding, BindingResult result, ModelMap model) {	
    	List<FeedingType> feedingTypes = feedingService.getAllFeedingTypes();
    	List<Pet> pets = petService.getAllPets();
		if (result.hasErrors()) {
			model.addAttribute("feeding", feeding);
			model.addAttribute("feedingTypes", feedingTypes);
			model.addAttribute("pets", pets);
			return "feedings/createOrUpdateFeedingForm";
		}
		else {
 
			try {
				this.feedingService.save(feeding);
				model.addAttribute("message", "Producto correctamente creado");
			} catch (UnfeasibleFeedingException e) {
				e.printStackTrace();
				model.addAttribute("message", "La mascota seleccionada no se le puede asignar el plan de alimentación especificado.");
				
			}
				return "redirect: welcome";
		}
	}
	

}
