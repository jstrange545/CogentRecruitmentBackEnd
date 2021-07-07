package cogent.recruitment.util;

import org.springframework.stereotype.Component;

import cogent.recruitment.entities.InterviewEvaluation;

@Component
public class InterviewEvaluationUtil {
	public void copyNonNullValues(InterviewEvaluation req, InterviewEvaluation db) {

		if (req.isPass()) {
			db.setPass(req.isPass());
		}
		
		if(!(req.isPass())) {
			db.setPass(false);
		}

		if (req.getNotes() != null) {
			db.setNotes(req.getNotes());
		}

	}
}
