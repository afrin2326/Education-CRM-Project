package com.iostream.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iostream.main.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>
{

}
