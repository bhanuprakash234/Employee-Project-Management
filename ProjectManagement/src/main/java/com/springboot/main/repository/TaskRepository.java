package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.main.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	Page<Task> findByEmployeeId(int eid , Pageable pageable);

	

	



	@Query("select t from Task t WHERE t.title=?1")
	List<Task> findByTitle(String title);







	List<Task> getBySprintId(int sid);







	@Query("SELECT t FROM Task t JOIN t.sprint s JOIN s.backlog b JOIN b.project p WHERE p.id = ?1")
	List<Task> getAllTasksByProjectId(int pid);







	@Query("select t from Task t where t.title LIKE %?1%")
	List<Task> searchByTaskName(String qStr);






	

	 






	







	

	

}
