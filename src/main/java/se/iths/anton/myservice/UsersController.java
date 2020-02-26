package se.iths.anton.myservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
       // log.debug("All persons called");
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
      //  log.info("POST create Person " + person);
        var p = repository.save(user);
   //     log.info("Saved to repository " + p);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(UsersController.class).slash(p.getId()).toUri());
        //headers.add("Location", "/api/persons/" + p.getId());
        return new ResponseEntity<>(p, headers, HttpStatus.CREATED);
    }


}
