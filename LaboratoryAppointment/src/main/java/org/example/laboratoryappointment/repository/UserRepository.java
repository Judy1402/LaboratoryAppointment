package org.example.laboratoryappointment.repository;

import org.example.laboratoryappointment.dox.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    @Query("select * from user u where u.account =:account")
    User findByAccount(String account);

    @Query("select * from user u where u.name =:name")
    List<User> findByName(String name);

    @Query("select * from user")
    List<User> findAllUsers();

    @Query("select * from user u where u.profession =:profession")
    List<User> findByProfession(String profession);

    @Modifying
    @Query("delete from user u where u.account =:account")
    void deleteByAccount(String account);

    @Modifying
    @Query("update user u set login_time =:time where u.account =:account")
    void updateLoginTime(LocalDateTime time, String account);

}
