package cogent.recruitment.controller;

import cogent.recruitment.entities.Document;
import cogent.recruitment.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

@CrossOrigin
@RestController
public class DocumentController {

	@Autowired
	DocumentService documentService;

	@PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/document")
	public ResponseEntity<?> getDocument(HttpServletResponse response, @RequestParam("applicationId") int id,
			@RequestParam("type") String documentType) throws IOException {
		Document document = documentService.getDocument(id, documentType);
		try (OutputStream os = response.getOutputStream();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			String documentName = document.getName() + ".pdf";
			byteArrayOutputStream.write(document.getData()); // get byte[] array then write it to byteArrayOutputStream
			response.setContentType("application/pdf"); // set response type to determine file type
			response.setHeader("Content-Disposition", "inline;filename=" + new String(documentName.getBytes()));
			byteArrayOutputStream.writeTo(os);
			response.getOutputStream().flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return null;
		return new ResponseEntity<>("", HttpStatus.OK);
	}
}
