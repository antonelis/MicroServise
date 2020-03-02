package se.iths.anton.myservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    final UsersRepository repository;
    private final UsersModelAssembler assembler;

    public UsersController(UsersRepository usersRepository, UsersModelAssembler usersModelAssembler) {
        this.repository = usersRepository;
        this.assembler = usersModelAssembler;
    }
    @GetMapping
    public CollectionModel<EntityModel<User>> all() {
        log.info("All persons called");
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<User>> one(@PathVariable Integer id) {
        return repository.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<User> createPerson(@RequestBody User user) {
        log.info("POST create Person " + user);
        var u = repository.save(user);
        log.info("Saved to repository " + u);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(UsersController.class).slash(u.getId()).toUri());
        //headers.add("Location", "/api/persons/" + p.getId());
        return new ResponseEntity<>(u, headers, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePerson(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            log.info("User deleted");
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    ResponseEntity<User> replacePerson(@RequestBody User newUser, @PathVariable Integer id) {
        return repository.findById(id)
                .map(user -> {
                    user.setUserName(newUser.getUserName());
                    user.setRealName(newUser.getRealName());
                    user.setCity(newUser.getCity());
                    user.setIncome(newUser.getIncome());
                    user.setInRelation(newUser.inRelation);
                    repository.save(user);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(linkTo(UsersController.class).slash(user.getId()).toUri());
                    return new ResponseEntity<>(user, headers, HttpStatus.OK);
                })
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PatchMapping("/{id}")
    ResponseEntity<User> modifyPerson(@RequestBody User newUser, @PathVariable Integer id) {
        return repository.findById(id)
                .map(user -> {
                    if (newUser.getUserName() != null)
                        user.setUserName(newUser.getUserName());
                    user.setRealName(newUser.getRealName());
                    user.setCity(newUser.getCity());
                    user.setIncome(newUser.getIncome());
                    user.setInRelation(newUser.inRelation);
                    repository.save(user);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(linkTo(UsersController.class).slash(user.getId()).toUri());
                    return new ResponseEntity<>(user, headers, HttpStatus.OK);
                })
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}