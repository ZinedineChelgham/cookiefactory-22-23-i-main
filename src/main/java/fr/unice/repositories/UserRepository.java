package fr.unice.repositories;

import fr.unice.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BasicRepository<User, String> {
}
