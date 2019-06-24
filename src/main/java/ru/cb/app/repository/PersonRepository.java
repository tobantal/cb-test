package ru.cb.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.cb.app.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	
	@Query(value = "select q from Person q where lower(q.firstName) like %:query% or lower(q.lastName) like %:query% or lower(q.company) like %:query% or lower(q.workPhone) like %:query% or lower(q.mobilePhone) like %:query% or lower(q.email) like %:query% or lower(q.birthDate) like %:query%")
	List<Person> findByQuery(@Param("query") String query);

}
