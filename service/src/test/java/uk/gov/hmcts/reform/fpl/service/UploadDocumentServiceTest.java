package uk.gov.hmcts.reform.fpl.service;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.document.DocumentUploadClientApi;
import uk.gov.hmcts.reform.document.domain.Document;
import uk.gov.hmcts.reform.document.domain.UploadResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static uk.gov.hmcts.reform.fpl.utils.DocumentManagementStoreLoader.successfulDocumentUploadResponse;
import static uk.gov.hmcts.reform.fpl.utils.DocumentManagementStoreLoader.unsuccessfulDocumentUploadResponse;

@RunWith(SpringRunner.class)
public class UploadDocumentServiceTest {

    private static final String USER_ID = "1";
    private static final String AUTH_TOKEN = "Bearer token";
    private static final String SERVICE_AUTH_TOKEN = "Bearer service token";

    @Mock
    private AuthTokenGenerator authTokenGenerator;
    @Mock
    protected DocumentUploadClientApi documentUploadClient;

    @InjectMocks
    private UploadDocumentService uploadDocumentService;

    @Before
    public void setup() {
        given(authTokenGenerator.generate()).willReturn(SERVICE_AUTH_TOKEN);
    }

    @Test
    public void shouldReturnFirstUploadedDocument() throws IOException {
        UploadResponse request = successfulDocumentUploadResponse();
        given(documentUploadClient.upload(eq(AUTH_TOKEN), eq(SERVICE_AUTH_TOKEN), eq(USER_ID), any()))
            .willReturn(request);

        Document document = uploadDocumentService.uploadPDF(USER_ID, AUTH_TOKEN, new byte[0], "file");

        Assertions.assertThat(document).isEqualTo(request.getEmbedded().getDocuments().get(0));
    }

    @Test
    public void shouldThrowExceptionIfServerResponseContainsNoDocuments() throws IOException {
        given(documentUploadClient.upload(eq(AUTH_TOKEN), eq(SERVICE_AUTH_TOKEN), eq(USER_ID), any()))
            .willReturn(unsuccessfulDocumentUploadResponse());

        assertThatThrownBy(() -> uploadDocumentService.uploadPDF(USER_ID, AUTH_TOKEN, new byte[0], "file"))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Document upload failed due to empty result");
    }

    @Test
    public void shouldRethrowExceptionIfServerCallThrownException() {
        given(documentUploadClient.upload(eq(AUTH_TOKEN), eq(SERVICE_AUTH_TOKEN), eq(USER_ID), any()))
            .willThrow(new RuntimeException("Something bad happened"));

        assertThatThrownBy(() -> uploadDocumentService.uploadPDF(USER_ID, AUTH_TOKEN, new byte[0], "file"))
            .isInstanceOf(Exception.class)
            .hasMessage("Something bad happened");
    }
}
