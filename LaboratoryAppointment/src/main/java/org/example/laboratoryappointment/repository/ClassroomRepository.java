package org.example.laboratoryappointment.repository;

import org.example.laboratoryappointment.dox.Classroom;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends CrudRepository<Classroom, String> {

    @Query("select * from classroom")
    List<Classroom> findAllClassrooms();

    @Query("select * from classroom c where c.name =:name")
    List<Classroom> findClassroomByName(String name);

    @Query("select * from classroom c where c.role =:role")
    List<Classroom> findClassroomByRole(String role);

    @Query("select * from classroom c where c.quantity =:quantity")
    List<Classroom> findClassroomByQuantity(int quantity);

    @Query("select * from classroom c where c.quantity >= :beg and c.quantity <=:end")
    List<Classroom> findClassroomBetween(int beg, int end);

    @Query("select * from classroom c where c.manager_name =:manager")
    List<Classroom> findClassroomByManager(String manager);

    @Query("select * from classroom c where c.status =:status")
    List<Classroom> findClassroomByStatus(int status);
}
