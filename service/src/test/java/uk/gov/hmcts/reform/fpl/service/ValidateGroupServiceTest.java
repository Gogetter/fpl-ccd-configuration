package uk.gov.hmcts.reform.fpl.service;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.reform.fpl.interfaces.NoticeOfProceedingsGroup;
import uk.gov.hmcts.reform.fpl.model.CaseData;
import uk.gov.hmcts.reform.fpl.model.HearingBooking;
import uk.gov.hmcts.reform.fpl.model.common.Element;

import java.util.List;
import java.util.UUID;

import javax.validation.Validation;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class ValidateGroupServiceTest {
    private ValidateGroupService validateGroupService;

    @BeforeEach()
    private void setup() {
        validateGroupService = new ValidateGroupService(Validation
            .buildDefaultValidatorFactory()
            .getValidator());
    }

    @Test
    void shouldReturnAnErrorIfFamilyManCaseNumberAndHearingBookingDetailsIsNotPopulated() {
        CaseData caseData = CaseData.builder().build();
        List<String> errors = validateGroupService.validateGroup(caseData, NoticeOfProceedingsGroup.class);

        assertThat(errors).containsOnlyOnce(
            "Enter Familyman case number",
            "Enter hearing details"
        );
    }

    @Test
    void shouldNotReturnAnErrorIfFamilyManCaseNumberAndHearingBookingDetailsIsPopulated() {
        CaseData caseData = CaseData.builder()
            .hearingDetails(ImmutableList.of(
                Element.<HearingBooking>builder()
                    .id(UUID.randomUUID())
                    .value(HearingBooking.builder().build())
                    .build()))
            .familyManCaseNumber("123")
            .build();
        List<String> errors = validateGroupService
            .validateGroup(caseData, NoticeOfProceedingsGroup.class);

        assertThat(errors).isEmpty();
    }
}
