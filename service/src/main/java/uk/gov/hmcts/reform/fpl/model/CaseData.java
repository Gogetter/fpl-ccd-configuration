package uk.gov.hmcts.reform.fpl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import uk.gov.hmcts.reform.fpl.enums.ProceedingType;
import uk.gov.hmcts.reform.fpl.interfaces.NoticeOfProceedingsGroup;
import uk.gov.hmcts.reform.fpl.model.common.Element;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseData {
    private final List<Element<Applicant>> applicants;
    @NotBlank(message = "Enter Familyman case number", groups = NoticeOfProceedingsGroup.class)
    private final String familyManCaseNumber;
    private List<ProceedingType> proceedingType;
}
