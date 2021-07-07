package cogent.recruitment.service;

import cogent.recruitment.entities.Document;

public interface DocumentService {
   Document getDocument(int applicationId, String documentType);
}
