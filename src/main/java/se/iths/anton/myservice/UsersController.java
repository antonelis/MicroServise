package se.iths.anton.myservice;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<EntityModel<User>> one(@PathVariable int id) {
        return repository.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
