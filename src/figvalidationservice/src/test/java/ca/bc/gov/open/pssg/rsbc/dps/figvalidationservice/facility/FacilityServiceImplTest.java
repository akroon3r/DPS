package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.facility;

import ca.bc.gov.open.ords.figcr.client.api.FacilityApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateFacilityPartyOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidationServiceConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FacilityServiceImplTest {


    public static final String API_EXCEPTION = "api exception";
    private FacilityServiceImpl sut;

    private static final String FACILITY_NAME = "FacilityName";
    private static final String FOUND_FACILITY_PARTY_ID = "123";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String VALIDATION_RESULT = "valid";
    private static final String ERROR_VALIDATION_RESULT = "invalid";
    private static final String ERROR_MESSAGE = "error";
    private static final String ERROR_CODE = "-2";

    @Mock
    private FacilityApi facilityApiMock;


    @BeforeAll
    public void setup() throws ApiException {
        MockitoAnnotations.initMocks(this);

        ValidateFacilityPartyOrdsResponse successResponse = new ValidateFacilityPartyOrdsResponse();
        successResponse.setValidationResult(VALIDATION_RESULT);
        successResponse.setFoundFacilityName(FACILITY_NAME);
        successResponse.setFoundFacilityPartyId(FOUND_FACILITY_PARTY_ID);
        successResponse.setStatusMessage(STATUS_MESSAGE);
        successResponse.setStatusCode(STATUS_CODE);

        ValidateFacilityPartyOrdsResponse errorResponse = new ValidateFacilityPartyOrdsResponse();
        errorResponse.setValidationResult(ERROR_VALIDATION_RESULT);
        errorResponse.setFoundFacilityName("");
        errorResponse.setFoundFacilityPartyId("");
        errorResponse.setStatusMessage(ERROR_MESSAGE);
        errorResponse.setStatusCode(ERROR_CODE);


        Mockito.when(facilityApiMock.validateFacilityParty(Mockito.eq("1"), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(successResponse);
        Mockito.when(facilityApiMock.validateFacilityParty(Mockito.eq("2"), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(errorResponse);
        Mockito.when(facilityApiMock.validateFacilityParty(Mockito.eq("3"), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(new ApiException(API_EXCEPTION));


        sut = new FacilityServiceImpl(facilityApiMock);
    }


    @Test
    public void withValidResponseShouldReturnValidResponse() {

        ValidateFacilityPartyResponse result = sut.validateFacilityParty(new ValidateFacilityPartyRequest("1", "a", "b", "c", "d", "e"));

        Assertions.assertEquals(FACILITY_NAME, result.getFoundFacilityName());
        Assertions.assertEquals(FOUND_FACILITY_PARTY_ID, result.getFoundFacilityPartyId());
        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, result.getValidationResult());

    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        ValidateFacilityPartyResponse result = sut.validateFacilityParty(new ValidateFacilityPartyRequest("2", "a", "b", "c", "d", "e"));
        Assertions.assertEquals(-2, result.getRespCode());
        Assertions.assertEquals(ERROR_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, result.getValidationResult());

    }

    @Test
    public void withApiExceptionShouldReturnValid() {

        ValidateFacilityPartyResponse result = sut.validateFacilityParty(new ValidateFacilityPartyRequest("3", "a", "b", "c", "d", "e"));
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
        Assertions.assertEquals(API_EXCEPTION, result.getValidationResult());

    }

}
