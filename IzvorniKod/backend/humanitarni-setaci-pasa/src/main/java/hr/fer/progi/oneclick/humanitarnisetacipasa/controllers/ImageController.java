package hr.fer.progi.oneclick.humanitarnisetacipasa.controllers;

import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Animal;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.Association;
import hr.fer.progi.oneclick.humanitarnisetacipasa.entities.User;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AnimalRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.AssociationRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.repositories.UserRepository;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.services.UserDetailsImpl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final UserRepository userRepository;
    private final AssociationRepository associationRepository;
    private final AnimalRepository animalRepository;

    public ImageController(UserRepository userRepository, AssociationRepository associationRepository, AnimalRepository animalRepository) {
        this.userRepository = userRepository;
        this.associationRepository = associationRepository;
        this.animalRepository = animalRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<?> uploadImage(@RequestParam("imagefile") MultipartFile image) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> user = this.userRepository.findById(userDetails.getId());

        if (user.isPresent()) {
            User presentUser = user.get();

            presentUser.setPicture(image.getBytes());

            this.userRepository.save(presentUser);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/association/{id}")
    public ResponseEntity<?> getAssociationImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Optional<Association> association = this.associationRepository.findById(id);

        if (association.isPresent() && association.get().getUser().getPicture() != null) {
            User associationUser = association.get().getUser();

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(associationUser.getPicture());
            IOUtils.copy(is, response.getOutputStream());

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/animal/{id}")
    public ResponseEntity<?> getAnimalImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Optional<Animal> animal = this.animalRepository.findById(id);

        if (animal.isPresent() && animal.get().getPicture() != null) {
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(animal.get().getPicture());
            IOUtils.copy(is, response.getOutputStream());

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/animal/{id}")
    public ResponseEntity<?> uploadAnimalImage(@PathVariable("id") Long id, @RequestParam("imagefile") MultipartFile image) throws IOException {
        Optional<Animal> animal = this.animalRepository.findById(id);

        if (animal.isPresent()) {
            Animal existingAnimal = animal.get();

            existingAnimal.setPicture(image.getBytes());

            this.animalRepository.save(existingAnimal);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserImage(HttpServletResponse response) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> user = this.userRepository.findById(userDetails.getId());

        if (user.isPresent() && user.get().getPicture() != null) {
            User presentUser = user.get();

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(presentUser.getPicture());
            IOUtils.copy(is, response.getOutputStream());

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }
}
