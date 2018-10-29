/*global process*/

module.exports = {
  localAuthorityEmail: 'local-authority@example.com',
  localAuthorityPassword: process.env.LA_USER_PASSWORD || 'password',
  eventSummary: '',
  eventDescription: '',
  applicationActions: {
    selectOrders: 'Select orders & directions',
    selectHearing: 'Select hearing',
    enterChildren: 'Enter children in the case',
    enterRespondents: 'Enter respondents in the case',
    enterApplicants: 'Enter applicant in the case',
    enterOthers: 'Enter others who need notice',
    enterGrounds: 'Enter grounds for application',
    enterRisk: 'Enter risk & harm to children',
    enterFactorsAffectingParenting: 'Factors affecting parenting',
    enterInternationalElement: 'Enter international element',
    enterOtherProceedings: 'Enter other proceedings',
    enterAllocationProposal: 'Enter allocation proposal',
    attendingHearing: 'Attending the court',
    uploadDocuments: 'Annex documents',
    submitCase: 'Submit application',
  },
  testFile: './e2e/fixtures/mockFile.txt',
  presidentsGuidanceUrl: 'https://www.judiciary.uk/wp-content/uploads/2013/03/President%E2%80%99s-Guidance-on-Allocation-and-Gatekeeping.pdf',
  scheduleUrl: 'https://www.judiciary.uk/wp-content/uploads/2013/03/Schedule-to-the-President%E2%80%99s-Guidance-on-Allocation-and-Gatekeeping.pdf',
  otherProposalUrl: '/otherProposal/otherProposal1',
};
