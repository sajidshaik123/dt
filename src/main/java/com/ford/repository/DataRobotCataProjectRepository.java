package com.ford.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ford.model.DataRobotCataProject;
@Repository
@Transactional
public interface DataRobotCataProjectRepository extends JpaRepository<DataRobotCataProject, String> {

	@Modifying
	@Query(value = "truncate table data_robot_cata_project", nativeQuery = true)
	void truncateDataRobotCataProject();

}
