package ru.cb.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.cb.app.domain.Work;

public interface WorkRepository extends JpaRepository<Work, Long>, JpaSpecificationExecutor<Work> {

	Optional<Work> findWorkByFirstNameAndLastName(String firstName, String lastName);

	@Query(value = "select q from Work q where lower(q.firstName) like %:query% or lower(q.lastName) like %:query% or lower(q.company) like %:query% or lower(q.address) like %:query%")
	List<Work> findByQuery(@Param("query") String query);

}
