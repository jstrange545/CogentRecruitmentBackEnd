package cogent.recruitment.service;

import cogent.recruitment.dao.DocumentDao;
import cogent.recruitment.entities.Document;
import cogent.recruitment.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Transactional
    @Override
    public Document getDocument(int applicationId, String documentType) {
        Document document = documentDao.findByApplicationIdAndNameContainingIgnoreCase(applicationId,documentType)
                .orElseThrow(() -> new ResourceNotFoundException("Document with type:"+ documentType +" and application_ID: " + applicationId + " not found."));
        return document;
    }
}
