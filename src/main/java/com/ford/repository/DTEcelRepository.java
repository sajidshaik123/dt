package com.ford.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ford.model.DTFileData;

@Transactional
public interface DTEcelRepository extends JpaRepository<DTFileData, String> {

	void deleteAllByFilePath(String lastModifiedFilePath);
	
	@Modifying
    @Query(
            value = "truncate table dTFile_Data",
            nativeQuery = true
    )
	void truncateDTFileData();

}
