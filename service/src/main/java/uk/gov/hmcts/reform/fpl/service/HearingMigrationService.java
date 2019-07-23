package uk.gov.hmcts.reform.fpl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ccd.client.model.AboutToStartOrSubmitCallbackResponse;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.fpl.model.Hearing;
import uk.gov.hmcts.reform.fpl.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class HearingMigrationService {

    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    public AboutToStartOrSubmitCallbackResponse setMigratedValue(CaseDetails caseDetails) {
        Map<String, Object> data = caseDetails.getData();

        if (caseDetails.getData().containsKey("hearing1") || !caseDetails.getData().containsKey("hearing")) {
            data.put("hearingMigrated", "Yes");
        } else {
            data.put("hearingMigrated", "No");
        }

        return AboutToStartOrSubmitCallbackResponse.builder()
            .data(data)
            .build();
    }

    public AboutToStartOrSubmitCallbackResponse addHiddenValues(CaseDetails caseDetails) {
        Map<String, Object> data = caseDetails.getData();

        if (caseDetails.getData().containsKey("hearing1")) {
            Hearing hearing = mapper.convertValue(data.get("hearing1"), Hearing.class);
            Hearing.HearingBuilder hearingBuilder = hearing.toBuilder();

            if (hearing.getHearingID() == null || hearing.getHearingID().isBlank()) {
                String now = DateUtils.convertLocalDateTimeToString(LocalDateTime.now());
                hearingBuilder.hearingID(UUID.randomUUID().toString());
                hearingBuilder.hearingDate(now);
                hearingBuilder.createdBy("");
                hearingBuilder.createdDate(now);
            }

            data.put("hearing1", hearingBuilder.build());
        }

        return AboutToStartOrSubmitCallbackResponse.builder()
            .data(data)
            .build();
    }

}
