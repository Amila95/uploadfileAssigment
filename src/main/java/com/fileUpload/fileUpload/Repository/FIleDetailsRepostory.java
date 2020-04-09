package com.fileUpload.fileUpload.Repository;

import com.fileUpload.fileUpload.modal.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FIleDetailsRepostory extends JpaRepository<FileDetails, Long> {
}
