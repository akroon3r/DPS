package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * model class for response messages for /validateApplicantForSharing requests
 *
 * @author archanasudha
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateApplicantForSharingResponse {

    private String validationResult;
    private int respCode;
    private String respMsg;

    public ValidateApplicantForSharingResponse(String validationResult, int respCode, String respMsg) {
        super();
        this.validationResult = validationResult;
        this.respMsg = respMsg;
        this.respCode = respCode;
    }

    public String getValidationResult() {
        return validationResult;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }


}


