package com.ford.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ford.model.DTFileData;
@Repository
@Transactional
public interface DTExcelRepository extends JpaRepository<DTFileData, Integer> {

	void deleteAllByFilePath(String lastModifiedFilePath);

	@Modifying
	@Query(value = "truncate table dTFile_Data", nativeQuery = true)
	void truncateDTFileData();

	List<DTFileData> findAllByFileName(String string);

}
