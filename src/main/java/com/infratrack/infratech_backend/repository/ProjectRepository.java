package com.infratrack.infratech_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.infratrack.infratech_backend.model.Project;

/**
 * MongoDB access for {@link Project}.
 *
 * Extending MongoRepository gives us findAll(), findById(), save(),
 * deleteById() etc. for free — Spring Data generates the implementation
 * at startup, there is no hand-written query code needed for these.
 */
public interface ProjectRepository extends MongoRepository<Project, String> {
}
